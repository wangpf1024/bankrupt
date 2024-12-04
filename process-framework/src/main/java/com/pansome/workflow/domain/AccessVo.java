package com.pansome.workflow.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
public class AccessVo implements UserDetails {

    @ApiModelProperty(name = "accessName" , value = "系统标示")
    private String accessName;

    @ApiModelProperty(name = "accessKey" , value = "密钥")
    private String accessKey;

    /**
     * 用户唯一标识
     */
    private String token;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return accessKey;
    }

    @Override
    public String getUsername() {
        return accessName;
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
