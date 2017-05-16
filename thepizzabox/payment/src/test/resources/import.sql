INSERT INTO pizzabox.user (first_name, last_name, address, contact_no, username, password, role) VALUES ('Roshni', 'Swadia', 'Pune', '9876546756', 'Roshni', 'Roshni', 'ROLE_GUEST');
INSERT INTO pizzabox.user (first_name, last_name, address, contact_no, username, password, role) VALUES ('Rupali', 'Paunikar', 'Pune', '9087678980', 'Rupali', 'Rupali', 'ROLE_GUEST');

INSERT INTO pizzabox.card_details (user_id, card_number, expiry_date, cvv, balance) VALUES (1, '9999999999999999', '08/23', '123', '200');
INSERT INTO pizzabox.card_details (user_id, card_number, expiry_date, cvv, balance) VALUES (2, '0000000000000000', '08/23', '123', '4000');

INSERT INTO pizzabox.order_details(payment_type, status, total_amount) VALUES (0, 0, 100.0);
INSERT INTO pizzabox.order_details(payment_type, status, total_amount) VALUES (1, 0, 100.0);
INSERT INTO pizzabox.order_details(payment_type, status, total_amount) VALUES (1, 0, 10000.0);



