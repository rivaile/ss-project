package com.rainbow.rbac.service.impl;

import com.rainbow.rbac.domain.Admin;
import com.rainbow.rbac.dto.AdminCondition;
import com.rainbow.rbac.dto.AdminInfo;
import com.rainbow.rbac.repository.AdminRepository;
import com.rainbow.rbac.service.AdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author denglin
 * @version V1.0
 * @Description:
 * @ClassName: AdminServiceImpl
 * @date 2018/9/25 10:33
 */
@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AdminInfo create(AdminInfo adminInfo) {
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminInfo, admin);
        admin.setPassword(passwordEncoder.encode("123"));
        adminRepository.save(admin);
        adminInfo.setId(admin.getId());

        return adminInfo;
    }

    @Override
    public AdminInfo update(AdminInfo adminInfo) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public AdminInfo getInfo(Long id) {
        return null;
    }

    @Override
    public Page<AdminInfo> query(AdminCondition condition, Pageable pageable) {
        return null;
    }
}
