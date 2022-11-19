CREATE TABLE IF NOT EXISTS sessions (
  id SERIAL PRIMARY KEY,
  name text
);

comment on table sessions is 'Сеансы';
comment on column sessions.id is 'Идентификатор сеанса';
comment on column sessions.name is 'Название сеанса';