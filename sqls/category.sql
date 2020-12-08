create table if not exists category
(
	id int auto_increment comment '分类id'
		primary key,
	name varchar(50) not null comment '分类名称',
	parent_id int default 0 not null comment '父级分类id',
	create_time datetime not null comment '创建时间'
)
comment '分类表';