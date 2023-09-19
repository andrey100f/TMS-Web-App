package com.example.tms_app.models;

import java.util.UUID;

public class UserRole {
    private UUID roleId;
    private String roleName;
    private Integer userRoleVersion;

    public UserRole() {
    }

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getUserRoleVersion() {
        return userRoleVersion;
    }

    public void setUserRoleVersion(Integer userRoleVersion) {
        this.userRoleVersion = userRoleVersion;
    }
}
