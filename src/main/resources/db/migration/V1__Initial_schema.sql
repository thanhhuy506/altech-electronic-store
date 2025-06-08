CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    version BIGINT NOT NULL DEFAULT 0,
    created_date TIMESTAMP NOT NULL,
    updated_date TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN DEFAULT FALSE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    address TEXT NOT NULL
);

CREATE INDEX idx_users_email ON users(email); 

CREATE TABLE IF NOT EXISTS products (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    version BIGINT NOT NULL DEFAULT 0,
    created_date TIMESTAMP NOT NULL,
    updated_date TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN DEFAULT FALSE,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(19,2) NOT NULL,
    image_url VARCHAR(255),
    category VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS stocks (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    version BIGINT NOT NULL DEFAULT 0,
    created_date TIMESTAMP NOT NULL,
    updated_date TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN DEFAULT FALSE,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    reserved_quantity INT DEFAULT 0,
    sold_quantity INT DEFAULT 0,
    FOREIGN KEY (product_id) REFERENCES products(id),
    CONSTRAINT uc_stock_product UNIQUE (product_id)
);

CREATE INDEX idx_stocks_product_id ON stocks(product_id);

CREATE TABLE IF NOT EXISTS discounts (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    version BIGINT NOT NULL DEFAULT 0,
    created_date TIMESTAMP NOT NULL,
    updated_date TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN DEFAULT FALSE,
    type VARCHAR(50) NOT NULL UNIQUE,
    description TEXT,
    required_quantity INT,
    discounted_quantity INT,
    discount_value DECIMAL(19,2)
);

CREATE TABLE IF NOT EXISTS product_discounts (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    version BIGINT NOT NULL DEFAULT 0,
    created_date TIMESTAMP NOT NULL,
    updated_date TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN DEFAULT FALSE,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    product_id BIGINT,
    discount_id BIGINT,
    valid_from TIMESTAMP NOT NULL,
    valid_to TIMESTAMP,
    CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES products(id),
    CONSTRAINT fk_discount_id FOREIGN KEY (discount_id) REFERENCES discounts(id)
);

-- CREATE TABLE IF NOT EXISTS product_discounts (
--     id BIGINT PRIMARY KEY AUTO_INCREMENT,
--     version BIGINT NOT NULL DEFAULT 0,
--     created_date TIMESTAMP NOT NULL,
--     updated_date TIMESTAMP,
--     created_by VARCHAR(255),
--     updated_by VARCHAR(255),
--     is_deleted BOOLEAN DEFAULT FALSE,
--     type VARCHAR(50) NOT NULL UNIQUE,
--     description TEXT,
--     is_active BOOLEAN DEFAULT TRUE,
--     required_quantity INT,
--     discounted_quantity INT,
--     discount_value DECIMAL(19,2),
--     product_id BIGINT,
--     discount_id BIGINT,
--     valid_from TIMESTAMP NOT NULL,
--     valid_to TIMESTAMP,
--     FOREIGN KEY (product_id) REFERENCES products(id)
--     FOREIGN KEY (discount_id) REFERENCES discounts(id)
-- );

CREATE TABLE IF NOT EXISTS baskets (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    version BIGINT NOT NULL DEFAULT 0,
    created_date TIMESTAMP NOT NULL,
    updated_date TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN DEFAULT FALSE,
    customer_id VARCHAR(255) NOT NULL,
    CONSTRAINT uk_basket_customer UNIQUE (customer_id)
);

CREATE TABLE IF NOT EXISTS basket_items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    version BIGINT NOT NULL DEFAULT 0,
    created_date TIMESTAMP,
    updated_date TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN DEFAULT FALSE,
    basket_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (basket_id) REFERENCES baskets(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE INDEX idx_basket_items_basket ON basket_items(basket_id);
CREATE INDEX idx_basket_items_product ON basket_items(product_id);