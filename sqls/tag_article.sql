create table if not exists tag_article
(
	id int auto_increment comment '对应关系id'
		primary key,
	article_id int not null comment '文章id',
	label_id int not null comment '标签id',
	create_time datetime not null comment '创建时间'
)
comment '文章与标签的对应关系表';
