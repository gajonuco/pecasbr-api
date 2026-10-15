# 0003 - Autenticação do Cliente separada da autenticação de Usuario (staff)

## Status
### Aceito

## Contexto
<div style="text-align: justify;">
Ao planejar a feature de conta do cliente (login, cadastro, histórico
de pedidos, endereços salvos), era preciso decidir se `Cliente`
reaproveitaria o mesmo mecanismo e modelo (`Usuario`) já usado pela
equipe interna no painel administrativo, ou se teria autenticação
própria, permanecendo uma entidade de domínio separada.
</div>

## Decisão
<div style="text-align: justify;">
Manter `Cliente` e `Usuario` como entidades e fluxos de autenticação
separados. Ambos emitem o mesmo formato de JWT (reaproveitando o
`JWTTokenUtil` já existente, agnóstico quanto à entidade de origem),
mas com uma role dedicada (`CLIENTE`) sem nenhuma sobreposição com as
roles de staff (`ADMIN`, `VENDEDOR`).
</div>

## Alternativas consideradas
<div style="text-align: justify;">
- *Reaproveitar `Usuario` com uma role adicional `CLIENTE`*:
  rejeitada. Misturaria dois domínios com ciclos de vida diferentes na
  mesma tabela, forçando `Usuario` a acumular campos que não fazem
  sentido pra staff (CPF, endereço, data de nascimento), e criaria um
  único ponto de falha onde um bug de autorização poderia, em tese,
  elevar um cliente comum a acesso de staff.
</div>

## Consequências
<div style="text-align: justify;">
- Duplicação inevitável de uma pequena parte da lógica (endpoints de
  cadastro/login próprios para Cliente).
- A infraestrutura de geração/validação de token permanece única e
  compartilhada — só o subject e a role emitidos mudam.
- Toda nova permissão de cliente precisa ser expressa como regra
  própria (`hasRole("CLIENTE")`), nunca reaproveitando regras de staff.
</div>