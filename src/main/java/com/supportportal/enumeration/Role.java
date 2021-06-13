package com.supportportal.enumeration;

import static com.supportportal.constant.Authority.*;

public enum Role {
    ROLE_MEMBER(MEMBER_AUTHORITIES),
    ROLE_CHEF(CHEF_AUTHORITIES),
    ROLE_SCRUM_MASTER(SCRUM_MASTER_AUTHORITIES),
    ROLE_ADMIN(ADMIN_AUTHORITIES);

    private String[] authorities;

    Role(String... authorities) {
        this.authorities = authorities;
    }

    public String[] getAuthorities() {
        return authorities;
    }
}
