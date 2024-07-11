-- Создание таблицы type_account
CREATE TABLE IF NOT EXISTS type_account (
                                            id_type_account INT AUTO_INCREMENT PRIMARY KEY,
                                            type_of_account VARCHAR(50) NOT NULL
);

-- Вставка начальных данных в таблицу type_account
INSERT INTO type_account(type_of_account)
VALUES ('не активированный пользователь');

-- Создание таблицы users
CREATE TABLE IF NOT EXISTS users (
                                     id_user INT AUTO_INCREMENT PRIMARY KEY,
                                     last_name VARCHAR(50) NOT NULL,
                                     first_name VARCHAR(50) NOT NULL,
                                     middle_name VARCHAR(50) NOT NULL,
                                     phone VARCHAR(50) NOT NULL,
                                     email VARCHAR(50) NOT NULL UNIQUE,
                                     id_type_of_account INT NOT NULL DEFAULT 1,
                                     FOREIGN KEY (id_type_of_account) REFERENCES type_account(id_type_account)
);

-- Создание таблицы company
CREATE TABLE IF NOT EXISTS company (
                                       id_company INT AUTO_INCREMENT PRIMARY KEY,
                                       name_company VARCHAR(50) NOT NULL,
                                       inn VARCHAR(12) NOT NULL,
                                       payment_account_number VARCHAR(20),
                                       bik_bank VARCHAR(9),
                                       id_user INT NOT NULL,
                                       FOREIGN KEY(id_user) REFERENCES users(id_user)
);

-- Создание таблицы category
CREATE TABLE IF NOT EXISTS category (
                                        id_category INT AUTO_INCREMENT PRIMARY KEY,
                                        name_category VARCHAR(50) NOT NULL
);

-- Создание таблицы supplying_company
CREATE TABLE IF NOT EXISTS supplying_company (
                                                 id_supplying_company INT AUTO_INCREMENT PRIMARY KEY,
                                                 name_supplying_company VARCHAR(50) NOT NULL,
                                                 inn_supplying_company VARCHAR(12) NOT NULL
);

-- Создание таблицы supplying
CREATE TABLE IF NOT EXISTS supplying (
                                         id_supplying INT AUTO_INCREMENT PRIMARY KEY,
                                         id_supplying_company INT NOT NULL,
                                         delivery_date DATE NOT NULL,
                                         delivery_amount DECIMAL NOT NULL,
                                         FOREIGN KEY (id_supplying_company) REFERENCES supplying_company(id_supplying_company)
);

-- Создание таблицы products
CREATE TABLE IF NOT EXISTS products (
                                        id_product INT AUTO_INCREMENT PRIMARY KEY,
                                        name_product VARCHAR(50) NOT NULL,
                                        stock_balance INT NOT NULL,
                                        minimum_stock_balance INT NOT NULL,
                                        retail_sale_price DECIMAL,
                                        internet_sale_price DECIMAL,
                                        wholesale_sale_price DECIMAL NOT NULL,
                                        purchase_price DECIMAL NOT NULL,
                                        id_supplying_company INT NOT NULL,
                                        id_category INT NOT NULL,
                                        FOREIGN KEY (id_supplying_company) REFERENCES supplying_company(id_supplying_company),
                                        FOREIGN KEY (id_category) REFERENCES category(id_category)
);

-- Создание таблицы status_order
CREATE TABLE IF NOT EXISTS status_order (
                                            id_status_order INT AUTO_INCREMENT PRIMARY KEY,
                                            status VARCHAR(50) NOT NULL
);

-- Вставка начальных данных в таблицу status_order
INSERT INTO status_order(status)
VALUES ('новая заявка');

-- Создание таблицы sale_order
CREATE TABLE IF NOT EXISTS sale_order (
                                          id_sale_order INT AUTO_INCREMENT PRIMARY KEY,
                                          id_company INT NOT NULL,
                                          data_order DATE NOT NULL,
                                          amount_order DECIMAL NOT NULL,
                                          id_status_order INT NOT NULL DEFAULT 1,
                                          check_link VARCHAR(250),
                                          FOREIGN KEY (id_company) REFERENCES company(id_company),
                                          FOREIGN KEY (id_status_order) REFERENCES status_order(id_status_order)
);

-- Создание таблицы order_products с составным первичным ключом
CREATE TABLE IF NOT EXISTS order_products (
                                              id_sale_order INT NOT NULL,
                                              id_product INT NOT NULL,
                                              quantity INT NOT NULL,
                                              PRIMARY KEY (id_sale_order, id_product),
                                              FOREIGN KEY (id_sale_order) REFERENCES sale_order(id_sale_order) ON DELETE CASCADE,
                                              FOREIGN KEY (id_product) REFERENCES products(id_product) ON DELETE CASCADE
);

-- Создание таблицы supplying_product с составным первичным ключом
CREATE TABLE IF NOT EXISTS supplying_product (
                                                 id_supplying INT NOT NULL,
                                                 id_product INT NOT NULL,
                                                 quantity INT NOT NULL,
                                                 PRIMARY KEY (id_supplying, id_product),
                                                 FOREIGN KEY (id_supplying) REFERENCES supplying(id_supplying) ON DELETE CASCADE,
                                                 FOREIGN KEY (id_product) REFERENCES products(id_product) ON DELETE CASCADE
);



