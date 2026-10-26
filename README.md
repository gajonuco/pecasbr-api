# Fashion Store API (pecasbr-api)

[![CI](https://github.com/gajonuco/pecasbr-api/actions/workflows/ci.yml/badge.svg)](https://github.com/gajonuco/pecasbr-api/actions/workflows/ci.yml)

API backend para um e-commerce de moda feminina, com catálogo de produtos (com variações de cor/tamanho e estoque), conta de cliente com endereços, carrinho/pedido, integração de pagamento via Asaas, notificações em tempo real (WebSocket e Firebase) e um painel administrativo com controle de acesso por papéis.

O pacote base do código é `com.gajonuco.pecasbr` e a classe principal é `PecasBrApplication`.

---

## Visão geral

A API expõe endpoints REST para:

- catálogo de produtos (`Peca`) com categorias, múltiplas imagens (upload por arquivo ou URL remota, imagem principal, reordenação) e variações por cor/tamanho com estoque individual
- conta de cliente própria (cadastro, login, dados, endereços múltiplos com endereço principal), separada da autenticação da equipe interna
- fluxo de pedidos, com ou sem retirada na loja, endereço de entrega vinculado ao cadastro do cliente, status com histórico (`NOVO_PEDIDO` → ... → `FINALIZADO`/`CANCELADO`) e filtros avançados
- geração de link de pagamento e confirmação automática via webhook do Asaas (com validação de assinatura)
- notificações push via Firebase Cloud Messaging (estoque baixo/crítico/zerado, pagamento confirmado) e WebSocket/STOMP para o painel administrativo
- aviso de novo pedido via bot do Telegram
- controle de acesso por papéis (RBAC): `ADMIN`, `VENDEDOR` (equipe interna) e `CLIENTE` (loja)
- cadastro de fretes, formas de pagamento e fluxo financeiro (registros a pagar/receber)

---

## Arquitetura do código

A aplicação segue um padrão clássico em camadas:

- `controller/` — adaptadores HTTP REST e webhooks
- `service/` — lógica de negócio e regras de domínio
- `dao/` — interfaces `JpaRepository` com consultas customizadas
- `model/` — entidades JPA e relacionamento de dados
- `dto/` — contratos de API e payloads específicos
- `integration/` — integração com serviços externos (Asaas: DTOs e serviço dedicado)
- `security/` — JWT (`JWTTokenUtil`), filtro de autenticação (`TokenFilter`) e configuração de segurança
- `config/` — WebSocket, CORS/recursos estáticos (`WebConfig`) e inicialização do Firebase
- `exception/` — exceções de domínio (`ClienteJaCadastradoException`, `EnderecoNaoEncontradoException`)

---

## Tech stack e dependências relevantes

- Java 21
- Spring Boot 3.4.3
- Spring Web + Spring WebFlux
- Spring Data JPA
- Spring Security
- Spring WebSocket / STOMP
- Spring Validation (`@Valid`)
- Thymeleaf
- Firebase Admin SDK (Cloud Messaging)
- JWT (`jjwt`, com `java-jwt` também na dependência)
- MapStruct
- Lombok
- Springdoc OpenAPI
- `spring-dotenv` (carrega variáveis de um arquivo `.env` local)
- MySQL (driver padrão) / PostgreSQL (disponível no classpath)
- Apache HttpClient 5
- Caelum Stella (validação/formatação de CPF)

---

## Modelo de domínio

### Produto (`Peca`)

- catálogo de roupas/acessórios: nome, detalhe, preço, preço promocional, categoria (`CategoriaPeca`)
- controle de estoque com `quantidadeEstoque`, `estoqueMinimo` e `estoqueCritico`, com status derivado (`NORMAL`, `BAIXO`, `CRITICO`, `ESGOTADO`)
- suporte a `PecaVariacao` (cor + tamanho + estoque próprio + SKU), com flags `corUnica`/`tamanhoUnico` quando o produto não varia; quando há variações, o estoque total da peça é a soma das variações (`sincronizarEstoqueTotal`)
- múltiplas imagens (`PecaImagem`) com ordem e indicação de imagem principal

### Cliente (`Cliente`) e endereços (`Endereco`)

- conta própria do cliente (nome, e-mail, telefone, CPF, data de nascimento, senha com hash)
- vários endereços por cliente, com um marcado como principal (`GET/POST/PUT/DELETE /cliente/me/enderecos`)
- busca administrativa por telefone, nome, palavra-chave e aniversariantes do mês

### Pedido (`Pedido`)

- histórico de status: `NOVO_PEDIDO`, `PAGO`, `EM_TRANSPORTE`, `ENTREGUE`, `POS_VENDA`, `FINALIZADO`, `CANCELADO` (constantes inteiras na própria entidade)
- relacionamento `@ManyToOne` com `Cliente` e com o `Endereco` de entrega (pode ser nulo quando o pedido é para retirada)
- relacionamento `@OneToMany` com `ItemPedido` (referencia `Peca` e, opcionalmente, a `PecaVariacao` escolhida)
- campos de `valorTotal`, `valorFrete`, `linkPagamento`, `asaasPaymentId`
- pode ser criado por um cliente autenticado ou como "guest" (dados do comprador informados no corpo da requisição)

### Usuário interno (`Usuario`) e papéis (`Role`)

- conta separada da conta de cliente, usada pela equipe interna (painel administrativo)
- `role` (`ADMIN` ou `VENDEDOR`) persistida e incluída como claim no JWT

### Outras entidades

- `CategoriaPeca`, `FormaPagamento`, `Frete`, `RegistroFinanceiro`

---

## Segurança e autenticação

### JWT e papéis

- Dois fluxos de autenticação distintos, deliberadamente não compartilhados (ver `docs/adr/0003-...`):
  - **Equipe interna**: `POST /login` com `Usuario`, token carrega a `role` (`ADMIN`/`VENDEDOR`).
  - **Cliente da loja**: `POST /cliente/cadastro` e `POST /cliente/login`, token sempre carrega a role fixa `CLIENTE`.
- Ambos usam o mesmo `JWTTokenUtil` (HS256, emissor `pecasbr-api`, expiração de 7 dias) e o mesmo `TokenFilter`, que decodifica o header `Authorization: Bearer <token>` em um `Authentication` do Spring Security.
- A chave de assinatura (`jwt.secret`) vem de variável de ambiente, não do código-fonte (ver `docs/adr/0002-...`).
- Autorização por papel (RBAC) é configurada em `MyWebApplicationSecurityConfig` com `hasRole`/`hasAnyRole` por rota e método HTTP — por exemplo, gestão de usuários é restrita a `ADMIN`; catálogo (escrita) e pedidos (gestão) aceitam `ADMIN` ou `VENDEDOR`; dados e endereços do próprio cliente exigem `CLIENTE`.

### CORS

- Origens liberadas: `localhost:4200`, `localhost:4222`, `https://projetoreal.dev.br`, `https://www.projetoreal.dev.br` e a URL configurada em `app.public-url` (varia por perfil/ambiente).

### WebSocket

- `WebSocketConfig` expõe um endpoint STOMP com SockJS em `/ws` (`setAllowedOriginPatterns("*")`)
- broker simples habilitado em `/topic`
- usado para notificar a tela de recibo/pedido quando um pagamento é confirmado

### Firebase

- `NotificationService` dispara notificações (estoque baixo/crítico/zerado e pagamento confirmado) via Firebase Cloud Messaging, enviando apenas `data` (sem `notification`) para evitar duplicidade
- inicialização condicional via propriedade `firebase.enabled` (habilitada por padrão); credenciais via `GOOGLE_APPLICATION_CREDENTIALS` ou `firebase/service-account.json` no classpath
- `NotificationController` (`/api/notifications/subscribe` e `/unsubscribe`) gerencia inscrição de tokens em tópicos — essas rotas são públicas também no `TokenFilter` (ignoram a decodificação de JWT)

### Asaas (pagamento)

- Webhook oficial e validado: `POST /webhook/asaas`, que exige o header `asaas-access-token` comparado em tempo constante com `ASAAS_WEBHOOK_TOKEN_*` (`MessageDigest.isEqual`), conforme `docs/adr/0001-...`. Eventos tratados: `PAYMENT_CONFIRMED` e `PAYMENT_RECEIVED`; o pedido correspondente (por `asaasPaymentId`) é marcado como `PAGO` uma única vez (não reprocessa se já estiver pago), com notificação por Firebase e WebSocket.
- Também existe `POST /createPayment` e `POST /webhook` em `IntegracaoController`, que geram/recebem eventos Asaas de forma mais simples (sem validação de token) e publicam no tópico WebSocket `/topic/payment`. **Ponto de atenção:** este segundo webhook, mais antigo, não tem a verificação de assinatura aplicada em `/webhook/asaas` — vale avaliar se ainda é necessário ou se deve ser removido/alinhado ao padrão do ADR 0001.

---

## Endpoints principais

### Autenticação — equipe interna

- `POST /login` — autentica `Usuario` e retorna token JWT

### Usuários (equipe interna — `ADMIN`)

- `GET /usuario`
- `GET /usuario/{id}`
- `POST /usuario`
- `PUT /usuario/{id}`

### Conta do cliente

- `POST /cliente/cadastro` — cadastra cliente e já retorna token JWT
- `POST /cliente/login`
- `GET /cliente/me` — dados do cliente autenticado (senha nunca retorna)
- `PUT /cliente/me` — atualiza nome, telefone e data de nascimento (e-mail/senha não mudam por aqui)
- `GET /cliente/me/enderecos`
- `POST /cliente/me/enderecos`
- `PUT /cliente/me/enderecos/{id}`
- `DELETE /cliente/me/enderecos/{id}`
- `PATCH /cliente/me/enderecos/{id}/principal`

### Clientes (visão administrativa — `ADMIN`/`VENDEDOR`)

- `GET /cliente`
- `GET /cliente/nome/{letra}`
- `GET /cliente/busca/{keyword}`
- `GET /cliente/compras/{id}` — compradores de uma peça
- `GET /cliente/aniversario/{mes}`
- `POST /cliente`
- `PUT /cliente`

### Categorias de produto

- `GET /categoria_peca`
- `GET /categoria_by_id`
- `GET /categoria_peca/search?key={texto}`
- `GET /categoria_peca/{id}`
- `POST /categoria_peca`
- `PUT /categoria_peca`

### Produtos e imagens

- `POST /peca`
- `PUT /peca/{idPeca}`
- `GET /peca` — lista paginada de destaques (público)
- `GET /peca/todos` (público)
- `GET /peca/categoria/{id}` (público)
- `GET /peca/{id}` (público)
- `GET /peca/busca?key={texto}&pageNumber={n}` (público)
- `POST /peca/upload` — upload de arquivo
- `POST /peca/{id}/imagem` — upload de imagem para produto (`arquivo`, `principal`)
- `POST /peca/{id}/imagem/url` — baixa e salva imagem a partir de uma URL externa
- `GET /peca/{idPeca}/imagens`
- `PATCH /peca/{idPeca}/imagem/{idImagem}/principal`
- `PATCH /peca/{id}/imagens/reordenar`
- `DELETE /peca/imagem/{idImagem}`
- `POST /produtos_mais_pedidos` — ranking de produtos mais pedidos

### Variações de produto (cor/tamanho/estoque)

- `GET /pecas/{idPeca}/variacoes`
- `PUT /pecas/{idPeca}/variacoes` — salva variações em lote
- `PATCH /pecas/{idPeca}/variacoes/{idVariacao}/estoque`
- `DELETE /pecas/{idPeca}/variacoes/{idVariacao}`
- `GET /pecas/{idPeca}/variacoes/disponibilidade?cor={cor}&tamanho={tamanho}`

### Pedidos

- `POST /pedido` — cria pedido (cliente autenticado ou guest, com validação `@Valid`)
- `PUT /pedido`
- `PATCH /pedido/{id}?status={status}` — equipe operacional
- `GET /pedido/search/{id}` — dono do pedido ou staff (`ADMIN`/`VENDEDOR`)
- `GET /pedido/meus` — pedidos do cliente autenticado
- `POST /pedido/filtrar`
- `GET /pedido/recentes?inicio={yyyy-MM-dd}&fim={yyyy-MM-dd}`

### Fretes

- `GET /fretesdisponiveis`
- `GET /fretes` (documentado com `bearerAuth` no OpenAPI)
- `GET /fretes/{id}`
- `GET /fretes/prefixo/{prefixo}` (público)
- `POST /fretes`
- `PUT /fretes`

### Formas de pagamento

- `GET /formaspagamento?visivel={0|1}`
- `GET /formaspagamento/{id}`
- `POST /formaspagamento`
- `PUT /formaspagamento`

### Financeiro

- `GET /financeiro`
- `POST /financeiro`
- `PUT /financeiro`

### Notificações

- `POST /api/notifications/subscribe`
- `POST /api/notifications/unsubscribe`

### Pagamentos e webhooks (Asaas)

- `POST /webhook/asaas` — webhook validado (ver seção de segurança)
- `POST /createPayment`
- `POST /webhook` — webhook legado, sem validação de assinatura

---

## Configuração e execução

### Pré-requisitos

- Java 21
- Maven 3.x ou wrapper `./mvnw`
- Banco de dados MySQL (driver padrão configurado) — PostgreSQL também está disponível como dependência
- Projeto de serviço Firebase (se `firebase.enabled` não for definido como `false`)

### Variáveis de ambiente

O projeto usa `spring-dotenv`, então um arquivo `.env` na raiz é carregado automaticamente. Use `src/main/resources/application.properties.example` como referência:

```
DB_URL=jdbc:mysql://localhost:3306/db_projetoreal?useTimezone=true&serverTimezone=America/Sao_Paulo
DB_USERNAME=
DB_PASSWORD=

TELEGRAM_CHAT_ID=
TELEGRAM_BOT_URL=

ASAAS_URL=
ASAAS_API_KEY=
ASAAS_WEBHOOK_TOKEN=

APP_PUBLIC_URL=

# Apenas produção
SSL_KEYSTORE_PATH=
SSL_KEYSTORE_PASSWORD=

JWT_SECRET=

IMAGES_DIR=
```

O perfil ativo é controlado por `SPRING_PROFILES_ACTIVE` (padrão: `dev`). Os perfis `dev` e `prod` têm arquivos próprios (`application-dev.properties`, `application-prod.properties`) com URLs de banco, chave do Asaas (sandbox vs. produção), nível de log e, no caso de `prod`, configuração de SSL (`server.ssl.*`) embutida no Tomcat.

### Executando localmente

```bash
./mvnw clean package
./mvnw spring-boot:run
```

#### Executando via JAR

```bash
./mvnw clean package
java -jar target/java-0.0.1-SNAPSHOT.jar
```

### Propriedades principais

- `server.port` (`8080` em `dev`, `8443` em `prod`)
- `spring.datasource.url` / `username` / `password` / `driver-class-name`
- `spring.jpa.hibernate.ddl-auto` (`update` em ambos os perfis — ver observação abaixo)
- `app.public-url` — usada em CORS e nos links gerados pelas notificações do Firebase
- `app.images-dir` / `app.images-base-url` — diretório local e URL base para upload/serviço de imagens (`WebConfig` expõe `GET /images/**`)
- `jwt.secret`
- `telegrambot_url`, `telegrambot_chat_id`, `telegrambot_msg`
- `asaas.url`, `asaas.apikey`, `asaas.webhook.token`
- `springdoc.packagesToScan`
- `firebase.enabled` (opcional; padrão `true`)

---

## Decisões de arquitetura (ADRs)

O histórico de decisões relevantes fica em `docs/adr/`:

- [0001 — Validar assinatura do webhook do Asaas](docs/adr/0001-validacao-assinatura-webhook-asaas.md)
- [0002 — RBAC com roles ADMIN e VENDEDOR](docs/adr/0002-rbac-com-roles-admin-vendedor.md)
- [0003 — Autenticação de Cliente separada de Usuario](docs/adr/0003-autenticacao-de-cliente-separada-de-usuario.md)

Scripts de migração de banco relevantes (executados manualmente em produção, onde `ddl-auto` deveria ser restritivo) ficam em `docs/migrations/`.

---

## Documentação e teste

- Swagger UI: `http://localhost:8080/swagger-ui.html` (ou `/v3/api-docs` para o JSON OpenAPI)
- Testes: `./mvnw test`
- Testes cobrem, entre outros: `PedidoController`, `AsaasWebhookController`, `ClienteServiceImpl`, `EnderecoServiceImpl`, `PedidoServiceImpl`, `Peca` (regras de estoque/variação), `Cliente`/`Endereco`, `JWTTokenUtil` e a integração com Asaas (`AsaasServiceImplTest`)
- CI (`.github/workflows/ci.yml`): compila, sobe um MySQL de serviço, roda os testes e executa o Checkstyle (regras do Google Java Style) sem bloquear o build em caso de violação

---

## Pontos de atenção

- `/webhook` (em `IntegracaoController`) convive com `/webhook/asaas` (em `AsaasWebhookController`); apenas o segundo valida a origem da notificação (ADR 0001) — vale revisar se o primeiro ainda é necessário.
- `spring.jpa.hibernate.ddl-auto=update` está ativo também no perfil `prod`, embora os comentários em `docs/migrations/` já assumam `validate` em produção (migração manual de schema); os dois não estão alinhados hoje.
- `UploadServiceImpl` grava arquivos no filesystem local (`app.images-dir`); já é configurável por variável de ambiente, mas ainda é sensível ao storage do host em deploys efêmeros/em contêiner.
- Captura genérica de `Exception` em vários controllers (`ClienteController`, `FreteController`, `FormaPagamentoController` etc.) com `ex.printStackTrace()`/`System.out.println`, em vez de tratamento consistente de erros (ex.: `@ControllerAdvice`).
- Uso de inteiros para status de `Pedido` (em vez de enum) e setters com `System.out.println` de debug remanescentes (ex.: `Peca#setEstoqueCritico`).
- Nomes como `Peca` e `CategoriaPeca` ainda carregam o histórico do projeto (nome anterior do pacote, `pecasbr`), mas o domínio atual é moda feminina.

## Próximos passos sugeridos

- unificar o fluxo de webhook do Asaas em um único endpoint validado
- alinhar `ddl-auto` entre o que está configurado e o que a documentação de migração assume
- tratamento global de exceções com `@ControllerAdvice` e remoção de `printStackTrace`/`System.out.println` residuais
- considerar um armazenamento de mídia externo/configurável para uploads de imagem
- avaliar enum para status de `Pedido`

---

## Arquivos-chave

- `pom.xml`
- `src/main/java/com/gajonuco/pecasbr/PecasBrApplication.java`
- `src/main/java/com/gajonuco/pecasbr/security/MyWebApplicationSecurityConfig.java`
- `src/main/java/com/gajonuco/pecasbr/security/JWTTokenUtil.java`
- `src/main/java/com/gajonuco/pecasbr/controller/PedidoController.java`
- `src/main/java/com/gajonuco/pecasbr/controller/PecaController.java`
- `src/main/java/com/gajonuco/pecasbr/controller/PecaVariacaoController.java`
- `src/main/java/com/gajonuco/pecasbr/controller/EnderecoController.java`
- `src/main/java/com/gajonuco/pecasbr/controller/AsaasWebhookController.java`
- `src/main/resources/application.properties` (+ `application-dev.properties`, `application-prod.properties`, `application.properties.example`)
- `docs/adr/`
