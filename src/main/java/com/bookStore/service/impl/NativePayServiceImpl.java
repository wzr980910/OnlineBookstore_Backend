package com.bookStore.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bookStore.mapper.BookMapper;
import com.bookStore.pojo.Book;
import com.bookStore.pojo.Orders;
import com.bookStore.pojo.vo.BookVo;
import com.bookStore.pojo.wxpay.NotifyDto;
import com.bookStore.service.BookService;
import com.bookStore.service.NativePayService;
import com.bookStore.util.WxPayTemplate;
import com.google.gson.Gson;
import com.wechat.pay.contrib.apache.httpclient.util.AesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

/**
 * ClassName: NativePayServiceImpl
 * Package: com.bookStore.service.impl
 * Description:
 *
 * @Author: 邓桂材
 * @Create: 2024/1/19 -11:52
 * @Version: v1.0
 */
@Service
public class NativePayServiceImpl implements NativePayService {
    private String apiV3Key = "CZBK51236435wxpay435434323FFDuv3";
    @Autowired
    private WxPayTemplate wxPayTemplate;
    @Autowired
    private BookService bookService;

    @Override
    //支付
    public String pay(Orders orders) {
        LambdaUpdateWrapper<Book> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Book::getId, orders.getBookId());
        BookVo bookVo = new BookVo();
        bookVo.setId(orders.getBookId());
        Page<BookVo> page = bookService.selectBookPage(bookVo);
        List<BookVo> bookVoList = page.getRecords();
        BookVo bookVoSelected = bookVoList.get(0);
        String bookName = bookVoSelected.getBookName();
        String code_url = null;
        try {
            code_url = wxPayTemplate.CreateOrder(orders.getPrice(), "购买图书:" + bookName, String.valueOf(orders.getOrderId()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return code_url;
    }

    @Override
    public String payNotify(NotifyDto dto) {
        try {
            //解密微信传递过来的参数
            String json = new AesUtil(apiV3Key.getBytes()).decryptToString(dto.getResource().getAssociated_data().getBytes(),
                    dto.getResource().getNonce().getBytes(),
                    dto.getResource().getCiphertext());
            Gson gson = new Gson();
            String outTradeNo = gson.fromJson(json, Map.class).get("out_trade_no").toString();
            System.out.println("支付成功的订单号" + outTradeNo);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            return "Fail";
        }
        return "Success";
    }
}
