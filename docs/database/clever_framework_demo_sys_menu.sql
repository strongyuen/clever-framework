INSERT INTO clever_framework_demo.sys_menu (id, create_time, create_user_id, last_update_time, last_update_user_id,
                                            code, deleted, has_children, level, name, parent_id, parent_ids, sort_num)
VALUES (1, '2019-06-06 11:11:11', null, '2019-06-06 11:11:11', 1, 'root', false, true, 0, '根菜单', null, null, 1);
INSERT INTO clever_framework_demo.sys_menu (id, create_time, create_user_id, last_update_time, last_update_user_id,
                                            code, deleted, has_children, level, name, parent_id, parent_ids, sort_num)
VALUES (101, '2019-06-06 11:11:11', 1, '2019-06-06 11:11:11', 1, 'system', false, true, 1, '系统管理', 1, '1', 1);
INSERT INTO clever_framework_demo.sys_menu (id, create_time, create_user_id, last_update_time, last_update_user_id,
                                            code, deleted, has_children, level, name, parent_id, parent_ids, sort_num)
VALUES (10101, '2019-06-06 11:11:11', 1, '2019-06-06 11:11:11', 1, 'user', false, false, 2, '用户管理', 101, '1,101', 1);
INSERT INTO clever_framework_demo.sys_menu (id, create_time, create_user_id, last_update_time, last_update_user_id,
                                            code, deleted, has_children, level, name, parent_id, parent_ids, sort_num)
VALUES (10102, '2019-06-06 11:11:11', 1, '2019-06-06 11:11:11', 1, 'role', false, false, 2, '角色管理', 101, '1,101', 1);
INSERT INTO clever_framework_demo.sys_menu (id, create_time, create_user_id, last_update_time, last_update_user_id,
                                            code, deleted, has_children, level, name, parent_id, parent_ids, sort_num)
VALUES (10103, '2019-06-06 11:11:11', 1, '2019-06-06 11:11:11', 1, 'menu', false, false, 2, '菜单管理', 101, '1,101', 1);
