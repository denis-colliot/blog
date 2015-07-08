CREATE sequence hibernate_sequence START 1;

CREATE TABLE t_post_po (
    po_id bigint not null,
    po_subject text not null,
    po_content text not null,
    creation_date timestamp not null,
    creation_user text,
    update_date timestamp,
    update_user text,
    CONSTRAINT pk_t_post_po PRIMARY KEY(po_id)
);

CREATE TABLE t_user_us (
    us_id bigint not null,
    us_name text not null,
    us_first_name text not null,
    us_login text not null,
    us_password text not null,
    creation_date timestamp not null,
    creation_user text,
    update_date timestamp,
    update_user text,
    CONSTRAINT pk_t_user_us PRIMARY KEY(us_id),
    CONSTRAINT un_us_login UNIQUE(us_login)
);

CREATE TABLE t_authentication_au (
    au_id text not null,
    us_id bigint not null,
    au_date_created timestamp not null,
    au_date_last_active timestamp not null,
    creation_date timestamp not null,
    creation_user text,
    update_date timestamp,
    update_user text,
    CONSTRAINT pk_t_authentication_au PRIMARY KEY(au_id),
    CONSTRAINT fk_au_us FOREIGN KEY(us_id) REFERENCES t_user_us(us_id)
);