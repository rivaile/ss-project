package com.rainbow.core.validate.code;

import com.rainbow.core.properties.SecurityConstants;

/**
 * @author denglin
 * @version V1.0
 * @Description: 校验码类型
 * @ClassName: ValidateCodeType
 * @date 2018/9/17 17:52
 */

public enum ValidateCodeType {

    SMS {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
        }
    },
    IMAGE {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
        }
    };

    /**
     * 校验时从请求中获取的参数的名字
     */
    public abstract String getParamNameOnValidate();

}
