insert into city (name, state, country)
values ('san francisco', 'ca', 'us');
insert into hotel(city, name, address, zip)
values (1, 'conrad treasury place', 'william & george streets', '4001')

begin;
insert into `sys_user` values ('1', 'admin', '18612344321', 'admin@qq.com', '25d55ad283aa400af464c76d713c07ad', '1', '1', 'admin', 'system', '2017-10-13 08:46:16', '127.0.0.1'), ('2', 'jimin', '13188889999', 'jimin@qq.com', '25d55ad283aa400af464c76d713c07ad', '1', '1', 'jimin.zheng', 'admin', '2017-10-14 14:45:19', '127.0.0.1'), ('3', 'jimmy', '13812344311', 'jimmy@qq.com', '25d55ad283aa400af464c76d713c07ad', '2', '1', '', 'admin', '2017-10-16 12:57:35', '0:0:0:0:0:0:0:1'), ('4', 'kate', '13144445555', 'kate@qq.com', '25d55ad283aa400af464c76d713c07ad', '1', '1', 'sss', 'admin', '2017-10-16 23:02:51', '0:0:0:0:0:0:0:1'), ('5', '服务员a', '18677778888', 'service@qq.com', '25d55ad283aa400af464c76d713c07ad', '12', '1', '', 'admin', '2017-10-17 00:22:15', '0:0:0:0:0:0:0:1');
commit;