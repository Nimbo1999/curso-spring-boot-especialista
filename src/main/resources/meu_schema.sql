CREATE DATABASE vendas;

USE vendas;

CREATE TABLE CUSTOMER (
    ID INTEGER PRIMARY KEY AUTO_INCREMENT,
    NOME VARCHAR(100),
    CPF VARCHAR(11),
    CREATED_AT TIMESTAMP,
    UPDATED_AT TIMESTAMP
);

CREATE TABLE PRODUTO (
    ID INTEGER PRIMARY KEY AUTO_INCREMENT,
    DESCRICAO VARCHAR(100),
    PRECO_UNITARIO NUMERIC(20,2),
    CREATED_AT TIMESTAMP,
    UPDATED_AT TIMESTAMP
);

CREATE TABLE PEDIDO (
    ID INTEGER PRIMARY KEY AUTO_INCREMENT,
    CLIENTE_ID INTEGER REFERENCES CUSTOMER (ID),
    DATA_PEDIDO TIMESTAMP,
    STATUS VARCHAR(20),
    TOTAL NUMERIC(20,2)
);

CREATE TABLE ITEM_PEDIDO (
    ID INTEGER PRIMARY KEY AUTO_INCREMENT,
    PEDIDO_ID INTEGER REFERENCES PEDIDO (ID),
    PRODUTO_ID INTEGER REFERENCES PRODUTO (ID),
    QUANTIDADE INTEGER
);

CREATE TABLE USUARIOS (
    ID INTEGER PRIMARY KEY AUTO_INCREMENT,
    LOGIN VARCHAR(100) NOT NULL,
    SENHA VARCHAR(255) NOT NULL,
    ADMIN BOOL DEFAULT FALSE
);