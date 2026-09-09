# 0002 - Controle de acesso por papéis (RBAC) com roles ADMIN e VENDEDOR

## Status
### Aceito

## Contexto
<div style="text-align: justify;">
A aplicação usava JWT, mas sem nenhum controle de autorização por
papel — qualquer usuário autenticado tinha acesso a qualquer endpoint
não explicitamente público, incluindo gestão de usuários, catálogo e
pedidos. O filtro de autenticação sempre atribuía lista vazia de
authorities, tornando impossível checar papel mesmo que fosse tentado
no controller. Também foi identificado nessa revisão que a chave de
assinatura do JWT estava hardcoded no código-fonte, publicada no
repositório.
</div>

## Decisão
<div style="text-align: justify;">
- Adicionar campo `role` (enum `ADMIN`, `VENDEDOR`) a `Usuário`.
- Incluir a role como claim assinado no JWT, convertida em `GrantedAuthority` (`ROLE_<role>`) na decodificação do token.
-  Restringir gestão de usuários a `ADMIN`, e catálogo/pedidos a `ADMIN` ou `VENDEDOR`, via `hasRole`/`hasAnyRole` no `SecurityFilterChain`.
- Pré-requisito: externalizar o secret do JWT para variável de
  ambiente, com um valor novo (o antigo é considerado comprometido
  permanentemente, por já ter estado em commit público) — role-based
  access não tem sentido sobre uma chave pública.
</div>

## Alternativas consideradas
<div style="text-align: justify;">
- *Permissões granulares por recurso* (ex: `pedido:editar`): mais
  flexível, mas desproporcional ao tamanho da equipe atual. Descartada
  por complexidade prematura.
- *Papel único, sem VENDEDOR*: mais simples, mas não reflete um
  e-commerce real, onde normalmente existe equipe operacional com
  acesso mais restrito que o administrador.
</div>

## Consequências
<div style="text-align: justify;">
- Usuários existentes precisam receber uma role manualmente (migração).
- Todo novo endpoint sensível precisa declarar sua regra de autorização
  explicitamente — a ausência cai no `anyRequest().authenticated()`
  genérico, que autoriza qualquer papel.
- Como o secret antigo já esteve exposto, todos os tokens emitidos
  antes da migração ficam inválidos quando a nova chave entra em
  produção — efeito colateral aceito e esperado.
</div>
