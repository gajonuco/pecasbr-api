-- 004_endereco_entrega_pedido.sql
--
-- Contexto: sub-issue #15 (gajonuco/pecasbr-api)
-- Adequar criação de pedido ao novo modelo de endereço (Endereco, sub-issue #7).
--
-- Pedido passa a referenciar qual Endereco foi usado como entrega.
-- Isso NÃO é um snapshot do endereço (texto congelado no momento da compra)
-- — é só a referência de qual registro foi usado. Se o cliente editar esse
-- endereço depois, o pedido antigo reflete a edição. O snapshot ficou
-- deliberadamente fora de escopo da Epic #6 (ver docs/adr/, "fora de escopo").
--
-- Ambiente: executar manualmente em produção, já que lá
-- spring.jpa.hibernate.ddl-auto=validate (não cria/altera schema sozinho).
-- Em dev, com ddl-auto=update, o Hibernate já aplica essa mudança sozinho.

-- 1. Adiciona a coluna, nullable (pedidos para retirar na loja não têm
--    endereço de entrega; pedidos antigos, criados antes dessa migração,
--    também ficam sem valor aqui).
ALTER TABLE tbl_pedido
    ADD COLUMN id_endereco_entrega INT NULL;

-- 2. Antes de criar a FK, confirma que não existe nenhum valor órfão
--    (não deveria haver nenhum, já que a coluna acabou de ser criada vazia
--    — esse SELECT é só uma checagem defensiva, deve sempre retornar 0 linhas).
SELECT p.id_pedido, p.id_endereco_entrega
FROM tbl_pedido p
         LEFT JOIN tbl_endereco e ON e.id_endereco = p.id_endereco_entrega
WHERE p.id_endereco_entrega IS NOT NULL
  AND e.id_endereco IS NULL;

-- 3. Cria a constraint de chave estrangeira.
ALTER TABLE tbl_pedido
    ADD CONSTRAINT fk_pedido_endereco_entrega
        FOREIGN KEY (id_endereco_entrega) REFERENCES tbl_endereco (id_endereco);

-- ─────────────────────────────────────────────────────────────────────────
-- Rollback (caso precise reverter esta migração):
--
-- ALTER TABLE tbl_pedido DROP FOREIGN KEY fk_pedido_endereco_entrega;
-- ALTER TABLE tbl_pedido DROP COLUMN id_endereco_entrega;
-- ─────────────────────────────────────────────────────────────────────────