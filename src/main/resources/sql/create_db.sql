drop table if exists users;
drop table if exists persistent_logins;
drop table if exists todo_list;
drop table if exists todo_item;

create table users(
  id int auto_increment primary key,
  email varchar(255),
  password varchar(128)
);

create table persistent_logins (
    username varchar(64) not null,
    series varchar(64) not null,
    token varchar(64) not null,
    last_used TIMESTAMP not null,
    primary key (series)
);

create table todo_list(
  id int auto_increment primary key,
  user_id int,
  list_name varchar(255),
  foreign key(user_id) references users(id)
);

create table todo_item(
  id int auto_increment primary key,
  list_id int,
  item_name varchar(255),
  done boolean,
  foreign key(list_id) references todo_list(id)
);