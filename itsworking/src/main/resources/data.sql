drop table if exists posts_t;
drop table if exists users_t;
drop table if exists comments_t;

create table users_t
(
    id        bigserial    not null primary key,
    name      varchar(100) not null,
    password  varchar(100) not null,
    info      text,
    join_time timestamp
);

create table posts_t
(
    id          bigserial not null primary key,
    user_id     bigint    not null,
    create_time timestamp not null,
    edit_time   timestamp not null,
    title       varchar(100),
    content     text,
    foreign key (user_id) references users_t (id)
);

create table comments_t
(
    id          bigserial not null primary key,
    post_id     bigint    not null,
    user_id     bigint    not null,
    create_time timestamp not null,
    content     text,
    foreign key (post_id) references posts_t (id),
    foreign key (user_id) references users_t (id)
);