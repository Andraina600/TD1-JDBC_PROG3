CREATE TABLE product(
    id serial primary key,
    name varchar,
    price numeric,
    creation_datetime timestamp
);

CREATE TABLE product_category(
    id serial primary key,
    name varchar,
    product_id int references Product(id)
);