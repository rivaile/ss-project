package com.rainbow.core.authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


/**
 * @author denglin
 * @version V1.0
 * @Description: 不做任何处理，只在控制台打印一句日志，然后抛出异常，提醒业务系统自己配置 UserDetailsService。
 * @ClassName: AuthenticationBeanConfig
 * @date 2018/9/18 16:29
 */
public class DefaultUserDetailsService implements UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.warn("请配置 UserDetailsService 接口的实现.");
		throw new UsernameNotFoundException(username);
	}

}
