INSERT INTO pizzabox.item (name, price, type) VALUES ('Deluxe Veggie', 500, '0');
INSERT INTO pizzabox.item (name, price, type) VALUES ('5 pepper', 450, '0');
INSERT INTO pizzabox.item (name, price, type) VALUES ('Exotica', 525, '0');

INSERT INTO pizzabox.item (name, price, type) VALUES ('Garlic Breadsticks', 200, '1');
INSERT INTO pizzabox.item (name, price, type) VALUES ('Cheesy Dip', 20, '1');
INSERT INTO pizzabox.item (name, price, type) VALUES ('White Pasta', 250, '1');

INSERT INTO pizzabox.item (name, price, type) VALUES ('Virgin Mojito', 100, '2');
INSERT INTO pizzabox.item (name, price, type) VALUES ('Lemonade', 125, '2');
INSERT INTO pizzabox.item (name, price, type) VALUES ('Pepsi', 99, '2');

INSERT INTO pizzabox.user (first_name, last_name, address, contact_no) VALUES ('Roshni', 'Swadia', 'Pune', '9876546756');
INSERT INTO pizzabox.user (first_name, last_name, address, contact_no) VALUES ('Rupali', 'Paunikar', 'Pune', '9087678980');

INSERT INTO pizzabox.card_details (user_id, card_number, expiry_date, cvv, balance) VALUES (1, '9999999999999999', '08/23', '123', '200');
INSERT INTO pizzabox.card_details (user_id, card_number, expiry_date, cvv, balance) VALUES (2, '0000000000000000', '08/23', '123', '4000');





INSERT INTO pizzabox.login_details VALUES ('roshni', 'guest', 'roshni', 1);
INSERT INTO pizzabox.login_details VALUES ('rupali', 'guest', 'rupali', 2);
