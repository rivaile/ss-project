package com.rainbow.rbac.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * @author denglin
 * @version V1.0
 * @Description:
 * @ClassName: RbacService
 * @date 2018/9/25 17:13
 */
public interface RbacService {

    boolean hasPermission(HttpServletRequest request, Authentication authentication);

}
