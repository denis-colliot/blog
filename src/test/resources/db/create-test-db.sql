CREATE sequence hibernate_sequence START 1;

CREATE TABLE t_post_po (
    po_id bigint not null,
    po_subject varchar not null,
    po_content varchar not null,
    creation_date timestamp not null,
    creation_user varchar,
    update_date timestamp,
    update_user varchar,
    CONSTRAINT pk_t_post_po PRIMARY KEY(po_id)
);

CREATE TABLE t_user_us (
    us_id bigint not null,
    us_name varchar not null,
    us_first_name varchar not null,
    us_login varchar not null,
    us_password varchar not null,
    creation_date timestamp not null,
    creation_user varchar,
    update_date timestamp,
    update_user varchar,
    CONSTRAINT pk_t_user_us PRIMARY KEY(us_id),
    CONSTRAINT un_us_login UNIQUE(us_login)
);

CREATE TABLE t_authentication_au (
    au_id varchar not null,
    us_id bigint not null,
    au_date_created timestamp not null,
    au_date_last_active timestamp not null,
    creation_date timestamp not null,
    creation_user varchar,
    update_date timestamp,
    update_user varchar,
    CONSTRAINT pk_t_authentication_au PRIMARY KEY(au_id),
    CONSTRAINT fk_au_us FOREIGN KEY(us_id) REFERENCES t_user_us(us_id)
);