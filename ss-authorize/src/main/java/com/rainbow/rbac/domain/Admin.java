package com.rainbow.rbac.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.Transient;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author denglin
 * @version V1.0
 * @Description:
 * @ClassName: Admin
 * @date 2018/9/25 10:35
 */
//@Entity
public class Admin implements UserDetails {

//    @Id
//    @GeneratedValue
    private Long id;

    private String username;

    private String password;

    /**
     * 用户有权访问的所有url，不持久化到数据库
     */
//    @Transient
    private Set<String> urls = new HashSet<>();
    /**
     * 用户有权的所有资源id，不持久化到数据库
     */
//    @Transient
    private Set<Long> resourceIds = new HashSet<>();

    public Set<String> getUrls() {
        return urls;
    }

    public Set<Long> getResourceIds() {
        return resourceIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
