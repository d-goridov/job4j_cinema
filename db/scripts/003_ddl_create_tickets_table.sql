CREATE TABLE tickets (
    id SERIAL PRIMARY KEY,
    session_id INT NOT NULL REFERENCES sessions(id),
    pos_row INT NOT NULL,
    cell INT NOT NULL,
    user_id INT NOT NULL REFERENCES users(id),
    CONSTRAINT place_unique UNIQUE(session_id, pos_row, cell)
);

comment on table tickets is 'Билеты';
comment on column tickets.id is 'Идентификатор билета';
comment on column tickets.session_id is 'Идентификатор сеанса';
comment on column tickets.pos_row is 'Номер ряда';
comment on column tickets.cell is 'Номер места';
comment on column tickets.user_id is 'Идентификатор пользователя';