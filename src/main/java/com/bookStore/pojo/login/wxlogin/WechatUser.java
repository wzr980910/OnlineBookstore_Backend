package com.bookStore.pojo.login.wxlogin;

import lombok.Data;

/**
 * ClassName: WechatUser
 * Package: com.bookStore.pojo.login.wxlogin
 * Description:
 *
 * @Author: 邓桂材
 * @Create: 2024/1/22 -10:33
 * @Version: v1.0
 */
@Data
public class WechatUser {
    //微信用户唯一标识
    private String openid;
    //昵称
    private String nickname;
    //性别,1男，2女，0未知
    private Integer sex;
    //国家
    private String country;
    //城市
    private String city;
    private String province;
    //头像
    private String headimgurl;

    //只有在用户将公众号绑定到微信开放平台账号后，才会出现该字段
    private String unionId;
}
