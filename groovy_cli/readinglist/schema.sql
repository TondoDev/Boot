CREATE TABLE Book (
  id  identity,
  reader varchar(20) not null,
  isbn varchar(20) not null,
  title varchar(20) not null,
  author varchar(20) not null,
  description varchar(20) not null
);
