-- Insert admin user (password is 'Password123!' encoded with BCrypt)
INSERT INTO users (version, created_date, email, password, role, first_name, last_name, phone_number, address, created_by)
VALUES (0, CURRENT_TIMESTAMP, 'admin@store.com', 
'$2a$10$pTNYzkDt.lRssRfBymnq..mOeBRGVbbkn.c8r6zpLrlrI7QzR6xza', 
'ADMIN', 'Admin', 'User', '+1234567890', '123 Admin Street', 'system');

-- Insert customer user (password is 'Password123!' encoded with BCrypt)
INSERT INTO users (version, created_date, email, password, role, first_name, last_name, phone_number, address, created_by)
VALUES (0, CURRENT_TIMESTAMP, 'customer@store.com', 
'$2a$10$pTNYzkDt.lRssRfBymnq..mOeBRGVbbkn.c8r6zpLrlrI7QzR6xza', 
'CUSTOMER', 'John', 'Doe', '+1987654321', '456 Customer Ave', 'system');

-- Discount
INSERT INTO discounts (version, created_date, updated_date, created_by, updated_by, type, description, required_quantity, discounted_quantity, discount_value) VALUES
(0, CURRENT_TIMESTAMP, NULL, 'system', NULL, 'BUY_1_GET_50_PERCENT_OFF', 'Buy one product and get 50% off on the second product', 1, 1, 50.00),
(0, CURRENT_TIMESTAMP, NULL, 'system', NULL, 'BUY_10_GET_1_FREE', 'Buy 2 products and get 1 product free', 10, 1, 100.00),
(0, CURRENT_TIMESTAMP, NULL, 'system', NULL, 'FLAT_PERCENTAGE', 'Get a flat percentage discount on all products', 0, 999999, 10.00);