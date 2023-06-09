-- liquibase formatted sql

-- changeset anton:1
CREATE TABLE lot
(
    id BIGINT NOT NULL generated by default as identity,
    primary key (id),
    status VARCHAR(255) NOT NULL,
    title VARCHAR(64) NOT NULL,
    description VARCHAR(4096) NOT NULL,
    start_price INTEGER CHECK ( start_price > 0 ),
    bid_price INTEGER CHECK( start_price > 0 )
)



