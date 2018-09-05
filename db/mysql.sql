
-- 系统用户
CREATE TABLE `sys_user` (
  `id` varchar(36) primary key NOT NULL,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) COMMENT '密码',
  `salt` varchar(20) COMMENT '盐',
  `email` varchar(100) COMMENT '邮箱',
  `name` varchar(100) COMMENT '人员名称/昵称',
  `mobile` varchar(100) COMMENT '手机号',
  `status` tinyint COMMENT '状态  0：正常   1：禁用',
  `create_user_id`varchar(36) COMMENT '创建者ID',
  `create_time` datetime COMMENT '创建时间',
  UNIQUE INDEX (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户';



-- 角色
CREATE TABLE `sys_role` (
  `id` varchar(36) primary key NOT NULL,
  `name` varchar(100) COMMENT '角色名称',
  `create_user_id` varchar(36) COMMENT '创建者ID',
  `create_time` datetime COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色';


-- 权限（资源和权限合并）
CREATE TABLE `sys_permission` (
  `id` varchar(36) primary key NOT NULL,
  `parent_id` varchar(36) COMMENT '父菜单ID，一级菜单为null',
  `name` varchar(100) COMMENT '名称',
  `url` varchar(200) COMMENT '菜单URL',
  `perms` varchar(500) COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) COMMENT '菜单图标',
  `order_num` int COMMENT '排序'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限';



-- 用户与角色对应关系
CREATE TABLE `sys_user_role` (
  `id` varchar(36)  primary key NOT NULL,
  `user_id` varchar(36) COMMENT '用户ID',
  `role_id` varchar(36) COMMENT '角色ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系';



-- 角色与权限对应关系
CREATE TABLE `sys_role_permission` (
  `id` varchar(36) primary key NOT NULL,
  `role_id` varchar(36) COMMENT '角色ID',
  `permission_id` varchar(36) COMMENT '菜单ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色与菜单对应关系';




INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES
	('001', '', '系统管理', '', NULL, 0, 'shouye', NULL),
	('002', '001', '用户管理', 'sys/user', NULL, 1, 'tubiao', NULL),
	('003', '002', '查看', NULL, 'sys:user:list,sys:user:info', 2, NULL, NULL),
	('004', '002', '修改', NULL, 'sys:user:update', 2, NULL, NULL),
	('005', '002', '删除', NULL, 'sys:user:delete', 2, NULL, NULL),
	('006', '002', '保存', NULL, 'sys:user:save', 2, NULL, NULL);

INSERT INTO `sys_role` (`id`, `name`, `create_user_id`, `create_time`) VALUES
	('001', 'USER', NULL, NULL),
	('002', 'ADMIN', NULL, NULL);


INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`) VALUES
	('1', '002', '001'),
	('2', '002', '002'),
	('3', '002', '003'),
	('4', '002', '004'),
	('5', '002', '005'),
	('6', '002', '006');


INSERT INTO `sys_user` (`id`, `username`, `password`, `salt`, `email`, `name`, `mobile`, `status`, `create_user_id`, `create_time`) VALUES
	('001', 'user1', '$2a$10$AWxWAI.GkzXeWeVDOacKne4OPWhxejy5FDIrdUlTjGzndY5BcFyk6', NULL, '43543@qq.com', '用户1', '18546531354', 0, NULL, '2018-08-29 16:05:09'),
	('002', 'admin', '$2a$10$AWxWAI.GkzXeWeVDOacKne4OPWhxejy5FDIrdUlTjGzndY5BcFyk6', NULL, 'eageag@163.com', '管理员', '16545353123', 0, NULL, '2018-08-29 16:05:09'),
	('8b3b410c4ed24619a9ede4897715f358', 'user2', '$2a$10$APrs8lHBrFmE0kdkACnWseOEDXvrP4HNGTZehkOuU0TcI9mpJGQv2', NULL, 'joealgjek@qq.com', '用户2', '18253453134', 0, NULL, NULL);



INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`) VALUES
	('46215b070b414528aa3b25e8376817f1', '001', '001'),
	('70b0d42e701b4779b82b9e8548c55cdd', '8b3b410c4ed24619a9ede4897715f358', '001'),
	('827500d0c88b4670a6680b07f3a58c61', '002', '002'),
	('86a21b28797a4c96ac5627f7bf87e84b', '002', '001');
