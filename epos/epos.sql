drop table if exists USER;

create table USER (
    ID integer primary key,
    USER_ROLE text,
    USER_NAME text,
    USER_PASS text
);

create table PRODUCT (
    ID integer primary key,
    PRODUCT_PRICE integer,
    PRODUCT_QUANTITY integer,
    PRODUCT_IMAGE_BASE64 text
);

