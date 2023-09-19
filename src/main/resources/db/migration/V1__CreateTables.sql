CREATE TABLE conta_a_pagar (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    valor DECIMAL(10, 2) NOT NULL,
    data_vencimento DATE NOT NULL,
    data_pagamento DATE,
    UNIQUE KEY unique_nome (nome)
);

CREATE TABLE regra_cobranca (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    operador ENUM('MAIOR', 'MENOR') NOT NULL,
    limite_dias INT NOT NULL,
    multa DECIMAL(5, 2) NOT NULL,
    taxa_ao_dia DECIMAL(5, 2) NOT NULL
);


