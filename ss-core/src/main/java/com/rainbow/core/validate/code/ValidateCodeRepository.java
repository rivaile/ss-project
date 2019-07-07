/**
 * 
 */
package com.rainbow.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author denglin
 * @version V1.0
 * @Description: /**
 * @ClassName: ValidateCodeRepository
 * @date 2018/9/17 17:54
 */
public interface ValidateCodeRepository {

	/**
	 * 保存验证码
	 * @param request
	 * @param code
	 * @param validateCodeType
	 */
	void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType);
	/**
	 * 获取验证码
	 * @param request
	 * @param validateCodeType
	 * @return
	 */
	ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType);
	/**
	 * 移除验证码
	 * @param request
	 * @param codeType
	 */
	void remove(ServletWebRequest request, ValidateCodeType codeType);

}
