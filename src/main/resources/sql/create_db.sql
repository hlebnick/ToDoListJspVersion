drop table if exists users;
drop table if exists todo_list;
drop table if exists todo_item;

create table users(
  id int primary key,
  email varchar(255),
  password varchar(128)
);

create table todo_list(
  id int primary key,
  user_id int,
  list_name varchar(255),
  foreign key(user_id) references users(id)
);

create table todo_item(
  id int primary key,
  list_id int,
  item_name varchar(255),
  done boolean,
  foreign key(list_id) references todo_list(id)
);