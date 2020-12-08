create table if not exists tag
(
	id int auto_increment comment '标签id'
		primary key,
	label varchar(50) not null comment '标签内容',
	create_time datetime not null comment '创建时间'
)
comment '文章标签表';
