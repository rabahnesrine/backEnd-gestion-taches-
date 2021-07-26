package com.supportportal.constant;

public class Authority {
    public static final String[] MEMBER_AUTHORITIES= { "user:read","projet:read" ,"task:read","task:update"};
    public static final String[] CHEF_AUTHORITIES = { "user:read","projet:read" ,"sprint:read","sprint:update","task:read","task:create","task:update","task:delete"};
    public static final String[] SCRUM_MASTER_AUTHORITIES = { "user:update","user:create","projet:create","projet:update","projet:delete","sprint:create","sprint:update","sprint:delete","task:create","task:update","task:delete" };
    public static final String[] ADMIN_AUTHORITIES = {  "user:create", "user:update" , "user:delete","projet:create","projet:update","projet:delete","sprint:create","sprint:update","sprint:delete","task:create","task:update","task:delete"};
}
