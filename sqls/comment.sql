create table if not exists comment
(
	id int auto_increment comment '评论id'
		primary key,
	comment varchar(1024) default '' not null comment '评论内容',
	article_id int not null comment '文章id',
	mail varchar(50) null comment '评论者的邮箱',
	site varchar(255) null comment '评论者的网站站点',
	nickname varchar(50) default 'Anonymous' not null comment '评论人的昵称',
	parent_id int default 0 not null comment '根评论的id',
	reply_id int default 0 not null comment 'reply的id',
	create_time datetime not null comment '创建时间',
	is_deleted tinyint(1) default 0 not null comment '是否删除'
)
comment '评论表';