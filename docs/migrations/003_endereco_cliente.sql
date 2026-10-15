-- 1. Cria a tabela nova
CREATE TABLE tbl_endereco (
                              id_endereco INT AUTO_INCREMENT PRIMARY KEY,
                              apelido VARCHAR(50),
                              cep VARCHAR(10) NOT NULL,
                              logradouro VARCHAR(100),
                              numero VARCHAR(20),
                              complemento VARCHAR(50),
                              bairro VARCHAR(100),
                              cidade VARCHAR(100),
                              estado VARCHAR(2),
                              principal BOOLEAN NOT NULL DEFAULT FALSE,
                              id_cliente INT NOT NULL,
                              CONSTRAINT fk_endereco_cliente FOREIGN KEY (id_cliente) REFERENCES tbl_cliente(id_cliente)
);

-- 2. Migra os endereços existentes (marcando todos como principal, já que hoje só existe um por cliente)
INSERT INTO tbl_endereco (cep, logradouro, numero, complemento, bairro, cidade, estado, principal, id_cliente)
SELECT cep_cliente, logradouro, numero, complemento, bairro, cidade, estado, TRUE, id_cliente
FROM tbl_cliente
WHERE cep_cliente IS NOT NULL;

-- 3. ANTES de adicionar a constraint de unicidade em email, verifica duplicados:
SELECT email_cliente, COUNT(*) FROM tbl_cliente GROUP BY email_cliente HAVING COUNT(*) > 1;
-- Se aparecer alguma linha, resolve manualmente antes do próximo passo (a constraint vai falhar com duplicados).

-- 4. Só depois de validar os passos acima:
ALTER TABLE tbl_cliente
DROP COLUMN cep_cliente, DROP COLUMN logradouro, DROP COLUMN numero,
    DROP COLUMN complemento, DROP COLUMN bairro, DROP COLUMN cidade, DROP COLUMN estado,
    ADD COLUMN senha VARCHAR(100) NULL,
    ADD UNIQUE INDEX uk_cliente_email (email_cliente);