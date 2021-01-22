create table if not exists tag_article
(
	id int auto_increment comment 'id'
		primary key,
	article_id int not null comment 'id',
	tag_name varchar(255) not null,
	create_time datetime not null
);

