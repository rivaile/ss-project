package com.rainbow.core.support;

/**
 * @author denglin
 * @version V1.0
 * @Description:
 * @ClassName: SimpleResponse
 * @date 2018/9/17 14:45
 */

public class SimpleResponse {

    public SimpleResponse(Object content) {
        this.content = content;
    }

    private Object content;

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

}
