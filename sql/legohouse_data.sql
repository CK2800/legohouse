-- customers.
INSERT INTO legohouse.users(username, email, password, employee) VALUES('Claus', 'claus@dreaming.dk', 'Kramath', true);
-- bricks.
INSERT INTO bricks(length, width) values(1,2);
INSERT INTO bricks(length, width) values(2,2);
INSERT INTO bricks(length, width) values(4,2);
-- orders.
INSERT INTO orders(userId, length, width, height) VALUES(1, 10, 5, 4);
-- lineitems.
INSERT INTO lineitems(orderId, brickId, qty) VALUES(1, 1, 1);
INSERT INTO lineitems(orderId, brickId, qty) VALUES(1, 2, 3);
