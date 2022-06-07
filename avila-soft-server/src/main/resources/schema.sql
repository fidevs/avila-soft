CREATE TABLE IF NOT EXISTS oauth_client_details (
    client_id VARCHAR(255) PRIMARY KEY,
    resource_ids VARCHAR(255),
    client_secret VARCHAR(255),
    scope VARCHAR(255),
    authorized_grant_types VARCHAR(255),
    web_server_redirect_uri VARCHAR(255),
    authorities VARCHAR(255),
    access_token_validity INTEGER,
    refresh_token_validity INTEGER,
    additional_information VARCHAR(4096),
    autoapprove varchar(255)
);

CREATE TABLE IF NOT EXISTS oauth_client_token (
    token_id VARCHAR(255),
    token bytea,
    authentication_id VARCHAR(255),
    user_name VARCHAR(255),
    client_id VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS oauth_access_token (
    token_id VARCHAR(255),
    token bytea,
    authentication_id VARCHAR(255),
    user_name VARCHAR(255),
    client_id VARCHAR(255),
    authentication bytea,
    refresh_token VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS oauth_refresh_token (
    token_id VARCHAR(255),
    token bytea,
    authentication bytea
);

CREATE TABLE IF NOT EXISTS oauth_code (
    code VARCHAR(255), authentication bytea
);

CREATE TABLE IF NOT EXISTS authorizations (
    auth_id varchar(36) not null,
    auth varchar(10) not null,
    description varchar(20) not null,
    primary key (auth_id)
    );

CREATE TABLE IF NOT EXISTS role_permission (
    role_id varchar(36) not null,
    auth_id varchar(36) not null,
    primary key (auth_id, role_id)
    ):

CREATE TABLE IF NOT EXISTS soft_user (
    uid varchar(36) not null,
    is_active boolean not null,
    email varchar(255) not null,
    password varchar(255) not null,
    user_name varchar(20) not null,
    role_id varchar(36) not null,
    primary key (uid)
    );

CREATE TABLE IF NOT EXISTS usr_config (
    config_id varchar(36) not null,
    created_at timestamp not null,
    last_update int8 not null,
    uid varchar(36) not null,
    primary key (config_id)
    );

CREATE TABLE IF NOT EXISTS usr_details (
    detail_id varchar(36) not null,
    date_of_birth date not null,
    last_name varchar(80) not null,
    name varchar(50) not null,
    zip_code varchar(6) not null,
    uid varchar(36) not null,
    primary key (detail_id)
    );

CREATE TABLE IF NOT EXISTS usr_role (
    role_id varchar(36) not null,
    name varchar(15) not null,
    primary key (role_id)
    );

CREATE TABLE IF NOT EXISTS usr_transaction_token (
    token_id varchar(36) not null,
    created_date timestamp not null,
    valid_until timestamp not null,
    token_type varchar(20) not null,
    uid varchar(36) not null,
    primary key (token_id)
    );

ALTER TABLE role_permission
    add constraint FKmllkuf459smji6pr70h95du6j
        foreign key (role_id)
            references usr_role;

ALTER TABLE role_permission
    add constraint FKxhnuan2nmdk58njs7w1ncd2i
        foreign key (auth_id)
            references authorizations;

ALTER TABLE soft_user
    add constraint FKgkbxwn1egjav6unxugy1nrctt
        foreign key (role_id)
            references usr_role;

ALTER TABLE usr_config
    add constraint FK2j4eck975gcfjdj03kfjqsm7h
        foreign key (uid)
            references soft_user;

ALTER TABLE usr_details
    add constraint FK3h5e4w0lq61612g6iyma7eu45
        foreign key (uid)
            references soft_user;

ALTER TABLE usr_transaction_token
    add constraint FK3etghivjp9s8gp0xrv4xwwc4m
        foreign key (uid)
            references soft_user;
