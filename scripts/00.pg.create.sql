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