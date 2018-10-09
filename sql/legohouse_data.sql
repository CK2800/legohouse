-- customers.
INSERT INTO legohouse.customers(username, email, password) VALUES('Claus', 'claus@dreaming.dk', 'Kramath');
-- bricks.
INSERT INTO bricks(length, width) values(1,2);
INSERT INTO bricks(length, width) values(2,4);
INSERT INTO bricks(length, width) values(2,2);
-- orders.
INSERT INTO orders(customerId) VALUES(1);
-- lineitems.
INSERT INTO lineitems(orderId, brickId, qty) VALUES(1, 1, 1);
INSERT INTO lineitems(orderId, brickId, qty) VALUES(1, 2, 3);
