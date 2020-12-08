create table if not exists article
(
	id int auto_increment comment '博客id'
		primary key,
	title varchar(255) not null comment '文章标题',
	content text null comment '文章内容',
	create_time datetime not null comment '创建时间',
	update_time datetime not null comment '更新时间',
	is_deleted tinyint(1) default 0 not null comment '是否删除',
	is_discuss tinyint(1) default 1 not null comment '是否开启评论',
	is_open tinyint(1) default 1 not null comment '是否公开',
	read_times int default 0 not null comment '阅读次数',
	viewer_nums int default 0 not null comment '浏览者的人数',
	is_reward tinyint(1) default 1 not null comment '是否开启打赏',
	category_id int default 0 not null comment '分类id'
)
comment '博客文章表';