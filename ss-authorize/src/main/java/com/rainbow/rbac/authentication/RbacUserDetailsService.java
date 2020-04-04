package com.rainbow.rbac.authentication;

import com.rainbow.rbac.domain.Admin;
//import com.rainbow.rbac.repository.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author denglin
 * @version V1.0
 * @Description:
 * @ClassName: RbacUserDetailsService
 * @date 2018/9/25 11:32
 */
@Component
@Transactional
public class RbacUserDetailsService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
//    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("表单登录用户名:" + username);
//        Admin admin = adminRepository.findByUsername(username);
        return null;
    }
}
