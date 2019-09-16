drop table if exists city;

create table city
(
    id      int primary key auto_increment,
    name    varchar(50),
    state   varchar(20),
    country varchar(50)
);
drop table if exists hotel;
create table hotel
(
    city    int,
    name    varchar(50),
    address varchar(50),
    zip     varchar(50)
);

drop table if exists `sys_user`;
create table `sys_user` (
  `id` int(11) not null auto_increment comment '用户id',
  `username` varchar(20) not null default '' comment '用户名称',
  `telephone` varchar(13) not null default '' comment '手机号',
  `mail` varchar(20) not null default '' comment '邮箱',
  `password` varchar(40) not null default '' comment '加密后的密码',
  `dept_id` int(11) not null default '0' comment '用户所在部门的id',
  `status` int(11) not null default '1' comment '状态，1：正常，0：冻结状态，2：删除',
  `remark` varchar(200) default '' comment '备注',
  `operator` varchar(20) not null default '' comment '操作者',
  `operate_time` datetime not null default current_timestamp comment '最后一次更新时间',
  `operate_ip` varchar(20) not null default '' comment '最后一次更新者的ip地址',
  primary key (`id`)
) engine=innodb auto_increment=6 default charset=utf8mb4;




