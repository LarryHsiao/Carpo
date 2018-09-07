create table if not exists files (
  id   integer primary key autoincrement,
  name text not null unique
);

create table if not exists tags (
  id   integer primary key autoincrement,
  name text not null unique
);

create table if not exists file_tag (
  id      integer primary key autoincrement,
  file_id integer not null,
  tag_id  integer not null,
  unique (file_id, tag_id)
);

select *
from tags;
select *
from file_tag;
select *
from files;

-- Sample data
insert into files(name) values ('filename');
insert into files(name) values ('filename2');
insert into tags(name) values ('tag');
insert into tags(name) values ('tag2');
insert into file_tag(file_id, tag_id) values (1,1);
insert into file_tag(file_id, tag_id) values (1,2);
insert into file_tag(file_id, tag_id) values (2,2);

-- Use case: File tags by file name
select tags.name
from tags
join files as file, file_tag
where file.name = 'filename2' and tags.id = file_tag.tag_id and file.id = file_tag.file_id;