INSERT INTO customer (id, first_name, last_name) VALUES (1, 'Allen', 'Border');
INSERT INTO customer (id, first_name, last_name) VALUES (2, 'David', 'Boon');

INSERT INTO transaction (id, bill_date, bill_amt, customer_id)  VALUES (1, '2022-01-31', 50.00, 1);
INSERT INTO transaction (id, bill_date, bill_amt, customer_id)  VALUES (11, '2022-02-28', 90.00, 1);
INSERT INTO transaction (id, bill_date, bill_amt, customer_id)  VALUES (111, '2022-03-31', 120.00, 1);

INSERT INTO transaction (id, bill_date, bill_amt, customer_id)  VALUES (2, '2022-01-31', 70.00, 2);
INSERT INTO transaction (id, bill_date, bill_amt, customer_id)  VALUES (22, '2022-02-28', 100.00, 2);
INSERT INTO transaction (id, bill_date, bill_amt, customer_id)  VALUES (222, '2022-03-31', 120.00, 2);