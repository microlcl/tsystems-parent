package com.tsystems.core.constant;

/**
 * 
 * @author Lichunlei
 *
 */
public enum ResponseCode {
    /**操作成功**/
    RC100("100","success"),
    /**操作失败**/
    RC999("999","fail"),

    RC204("204","授权规则不通过,请稍后再试!"),
    
    RC301("301","parameter error"),
    
    /**access_denied**/
    RC403("403","You have no access to visit this resource"),

    /**服务异常**/
    RC500("500","backend service error"),
    RC501("501","service error"),

    INVALID_TOKEN("4001","invalid token"),
    ACCESS_DENIED("2003","没有权限访问该资源"),
    USERNAME_OR_PASSWORD_ERROR("4002","username or password is not correct, or you are locked"),
	LDAP_CONNECTION_TIMEOUT("4003","LDAP Connection timed out"),
	JWT_AUTHENTICATION_FAIL("4004","JWT authentication fail"),
	JWT_EXPIRED("4005","token expired, please login again"),
	JWT_INVALID("4006","invalid JWT");
	

    /**自定义状态码**/
    private final String code;
    /**自定义描述**/
    private final String message;

    ResponseCode(String code, String message){
        this.code = code;
        this.message = message;
    }


    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
