package com.bookStore.util.result;

import com.bookStore.pojo.login.wxlogin.TokenInfo;
import com.bookStore.pojo.login.wxlogin.WechatUser;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.IOException;

/**
 * ClassName: WechatLoginUtil
 * Package: com.bookStore.util.result
 * Description:
 *
 * @Author: 邓桂材
 * @Create: 2024/1/22 -10:00
 * @Version: v1.0
 */

public class WechatLoginUtil {
    @Value("wx.mp.appid")
    private static String appid;
    @Value("wx.mp.secret")
    private static String secret;

    public static WechatUser getUserInfo(String code) throws IOException {
        //构造http请求客户端
        HttpClient httpClient= HttpClients.createDefault();
        //用code交换token，code为微信扫码后微信服务器响应回来的值
        String tokenUrl="https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+secret+
                "&code=CODE&grant_type=authorization_code";
        //发请求
        HttpGet httpGet = new HttpGet(tokenUrl);
        String responseResult="";
        //接收返回的数据，转化成utf-8格式
        HttpResponse response = httpClient.execute(httpGet);
        if(response.getStatusLine().getStatusCode() == 200){
            responseResult= EntityUtils.toString(response.getEntity(),"UTF-8");
        }
        //将结果封装到TokenInfo对象中
        Gson gson = new Gson();
        TokenInfo tokenInfo = gson.fromJson(responseResult, TokenInfo.class);
        //用accessToken获取个人用户信息
        String userInfoUrl="https://api.weixin.qq.com/sns/userinfo?access_token="+tokenInfo.getAccessToken()+
                "&openid="+tokenInfo.getOpenid()+"&lang=zh_CN";
        //构造http请求客户端
        HttpGet httpGetUserInfo = new HttpGet(userInfoUrl);
        //接收数据
        HttpResponse responseUserInfo = httpClient.execute(httpGetUserInfo);
        WechatUser wechatUser=null;
        if(responseUserInfo.getStatusLine().getStatusCode() == 200){
            responseResult=EntityUtils.toString(responseUserInfo.getEntity(),"UTF-8");
            //将获取到的用户信息转化为wechatUser对象
            wechatUser=gson.fromJson(responseResult, WechatUser.class);
        }
            return wechatUser;
    }
}
