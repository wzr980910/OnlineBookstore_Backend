package com.bookStore.pojo.login.wxlogin;

import lombok.Data;

/**
 * ClassName: TokenInfo
 * Package: com.bookStore.pojo.login.wxlogin
 * Description:
 *
 * @Author: 邓桂材
 * @Create: 2024/1/22 -9:53
 * @Version: v1.0
 */
@Data
public class TokenInfo {
    //网页授权接口凭证，与基础支持的access_token不同
    private String accessToken;
    //超时时间，单位秒
    private String expiresIn;
    //刷新凭证
    private String refreshToken;
    //用户唯一标识
    private String openid;
    //用户授权作用域
    private String scope;
}
