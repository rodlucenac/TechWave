CREATE DATABASE IF NOT EXISTS TechWave;
USE TechWave;

CREATE TABLE Endereco (
    id_endereco INT AUTO_INCREMENT PRIMARY KEY,
    rua VARCHAR(255),
    numero VARCHAR(10),
    bairro VARCHAR(255),
    cidade VARCHAR(255),
    estado VARCHAR(2),
    cep VARCHAR(10)
);

CREATE TABLE Usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255),
    email VARCHAR(255),
    senha VARCHAR(255),
    cpf VARCHAR(14),
    endereco_id INT,
    FOREIGN KEY (endereco_id) REFERENCES Endereco(id_endereco)
);

CREATE TABLE Administrador (
    id_usuario INT PRIMARY KEY,
    cargo VARCHAR(255),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);

CREATE TABLE Cliente (
    id_usuario INT PRIMARY KEY,
    data_nascimento DATE,
    telefone VARCHAR(15),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);

CREATE TABLE Notificacao (
    id_notificacao INT AUTO_INCREMENT PRIMARY KEY,
    mensagem TEXT
);

CREATE TABLE Recebe (
    cliente_id INT,
    notificacao_id INT,
    status_ativo BOOLEAN,
    PRIMARY KEY (cliente_id, notificacao_id),
    FOREIGN KEY (cliente_id) REFERENCES Cliente(id_usuario),
    FOREIGN KEY (notificacao_id) REFERENCES Notificacao(id_notificacao)
);

CREATE TABLE Produto (
    id_produto INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255),
    descricao TEXT,
    estoque INT,
    preco DECIMAL(10, 2),
    imagem VARCHAR(255)
);

CREATE TABLE Categoria (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255),
    descricao TEXT
);

CREATE TABLE Pertence (
    produto_id INT,
    categoria_id INT,
    PRIMARY KEY (produto_id, categoria_id),
    FOREIGN KEY (produto_id) REFERENCES Produto(id_produto),
    FOREIGN KEY (categoria_id) REFERENCES Categoria(id_categoria)
);

CREATE TABLE Carrinho_compra (
    id_carrinho INT AUTO_INCREMENT PRIMARY KEY,
    status_carrinho VARCHAR(50)
);

CREATE TABLE Pedido (
    id_pedido INT AUTO_INCREMENT PRIMARY KEY,
    data_pedido DATE,
    status_pedido VARCHAR(50),
    valor_total DECIMAL(10, 2),
    carrinho_id INT,
    FOREIGN KEY (carrinho_id) REFERENCES Carrinho_compra(id_carrinho)
);

CREATE TABLE Adiciona (
    pedido_id INT,
    produto_id INT,
    quantidade INT,
    PRIMARY KEY (pedido_id, produto_id),
    FOREIGN KEY (pedido_id) REFERENCES Pedido(id_pedido),
    FOREIGN KEY (produto_id) REFERENCES Produto(id_produto)
);

CREATE TABLE Pagamento (
    id_pagamento INT AUTO_INCREMENT PRIMARY KEY,
    tipo_pagamento VARCHAR(50),
    status_pagamento VARCHAR(50),
    valor_pagamento DECIMAL(10, 2),
    data_pagamento DATE,
    pedido_id INT,
    FOREIGN KEY (pedido_id) REFERENCES Pedido(id_pedido)
);

CREATE TABLE Favorito (
    produto_id INT,
    cliente_id INT,
    data_adicao DATE,
    PRIMARY KEY (produto_id, cliente_id),
    FOREIGN KEY (produto_id) REFERENCES Produto(id_produto),
    FOREIGN KEY (cliente_id) REFERENCES Cliente(id_usuario)
);

-- VIEWS PARA DASHBOARDS E RELATÓRIOS

-- Ranking de Produtos Favoritos
CREATE VIEW vw_produtos_favoritos AS
SELECT 
    p.id_produto,
    p.nome,
    COUNT(f.cliente_id) AS total_favoritos
FROM Produto p
JOIN Favorito f ON p.id_produto = f.produto_id
GROUP BY p.id_produto, p.nome
ORDER BY total_favoritos DESC;

-- Resumo de Notificações
CREATE VIEW vw_resumo_notificacoes AS
SELECT 
    n.id_notificacao,
    n.mensagem,
    COUNT(r.cliente_id) AS total_envios,
    SUM(CASE WHEN r.status_ativo = TRUE THEN 1 ELSE 0 END) AS envios_ativos
FROM Notificacao n
LEFT JOIN Recebe r ON n.id_notificacao = r.notificacao_id
GROUP BY n.id_notificacao, n.mensagem;

-- Relatório de Pedidos por Dia
CREATE VIEW vw_pedidos_por_dia AS
SELECT 
    DATE(data_pedido) AS dia,
    COUNT(*) AS total_pedidos,
    SUM(valor_total) AS total_vendas,
    AVG(valor_total) AS ticket_medio
FROM Pedido
GROUP BY DATE(data_pedido);

-- Análise de Produtos por Categoria
CREATE VIEW vw_produtos_por_categoria AS
SELECT 
    c.id_categoria, 
    c.nome AS categoria,
    SUM(a.quantidade) AS total_vendidos,
    COUNT(DISTINCT p.id_produto) AS num_produtos
FROM Categoria c
JOIN Pertence pe ON c.id_categoria = pe.categoria_id
JOIN Produto p ON pe.produto_id = p.id_produto
LEFT JOIN Adiciona a ON p.id_produto = a.produto_id
GROUP BY c.id_categoria, c.nome;

-- Resumo de Pedidos por Status
CREATE VIEW vw_pedidos_status AS
SELECT 
    status_pedido, 
    COUNT(*) AS total_pedidos,
    SUM(valor_total) AS total_valor
FROM Pedido
GROUP BY status_pedido;

-- Resumo de Pagamentos por Status
CREATE VIEW vw_pagamentos_status AS
SELECT 
    status_pagamento,
    COUNT(*) AS total_pagamentos,
    SUM(valor_pagamento) AS total_receita
FROM Pagamento
GROUP BY status_pagamento;

-- PROCEDURES

-- Procedures de CRUD de Produto
DELIMITER //

CREATE PROCEDURE sp_inserir_produto (
    IN p_nome VARCHAR(255),
    IN p_descricao TEXT,
    IN p_estoque INT,
    IN p_preco DECIMAL(10,2),
    IN p_imagem VARCHAR(255)
)
BEGIN
    INSERT INTO Produto (nome, descricao, estoque, preco, imagem)
    VALUES (p_nome, p_descricao, p_estoque, p_preco, p_imagem);
END;
//

CREATE PROCEDURE sp_atualizar_produto (
    IN p_id INT,
    IN p_nome VARCHAR(255),
    IN p_descricao TEXT,
    IN p_estoque INT,
    IN p_preco DECIMAL(10,2),
    IN p_imagem VARCHAR(255)
)
BEGIN
    UPDATE Produto
    SET nome = p_nome,
        descricao = p_descricao,
        estoque = p_estoque,
        preco = p_preco,
        imagem = p_imagem
    WHERE id_produto = p_id;
END;
//

CREATE PROCEDURE sp_deletar_produto (
    IN p_id INT
)
BEGIN
    DELETE FROM Produto
    WHERE id_produto = p_id;
END;
//

-- Procedures de CRUD de Categoria
CREATE PROCEDURE sp_inserir_categoria (
    IN p_nome VARCHAR(255),
    IN p_descricao TEXT
)
BEGIN
    INSERT INTO Categoria (nome, descricao)
    VALUES (p_nome, p_descricao);
END;
//

CREATE PROCEDURE sp_atualizar_categoria (
    IN p_id INT,
    IN p_nome VARCHAR(255),
    IN p_descricao TEXT
)
BEGIN
    UPDATE Categoria
    SET nome = p_nome,
        descricao = p_descricao
    WHERE id_categoria = p_id;
END;
//

CREATE PROCEDURE sp_deletar_categoria (
    IN p_id INT
)
BEGIN
    DELETE FROM Categoria
    WHERE id_categoria = p_id;
END;
//

-- Procedure para Relatório Dinâmico por Período
CREATE PROCEDURE sp_relatorio_periodo (
    IN data_inicio DATE, 
    IN data_fim DATE
)
BEGIN
    SELECT 
         DATE(data_pedido) AS dia,
         COUNT(*) AS total_pedidos,
         SUM(valor_total) AS total_vendas,
         AVG(valor_total) AS ticket_medio
    FROM Pedido
    WHERE data_pedido BETWEEN data_inicio AND data_fim
    GROUP BY DATE(data_pedido);
END;
//

DELIMITER ;

-- TRIGGERS

DELIMITER //

-- Trigger para notificar alteração de status de Pedido
CREATE TRIGGER tr_status_pedido_notify
AFTER UPDATE ON Pedido
FOR EACH ROW
BEGIN
    IF NEW.status_pedido IN ('confirmado', 'pendente', 'cancelado')
       AND NEW.status_pedido <> OLD.status_pedido THEN
        INSERT INTO Notificacao (mensagem)
        VALUES (CONCAT('Pedido ', NEW.status_pedido));
        INSERT INTO Recebe (cliente_id, notificacao_id, status_ativo)
        VALUES (1, LAST_INSERT_ID(), TRUE);
    END IF;
END;
//

-- Trigger para notificar alteração de status de Pagamento
CREATE TRIGGER tr_status_pagamento_notify
AFTER UPDATE ON Pagamento
FOR EACH ROW
BEGIN
    IF NEW.status_pagamento IN ('confirmado', 'pendente', 'cancelado')
       AND NEW.status_pagamento <> OLD.status_pagamento THEN
        INSERT INTO Notificacao (mensagem)
        VALUES (CONCAT('Pagamento ', NEW.status_pagamento));
        INSERT INTO Recebe (cliente_id, notificacao_id, status_ativo)
        VALUES (1, LAST_INSERT_ID(), TRUE);
    END IF;
END;
//

DELIMITER ;

CALL sp_inserir_produto('Teclado Mecânico', 'Switch azul', 50, 199.99, 'teclado.jpg');
CALL sp_atualizar_produto(1, 'Teclado RGB', 'Switch vermelho, RGB', 60, 249.99, 'teclado_rgb.jpg');

CALL sp_inserir_categoria('Acessórios', 'Produtos auxiliares para PCs');
CALL sp_atualizar_categoria(1, 'Periféricos', 'Mouse, teclado, headset');

CALL sp_relatorio_periodo('2023-01-01', '2023-12-31');

UPDATE Pedido SET status_pedido = 'confirmado' WHERE id_pedido = 1;
UPDATE Pedido SET status_pedido = 'pendente' WHERE id_pedido = 1;
UPDATE Pedido SET status_pedido = 'cancelado' WHERE id_pedido = 1;

UPDATE Pagamento SET status_pagamento = 'confirmado' WHERE id_pagamento = 1;
UPDATE Pagamento SET status_pagamento = 'pendente' WHERE id_pagamento = 1;
UPDATE Pagamento SET status_pagamento = 'cancelado' WHERE id_pagamento = 1;
