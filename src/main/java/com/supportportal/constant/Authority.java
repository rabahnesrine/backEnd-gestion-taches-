package com.supportportal.constant;

public class Authority {
    public static final String[] MEMBER_AUTHORITIES= { "user:read","projet:read" };
    public static final String[] CHEF_AUTHORITIES = { "user:read","projet:read" ,"sprint:read","sprint:update"};
    public static final String[] SCRUM_MASTER_AUTHORITIES = { "user:read", "user:update","user:create","projet:read","projet:create","projet:update","projet:delete","sprint:read","sprint:create","sprint:update","sprint:delete" };
    public static final String[] ADMIN_AUTHORITIES = { "user:read", "user:create", "user:update" , "user:delete","projet:read","projet:create","projet:update","projet:delete","sprint:read","sprint:create","sprint:update","sprint:delete"};
}
