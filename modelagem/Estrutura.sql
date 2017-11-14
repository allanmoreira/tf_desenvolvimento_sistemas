DROP DATABASE coin_counter_bd;
CREATE DATABASE coin_counter_bd;
USE coin_counter_bd;

CREATE TABLE MOEDAS (
    idMoeda INT AUTO_INCREMENT NOT NULL,
    Nome VARCHAR(30) NOT NULL,
    Sigla VARCHAR(10) NOT NULL,
    Valor DECIMAL(19,8) NOT NULL,
    CONSTRAINT moeda_pk PRIMARY KEY (idMoeda)
);

CREATE TABLE USUARIOS (
    idUsuario INT AUTO_INCREMENT NOT NULL,
    Nome VARCHAR(60) NOT NULL,
    Email VARCHAR(60) NOT NULL,
    Senha VARCHAR(24) NOT NULL,
    CONSTRAINT usuario_pk PRIMARY KEY (idUsuario)
);

CREATE TABLE INVESTIMENTOS (
    idInvestimento INT AUTO_INCREMENT NOT NULL,
    idMoeda INT NOT NULL,
    idUsuario INT NOT NULL,
    Descricao VARCHAR(80),
    dataIncial DATE NOT NULL,
    dataFinal DATE,
    Quantidade INT NOT NULL,
    CONSTRAINT investimentos_pk PRIMARY KEY (idInvestimento),
    CONSTRAINT fk_moeda_investimento FOREIGN KEY (idMoeda) REFERENCES MOEDAS(idMoeda),
    CONSTRAINT fk_usuario_investimento FOREIGN KEY (idUsuario) REFERENCES USUARIOS(idUsuario)
);

CREATE TABLE ALARMES (
    idAlarme INT AUTO_INCREMENT NOT NULL,
    idInvestimento INT NOT NULL,
    Valor DECIMAL(11,2) NOT NULL,
    Ativo CHAR (1),
    CONSTRAINT alarme_pk PRIMARY KEY (idAlarme),
    CONSTRAINT fk_investimento_alarme FOREIGN KEY (idInvestimento) REFERENCES INVESTIMENTOS(idInvestimento)
);

CREATE TABLE HISTORICO_MOEDA (
    idHistoricoMoeda INT AUTO_INCREMENT NOT NULL,
    idMoeda INT NOT NULL,
    valorAbertura DECIMAL(21,10) NOT NULL,
    valorFechamento DECIMAL(21,10) NOT NULL,
    valorAlta DECIMAL(21,10) NOT NULL,
    valorBaixa DECIMAL(21,10) NOT NULL,
    volumeMoeda DECIMAL(21,10) NOT NULL,
    volumeBTC DECIMAL(21,10) NOT NULL,
    dataHistorico DATE,
    CONSTRAINT historico_pk PRIMARY KEY (idHistoricoMoeda),
    CONSTRAINT fk_moeda_historico FOREIGN KEY (idMoeda) REFERENCES MOEDAS(idMoeda)
);
    
INSERT INTO MOEDAS (idMoeda, Nome, Sigla, Valor) VALUES (1, 'Litecoin', 'LTC', 0.01049510);
INSERT INTO MOEDAS (idMoeda, Nome, Sigla, Valor) VALUES (2, 'Reddcoin', 'RDD', 0.00000020);
INSERT INTO MOEDAS (idMoeda, Nome, Sigla, Valor) VALUES (3, 'BitcoinCash', 'BCH', 0.05821169);
INSERT INTO MOEDAS (idMoeda, Nome, Sigla, Valor) VALUES (4, 'Ethereum', 'ETH', 0.05427384);
INSERT INTO MOEDAS (idMoeda, Nome, Sigla, Valor) VALUES (5, 'Safe Exchange', 'LTC', 0.00000171);

ALTER TABLE USUARIOS MODIFY COLUMN Senha VARCHAR(24);
