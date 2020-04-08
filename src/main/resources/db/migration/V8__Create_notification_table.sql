create table notification
(
    id bigint auto_increment primary key,
    notifier bigint not null,
    notifier_name varchar(100) not null,
    receiver bigint not null,
    outer_id bigint not null,
    outer_title varchar(256) not null,
    type int not null,
    gmt_create bigint not null,
    status int default 0 not null
);