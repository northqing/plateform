insert into plateform.sys_user(id, username, password, salt, name,state, deleted) value (1, 'admin','b0a4cde8cd411b4abcbb05dee41f0995', '453d502d720255d5', '系统管理员', 1,0);
insert into plateform.sys_role(id, role, name,description, deleted) values(1, 'administrator','系统管理员组','系统管理员组',0);
insert into plateform.sys_user_role(role_id, user_id) values(1,1);
insert into plateform.sys_permission(id, name, resource_type,url,permission,parent_id, deleted) values(1, '基础平台子系统','menu','/sys', 'role:view', null, 0);
insert into plateform.sys_permission(id, name, resource_type,url,permission,parent_id, deleted) values(2, '系统管理','menu','/sys', 'role:view', 1, 0);
insert into plateform.sys_permission(id, name, resource_type,url,permission,parent_id, deleted) values(3, '用户管理','menu','/sys/user', 'role:*', 2, 0);
insert into plateform.sys_permission(id, name, resource_type,url,permission,parent_id, deleted) values(4, '角色管理','menu','/sys/role', 'role:*', 2, 0);
insert into plateform.sys_permission(id, name, resource_type,url,permission,parent_id, deleted) values(5, '部门管理','menu','/sys/organization', 'role:*', 2, 0);
insert into plateform.sys_permission(id, name, resource_type,url,permission,parent_id, deleted) values(6, '菜单管理','menu','/sys/menu', 'role:*', 2, 0);
insert into plateform.sys_permission(id, name, resource_type,url,permission,parent_id, deleted) values(7, '配置管理','menu','/config', 'role:view', 1, 0);
insert into plateform.sys_permission(id, name, resource_type,url,permission,parent_id, deleted) values(8, '系统配置','menu','/config/sys', 'role:view', 7, 0);
insert into plateform.sys_role_permission(role_id, permission_id) values(1,1);
insert into plateform.sys_role_permission(role_id, permission_id) values(1,2);
insert into plateform.sys_role_permission(role_id, permission_id) values(1,3);
insert into plateform.sys_role_permission(role_id, permission_id) values(1,4);
insert into plateform.sys_role_permission(role_id, permission_id) values(1,5);
insert into plateform.sys_role_permission(role_id, permission_id) values(1,6);
insert into plateform.sys_role_permission(role_id, permission_id) values(1,7);
insert into plateform.sys_role_permission(role_id, permission_id) values(1,8);
insert into plateform.sys_organization(id, name, code,deleted) values(1, '广东新康博思信息技术有限公司','suncompass',0);