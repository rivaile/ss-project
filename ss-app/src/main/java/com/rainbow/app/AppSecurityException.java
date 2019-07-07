package com.rainbow.app;

/**
 * @author denglin
 * @version V1.0
 * @Description:
 * @ClassName: AppSecurityException
 * @date 2018/9/23 15:51
 */
public class AppSecurityException extends RuntimeException {
    private static final long serialVersionUID = -1629364510827838114L;

    public AppSecurityException(String msg){
        super(msg);
    }
}
