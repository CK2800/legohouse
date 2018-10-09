DROP DATABASE IF EXISTS legohouse;

CREATE DATABASE legohouse;
use legohouse;

DROP TABLE IF EXISTS lineitems;
DROP TABLE IF EXISTS bricks;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS customers;

CREATE TABLE customers(
	id int PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(30) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(30) NOT NULL    
);

CREATE TABLE orders(
	id int PRIMARY KEY AUTO_INCREMENT,
    customerId int NOT NULL,
    orderDate datetime NOT NULL DEFAULT NOW(), -- order date defaults to now().
    shippedDate datetime, -- order shipment date
    CONSTRAINT fk_orders_customers
    FOREIGN KEY(customerId)
    REFERENCES customers(id)
    ON DELETE NO ACTION -- a customer with orders cannot be deleted.
);

CREATE TABLE bricks(
	id int PRIMARY KEY AUTO_INCREMENT,
    length int NOT NULL,
    width int NOT NULL
);

CREATE TABLE lineitems(
	orderId int NOT NULL,
    brickId int NOT NULL,
    qty int NOT NULL,
    PRIMARY KEY(orderId, brickId), -- composite primary key
    CONSTRAINT fk_lineitems_orders
    FOREIGN KEY(orderId)
    REFERENCES orders(id)
    ON DELETE CASCADE, -- when an order is deleted, so are its lineitems.
    CONSTRAINT fk_lineitems_bricks
    FOREIGN KEY(brickId)
    REFERENCES bricks(id)
    ON DELETE NO ACTION -- a brick that has been ordered (and as such are referenced by lineitems), cannot be deleted.
);

