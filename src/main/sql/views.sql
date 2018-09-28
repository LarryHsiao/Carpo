create table if not exists views (
  id   integer primary key autoincrement,
  name text not null unique
);

create table if not exists view_conditions (
  id      integer primary key autoincrement,
  view_id integer not null,
  tag_id  integer not null,
  type    text    not null, -- include/exclude for now
  unique (view_id, tag_id)

);

select *
from views;

select *
from view_conditions;

-- Use case: New view
insert into views (name)
values ('view1');
insert into views (name)
values ('view2');

-- Use case: Insert new condition
insert into view_conditions (view_id, tag_id, type)
values (1, 1, 'included');
insert into view_conditions (view_id, tag_id, type)
values (1, 2, 'excluded');

-- Use case: Delete a View
delete from views
where id=?;
