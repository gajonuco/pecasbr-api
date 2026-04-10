# Fashion Store API

API backend para um e-commerce de moda feminina, com foco em catálogo de roupas, gestão de estoque, vendas por pedido e integrações de pagamento.
Este projeto entrega uma arquitetura corporativa com autenticação JWT, integração externa com Asaas e Firebase, suporte a upload de mídia e uma camada de serviços bem definida.

---

## Visão geral

A `Fashion Store API` foi desenvolvida para ser o núcleo de um sistema de varejo de moda feminina.
Ela expõe endpoints REST para gestão de produtos, clientes, pedidos e controle financeiro, além de suportar:

- catálogo de produtos com categorias, imagens e variações
- fluxo de pedidos com alteração de status e integração de pagamento
- upload de fotos de produtos via arquivo ou URL remota
- notificações push via Firebase e WebSocket para painel de administração
- webhook Asaas para confirmação automatizada de pagamentos
- ferramentas de busca, filtro e ranking de vendas

---

## Principais responsabilidades do backend

- Persistência JPA de domínio relacional (`Pedido`, `Cliente`, `Peca`, `ItemPedido`, `CategoriaPeca`, `PecaImagem`)
- Validação de autenticação com JWT e filtro de requisição customizado
- Configuração de CORS segura para frontend Angular / administração
- Exposição de API documentada com Springdoc OpenAPI
- Upload e ingestão de imagens de produto via `RestTemplate` ou multipart
- Notificações reativas: WebSocket + Firebase topics
- Integração com serviço de pagamento externo (Asaas)

---

## Tech stack e dependências relevantes

- Java 21
- Spring Boot 3.4.3
- Spring Web + Spring WebFlux
- Spring Data JPA
- Spring Security
- Spring WebSocket / STOMP
- Thymeleaf
- Firebase Admin SDK
- JWT (`java-jwt` + `jjwt`)
- MapStruct
- Lombok
- Springdoc OpenAPI
- MySQL / PostgreSQL
- Apache HttpClient
- Caelum Stella (validação de CPF/CNPJ e formatação)

---

## Arquitetura do código

A aplicação segue um padrão clássico em camadas:

- `controller/` — adaptadores HTTP REST e webhooks
- `service/` — lógica de negócio e regras de domínio
- `dao/` — acesso direto a repositórios e consultas customizadas
- `model/` — entidades JPA e relacionamento de dados
- `dto/` — contratos de API e payloads específicos
- `integration/` — integração com serviços externos, como Asaas
- `security/` — configuração de segurança, JWT e filtro de autenticação
- `config/` — configuração de WebSocket e CORS

O pacote base é `com.gabriel_nunez.oficina_mecanica` por legado de projeto, mas o domínio atual é e-commerce de moda feminina.

---

## Análise técnica

- a arquitetura em camadas ajuda a deixar o projeto mais organizado e facilita manutenção
- a separação de `controller`, `service`, `dao` e `integration` está clara, embora seja possível melhorar a centralização das regras de upload e mídia
- o uso de JWT e `SecurityFilterChain` atende à necessidade de autenticação, mas a configuração de CORS merece atenção em ambientes de produção
- `spring.jpa.hibernate.ddl-auto=update` é conveniente para desenvolvimento; em produção, um fluxo de migração de banco é mais seguro
- o suporte a notificações em tempo real e processamento de webhook agrega valor ao e-commerce
- alguns nomes como `Peca` e `CategoriaPeca` ainda carregam o histórico do projeto e podem ser revisitados em refatorações futuras

### Pontos de atenção

- há credenciais e URLs no `application.properties`; para produção, o ideal é externalizar esses segredos
- o `UploadServiceImpl` usa caminho de arquivo local fixo, o que pode dificultar deploy em contêineres ou nuvem
- capturas genéricas de `Exception` nos controllers podem ser melhoradas com tratamento de erros mais consistente
- `RestTemplate` e Spring WebFlux coexistem, então há oportunidade de alinhar a comunicação externa com uma única abordagem
- o uso de inteiros para status de pedido pode ser simplificado com enums para mais clareza

### Próximos passos

- revisar nomes de domínio para refletir melhor produtos de moda
- adicionar validação de entrada com `@Valid`, `@NotNull`, `@Size`
- aplicar tratamento global de exceções com `@ControllerAdvice`
- preparar configuração separada para `dev` e `prod`
- ampliar testes de integração para fluxos críticos, especialmente pagamento e webhook
- considerar um armazenamento de mídia configurável para uploads

---

## Modelo de domínio

### Pedido (`Pedido`)

- histórico de status: `NOVO_PEDIDO`, `PAGO`, `EM_TRANSPORTE`, `ENTREGUE`, `POS_VENDA`, `FINALIZADO`, `CANCELADO`
- relacionamento `@ManyToOne` com `Cliente`
- relacionamento `@OneToMany` com `ItemPedido`
- campos de `valorTotal`, `valorFrete`, `linkPagamento`, `asaasPaymentId`

### Produto (`Peca`)

- catálogo de produtos que representa roupas e acessórios femininos
- busca por palavra-chave, paginação e destaque de itens
- upload de imagens por arquivo ou URL externa
- múltiplas imagens por produto e definição de imagem principal

### Cliente (`Cliente`)

- busca por telefone, nome, palavra-chave e aniversariantes
- histórico de compras e relacionamento com pedido

---

## Segurança e integrações

### JWT e CORS

- `MyWebApplicationSecurityConfig` configura filtros JWT e autorizações
- endpoints públicos autorizados para login, busca de produtos, documentação e webhook
- CORS habilitado para origens confiáveis incluindo `localhost:4200`, `localhost:4222`, `projetoreal.dev.br`

### WebSocket

- `WebSocketConfig` expõe endpoint STOMP em `/ws`
- mensagens são publicadas em `/topic`
- usado para eventos de pagamento e atualizações em tempo real

### Firebase

- `NotificationService` gera notificações com payload de dados
- tópicos são usados para alertas de estoque e confirmação de pagamento
- mensagens são construídas para evitar duplicação de notificações automáticas

### Asaas

- webhook em `/webhook/asaas`
- eventos processados: `PAYMENT_CONFIRMED`, `PAYMENT_RECEIVED`
- pedido atualizado para `PAGO` e notificado via Firebase/WebSocket

---

## Endpoints principais

### Autenticação

- `POST /login` — autentica usuário e retorna token JWT

### Usuários

- `GET /usuario`
- `GET /usuario/{id}`
- `POST /usuario`
- `PUT /usuario/{id}`

### Clientes

- `GET /cliente/{telefone}`
- `GET /cliente/nome/{letra}`
- `GET /cliente/busca/{keyword}`
- `GET /cliente/compras/{id}`
- `GET /cliente/aniversario/{mes}`
- `POST /cliente`
- `PUT /cliente`

### Produtos e imagens

- `POST /peca`
- `PUT /peca/{idPeca}`
- `GET /peca` — lista paginada de destaques
- `GET /peca/todos`
- `GET /peca/categoria/{id}`
- `GET /peca/{id}`
- `GET /peca/busca?key={texto}`
- `POST /peca/upload` — upload de arquivo
- `POST /peca/{id}/imagem` — upload de imagem para produto
- `POST /peca/{id}/imagem/url` — adiciona imagem via URL externa
- `GET /peca/{idPeca}/imagens`
- `PATCH /peca/{idPeca}/imagem/{idImagem}/principal`
- `PATCH /peca/{id}/imagens/reordenar`
- `DELETE /peca/imagem/{idImagem}`
- `POST /produtos_mais_pedidos`

### Pedidos

- `POST /pedido`
- `PUT /pedido`
- `PATCH /pedido/{id}?status={status}`
- `GET /pedido/search/{id}`
- `POST /pedido/filtrar`
- `GET /pedido/recentes?inicio={yyyy-MM-dd}&fim={yyyy-MM-dd}`

### Notificações

- `POST /api/notifications/subscribe`
- `POST /api/notifications/unsubscribe`

### Pagamentos e webhook

- `POST /webhook/asaas`

---

## Configuração e execução

### Pré-requisitos

- Java 21
- Maven 3.x ou wrapper `./mvnw`
- Banco de dados MySQL ou PostgreSQL

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

- `server.port`
- `spring.datasource.url`
- `spring.datasource.username`
- `spring.datasource.password`
- `spring.datasource.driver-class-name`
- `spring.jpa.hibernate.ddl-auto`
- `app.frontend.url`
- `telegrambot_url`, `telegrambot_chat_id`, `telegrambot_msg`
- `asaas.url`, `asaas.apikey`
- `springdoc.packagesToScan`

---

## Documentação e teste

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- Testes: `./mvnw test`

---

## Boas práticas para produção

- use `spring.jpa.hibernate.ddl-auto=validate` ou `none` em produção
- remova credenciais e tokens do `application.properties`
- centralize segredos via variáveis de ambiente ou vault
- habilite logs e tracing apenas em ambientes controlados
- configure CORS estritamente para domínios conhecidos
- monitore status do webhook Asaas e falhas de entrega

---

## Pontos de atenção

- `NotificationService` envia notificações via topic com payload customizado
- `PecaController` suporta upload de imagens e ingestão a partir de URL externa
- `AsaasWebhookController` não reprocessa pedidos já confirmados
- `MyWebApplicationSecurityConfig` abre endpoints públicos necessários para frontend e documentação
- `WebSocketConfig` expõe `/ws` com SockJS para clientes de dashboard

---

## Observações e próximos passos

### O que já está bem resolvido

- a arquitetura em camadas e o uso de padrões Spring facilitam a manutenção
- notificações em tempo real são um diferencial para o painel administrativo
- integração com Asaas e Firebase traz automação de pagamento e alertas de estoque

### O que pode ser melhorado

- externalizar segredos e credenciais fora do repositório
- adotar migrações de banco de dados mais previsíveis
- adicionar tratamento global de exceções e melhores códigos HTTP
- padronizar logs e reduzir `System.out.println`
- tornar upload de mídia configurável e menos dependente do filesystem local

### Possíveis evoluções

- separar módulos de checkout e carrinho para maior desacoplamento
- adicionar uma camada de aplicação para orquestração de fluxos de negócio
- avaliar search full-text para catálogo de produtos

---

## Arquivos-chave

- `pom.xml`
- `src/main/java/com/gabriel_nunez/oficina_mecanica/OficinaMecanicaApplication.java`
- `src/main/java/com/gabriel_nunez/oficina_mecanica/security/MyWebApplicationSecurityConfig.java`
- `src/main/java/com/gabriel_nunez/oficina_mecanica/controller/PedidoController.java`
- `src/main/java/com/gabriel_nunez/oficina_mecanica/controller/PecaController.java`
- `src/main/java/com/gabriel_nunez/oficina_mecanica/controller/AsaasWebhookController.java`
- `src/main/resources/application.properties`

---


