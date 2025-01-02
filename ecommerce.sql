CREATE TABLE tb_product(
	id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
	name VARCHAR(255) NOT NULL,
	price DECIMAL(10,2) NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE tb_category(
	id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
	name VARCHAR(255) NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE tb_product_category(
	product_id BIGINT NOT NULL,
	category_id BIGINT NOT NULL,
	PRIMARY KEY(product_id, category_id),
	FOREIGN KEY (product_id) REFERENCES tb_product(id),
	FOREIGN KEY (category_id) REFERENCES tb_category(id)
);

CREATE TABLE tb_cart(
	id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
	total DECIMAL(10, 2) DEFAULT 0.00,
	PRIMARY KEY(id)
);

CREATE TABLE tb_cart_item(
	product_id BIGINT NOT NULL,
	cart_id BIGINT NOT NULL,
	quantity INTEGER NOT NULL,
	subtotal DECIMAL(10, 2) NOT NULL,
	PRIMARY KEY(product_id, cart_id),
	FOREIGN KEY (product_id) REFERENCES tb_product(id),
	FOREIGN KEY (cart_id) REFERENCES tb_cart(id)
);

-- INSERTS
INSERT INTO tb_category (name) VALUES 
('Eletrônicos'),
('Roupas'),
('Livros'),
('Jogos'),
('Alimentos'),
('Móveis'),
('Brinquedos'),
('Ferramentas');

INSERT INTO tb_product (name, price) VALUES 
('Smartphone', 1200.50),
('Notebook', 3500.00),
('Camiseta', 50.90),
('Calça Jeans', 120.00),
('Livro de Java', 89.90),
('Livro de Python', 75.50),
('Jogo de Tabuleiro', 200.00),
('Console de Videogame', 2500.00),
('Chocolate', 15.00),
('Cadeira de Escritório', 480.00),
('Sofá 3 Lugares', 1800.00),
('Carrinho de Controle Remoto', 150.00),
('Chave de Fenda', 25.90),
('Martelo', 30.00),
('TV LED 50"', 2500.00),
('Headset Gamer', 350.00),
('Arroz 5kg', 22.90),
('Geladeira', 3500.00),
('Mouse Gamer', 125.00),
('Mesa de Jantar', 1200.00);

INSERT INTO tb_product_category (product_id, category_id) VALUES 
(1, 1),  -- Smartphone em Eletrônicos
(2, 1),  -- Notebook em Eletrônicos
(3, 2),  -- Camiseta em Roupas
(4, 2),  -- Calça Jeans em Roupas
(5, 3),  -- Livro de Java em Livros
(6, 3),  -- Livro de Python em Livros
(7, 4),  -- Jogo de Tabuleiro em Jogos
(8, 4),  -- Console de Videogame em Jogos
(9, 5),  -- Chocolate em Alimentos
(10, 6), -- Cadeira de Escritório em Móveis
(11, 6), -- Sofá 3 Lugares em Móveis
(12, 7), -- Carrinho de Controle Remoto em Brinquedos
(13, 8), -- Chave de Fenda em Ferramentas
(14, 8), -- Martelo em Ferramentas
(15, 1), -- TV LED 50" em Eletrônicos
(16, 1), -- Headset Gamer em Eletrônicos
(17, 5), -- Arroz 5kg em Alimentos
(18, 1), -- Geladeira em Eletrônicos
(19, 1), -- Mouse Gamer em Eletrônicos
(20, 6); -- Mesa de Jantar em Móveis

INSERT INTO tb_cart (total) VALUES (5152.30);
INSERT INTO tb_cart (total) VALUES (289.90);
INSERT INTO tb_cart (total) VALUES (698.70);
-- INSERT INTO tb_cart DEFAULT VALUES;

INSERT INTO tb_cart_item (product_id, cart_id, quantity, subtotal) VALUES 
(1, 1, 1, 1200.50), -- 1 Smartphone no Carrinho 1
(3, 1, 2, 101.80), -- 2 Camisetas no Carrinho 1
(5, 2, 1, 89.90), -- 1 Livro de Java no Carrinho 2
(7, 2, 1, 200.00), -- 1 Jogo de Tabuleiro no Carrinho 2
(10, 3, 1, 480.00), -- 1 Cadeira de Escritório no Carrinho 3
(9, 3, 10, 150.00), -- 10 Chocolates no Carrinho 3
(2, 1, 1, 3500.00), -- 1 Notebook no Carrinho 1
(16, 1, 1, 350.00), -- 1 Headset Gamer no Carrinho 1
(17, 3, 3, 68.70); -- 3 Sacos de Arroz no Carrinho 3

SELECT * FROM tb_cart;

CREATE VIEW vw_product_report AS
	SELECT p.id, p.name, COUNT(quantity) AS total_sales
	FROM tb_cart_item AS ci
	INNER JOIN tb_product AS p
	ON ci.product_id=p.id
	GROUP BY p.id, p.name
	ORDER BY total_sales DESC;