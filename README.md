# 🔧 API E-commerce para Loja de Auto Peças

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.3-brightgreen?style=for-the-badge&logo=spring)
![MySQL](https://img.shields.io/badge/MySQL-8.x-blue?style=for-the-badge&logo=mysql)
![JWT](https://img.shields.io/badge/JWT-0.11.5-black?style=for-the-badge&logo=jsonwebtokens)
![Firebase](https://img.shields.io/badge/Firebase-9.2.0-orange?style=for-the-badge&logo=firebase)
![Status](https://img.shields.io/badge/Status-Concluído-success?style=for-the-badge)

## 📋 Sobre o Projeto

API RESTful robusta desenvolvida para gerenciar operações de uma loja de autopeças/oficina mecânica, oferecendo controle completo de estoque, pedidos, clientes e notificações em tempo real.

O sistema resolve problemas críticos de negócio como:
- **Controle de estoque inteligente** com alertas automáticos em múltiplos níveis
- **Gestão completa de pedidos** desde a criação até a finalização
- **Notificações multicanal** (Telegram + Firebase Push Notifications)
- **Relatórios de vendas** com análise temporal
- **Segurança robusta** com autenticação JWT

---

## ✨ Funcionalidades Principais

### 🎯 Gestão de Pedidos
- ✅ Criação de pedidos com múltiplos itens
- ✅ Controle de status com 7 estados (Novo → Pago → Em Transporte → Entregue → Pós-Venda → Finalizado / Cancelado)
- ✅ Atualização automática de estoque ao confirmar pedido
- ✅ Cálculo automático de valores (preços promocionais, frete)
- ✅ Filtros avançados (período, cliente, status combinados)
- ✅ Busca por ID do pedido (público para rastreamento)

### 📦 Controle Inteligente de Estoque
- ✅ Três níveis de alerta: **Normal** → **Baixo** → **Crítico** → **Esgotado**
- ✅ Desabilitação automática de produtos sem estoque
- ✅ Notificações push em tempo real via Firebase
- ✅ Detecção de mudanças de status com logs detalhados
- ✅ Proteção contra venda de produtos indisponíveis

### 🔔 Sistema de Notificações
- ✅ **Telegram Bot**: Alerta instantâneo de novos pedidos com link direto ao dashboard
- ✅ **Firebase Cloud Messaging**: Push notifications para alertas de estoque
- ✅ Notificações customizadas por nível de criticidade (⚠️ Baixo, 🚨 Crítico, 🔴 Esgotado)
- ✅ URLs dinâmicas para acesso direto ao produto

### 🔐 Segurança
- ✅ Autenticação stateless com **JWT** (tokens válidos por 7 dias)
- ✅ Proteção de endpoints sensíveis via Spring Security
- ✅ CORS configurado para múltiplos ambientes (desenvolvimento e produção)
- ✅ Endpoints públicos estratégicos (catálogo, rastreamento de pedidos)

### 📊 Relatórios e Análises
- ✅ Total de vendas por período (última semana, mês, customizado)
- ✅ Análise de vendas agrupadas por data
- ✅ Filtros combinados para relatórios gerenciais

### 🖼️ Gestão de Mídia
- ✅ Upload de imagens de produtos
- ✅ Armazenamento em diretório público
- ✅ Servir imagens via endpoints públicos

---

## 🛠️ Tecnologias Utilizadas

### Core
- **Java 21** - Linguagem principal com LTS
- **Spring Boot 3.4.3** - Framework base
- **Spring Data JPA** - Persistência e ORM
- **Hibernate** - Implementação JPA
- **MySQL 8.x** - Banco de dados relacional

### Segurança
- **Spring Security** - Autenticação e autorização
- **JJWT 0.11.5** - JSON Web Tokens (io.jsonwebtoken)
- **Auth0 JWT 4.4.0** - Biblioteca adicional JWT
- **CORS** - Configuração multi-origem

### Integrações
- **Firebase Admin SDK 9.2.0** - Push notifications (FCM)
- **Telegram Bot API** - Notificações instantâneas de pedidos
- **Apache HttpClient 5** - Cliente HTTP para integrações

### Utilitários
- **Lombok 1.18.34** - Redução de boilerplate
- **MapStruct 1.5.5** - Mapeamento de objetos
- **Caelum Stella 2.1.2** - Validação de documentos brasileiros (CPF)
- **Spring WebFlux** - Cliente reativo para chamadas assíncronas

### Documentação
- **SpringDoc OpenAPI 2.7.0** - Documentação interativa da API
- **Swagger UI** - Interface visual de testes

### Ferramentas de Build
- **Maven 3.8+** - Gerenciamento de dependências
- **Maven Compiler Plugin 3.13.0** - Compilação Java 21

---

## 🏗️ Arquitetura e Decisões Técnicas

### Diagrama de Arquitetura

```mermaid
graph TB
    subgraph "Cliente"
        CLIENT[Frontend Angular/PWA]
        MOBILE[Mobile Browser]
    end

    subgraph "API Spring Boot"
        subgraph "Controllers"
            PC[PedidoController]
            PRC[PecaController]
            CC[ClienteController]
            CAT[CategoriaController]
            AUTH[AuthController]
        end

        subgraph "Security Layer"
            JWT[JWT Token Filter]
            SEC[Spring Security]
        end

        subgraph "Services"
            PS[PedidoService]
            PRS[PecaService]
            US[UploadService]
            NS[NotificationService]
            BS[BotService]
        end

        subgraph "Repositories"
            PDAO[PedidoDAO]
            PRDAO[PecaDAO]
            CDAO[ClienteDAO]
            CATDAO[CategoriaDAO]
        end
    end

    subgraph "Banco de Dados"
        DB[(MySQL 8.x)]
    end

    subgraph "Serviços Externos"
        TELEGRAM[Telegram Bot API]
        FIREBASE[Firebase Cloud Messaging]
        FS[File System<br/>Imagens]
    end

    CLIENT -->|HTTP/HTTPS| PC
    CLIENT -->|HTTP/HTTPS| PRC
    MOBILE -->|HTTP/HTTPS| PC
    
    PC --> JWT
    PRC --> JWT
    AUTH -.->|Gera Token| JWT
    
    JWT --> SEC
    SEC --> PS
    SEC --> PRS
    
    PS --> PDAO
    PS --> PRDAO
    PS --> NS
    PS --> BS
    
    PRS --> PRDAO
    PRS --> US
    
    PDAO --> DB
    PRDAO --> DB
    CDAO --> DB
    CATDAO --> DB
    
    NS -->|Push Notifications| FIREBASE
    BS -->|Alerta Pedidos| TELEGRAM
    US -->|Salvar Arquivos| FS
    
    FIREBASE -.->|Notifica| CLIENT
    FIREBASE -.->|Notifica| MOBILE

    style CLIENT fill:#e1f5ff
    style MOBILE fill:#e1f5ff
    style DB fill:#fff4e1
    style TELEGRAM fill:#e8f5e9
    style FIREBASE fill:#ffe8e8
    style JWT fill:#ffe8f5
    style SEC fill:#ffe8f5
```

### Fluxo de Criação de Pedido

```mermaid
sequenceDiagram
    autonumber
    actor Cliente
    participant API as PedidoController
    participant Service as PedidoService
    participant PecaDAO
    participant NotificationService
    participant BotService
    participant DB as MySQL
    participant Telegram
    participant Firebase

    Cliente->>API: POST /pedido
    API->>Service: inserirPedido(pedido)
    
    loop Para cada item do pedido
        Service->>PecaDAO: findById(pecaId)
        PecaDAO->>DB: SELECT
        DB-->>PecaDAO: Peça
        PecaDAO-->>Service: Peça
        
        Service->>Service: validar estoque
        alt Estoque insuficiente
            Service-->>API: null (erro)
            API-->>Cliente: 400 Bad Request
        end
        
        Service->>Service: calcular preços
        Service->>Service: atualizar estoque
        Service->>Service: verificar status estoque
        
        alt Estoque mudou para crítico/baixo/zerado
            Service->>NotificationService: notificar mudança
            NotificationService->>Firebase: enviar push notification
            Firebase-->>Cliente: 📱 Notificação
        end
        
        Service->>PecaDAO: save(peca)
        PecaDAO->>DB: UPDATE tbl_peca
    end
    
    Service->>PecaDAO: save(pedido)
    PecaDAO->>DB: INSERT tbl_pedido + tbl_item_pedido
    DB-->>Service: Pedido salvo
    
    Service->>BotService: sendBotMessage(pedidoId)
    BotService->>Telegram: POST sendMessage
    Telegram-->>BotService: ✅ Mensagem enviada
    
    Service-->>API: Pedido criado
    API-->>Cliente: 201 Created
```

### Fluxo de Autenticação JWT

```mermaid
sequenceDiagram
    autonumber
    actor User
    participant Controller as AuthController
    participant Security as Spring Security
    participant JWT as JWTTokenUtil
    participant DB as MySQL

    User->>Controller: POST /login {username, password}
    Controller->>Security: authenticate(credentials)
    Security->>DB: SELECT usuario WHERE username=?
    DB-->>Security: Usuario encontrado
    
    Security->>Security: validar senha
    alt Credenciais inválidas
        Security-->>Controller: AuthenticationException
        Controller-->>User: 401 Unauthorized
    end
    
    Security-->>Controller: Authentication OK
    Controller->>JWT: generateToken(usuario)
    JWT->>JWT: criar payload (subject, issuer, expiration)
    JWT->>JWT: assinar com HS256
    JWT-->>Controller: "Bearer eyJhbG..."
    Controller-->>User: 200 OK {token}
    
    Note over User: Armazena token
    
    User->>Controller: GET /pedido/status/1<br/>Authorization: Bearer token
    Controller->>JWT: decodeToken(request)
    JWT->>JWT: validar assinatura
    JWT->>JWT: validar issuer
    JWT->>JWT: validar expiração
    
    alt Token inválido/expirado
        JWT-->>Controller: null
        Controller-->>User: 403 Forbidden
    end
    
    JWT-->>Controller: Authentication OK
    Controller->>Controller: processar requisição
    Controller-->>User: 200 OK {dados}
```

### Sistema de Notificações - Detecção de Mudanças

```mermaid
flowchart TD
    START([Pedido Confirmado]) --> LOOP{Para cada<br/>item}
    
    LOOP -->|Item| GET[Buscar Peça no BD]
    GET --> SAVE_OLD[Salvar Estoque Anterior]
    SAVE_OLD --> CALC[Calcular Novo Estoque<br/>anterior - quantidade]
    CALC --> UPDATE[Atualizar Peça no BD]
    UPDATE --> CHECK{Estoque<br/>mudou?}
    
    CHECK -->|Não| LOOP
    CHECK -->|Sim| GET_STATUS_OLD[Calcular Status Anterior]
    GET_STATUS_OLD --> GET_STATUS_NEW[Calcular Status Atual]
    GET_STATUS_NEW --> COMPARE{Status<br/>mudou?}
    
    COMPARE -->|Não| LOOP
    COMPARE -->|Sim| WHICH{Qual novo<br/>status?}
    
    WHICH -->|BAIXO| NOTIF_BAIXO[⚠️ Notificar Estoque Baixo]
    WHICH -->|CRITICO| NOTIF_CRIT[🚨 Notificar Estoque Crítico]
    WHICH -->|ESGOTADO| NOTIF_ZERO[🔴 Notificar Esgotado<br/>+ Desabilitar Produto]
    
    NOTIF_BAIXO --> FIREBASE1[Firebase Push:<br/>titulo, corpo, url, tipo]
    NOTIF_CRIT --> FIREBASE2[Firebase Push:<br/>titulo, corpo, url, tipo]
    NOTIF_ZERO --> FIREBASE3[Firebase Push:<br/>titulo, corpo, url, tipo]
    
    FIREBASE1 --> LOOP
    FIREBASE2 --> LOOP
    FIREBASE3 --> LOOP
    
    LOOP -->|Próximo| LOOP
    LOOP -->|Fim| SAVE_PEDIDO[Salvar Pedido]
    SAVE_PEDIDO --> TELEGRAM[Enviar Telegram<br/>Novo Pedido]
    TELEGRAM --> END([Fim])

    style START fill:#e8f5e9
    style END fill:#e8f5e9
    style NOTIF_BAIXO fill:#fff9c4
    style NOTIF_CRIT fill:#ffccbc
    style NOTIF_ZERO fill:#ffcdd2
    style FIREBASE1 fill:#e1f5ff
    style FIREBASE2 fill:#e1f5ff
    style FIREBASE3 fill:#e1f5ff
    style TELEGRAM fill:#c8e6c9
```

### Padrão de Camadas
```
Controller → Service → DAO (Repository) → Database
```

### Sistema de Notificações Inteligente

O projeto implementa um **sistema dual de notificações**:

**1. Telegram Bot (Pedidos)**
- Acionado automaticamente ao criar novo pedido
- Envia mensagem formatada com ID do pedido
- Inclui link direto ao dashboard administrativo

**2. Firebase Push (Estoque)**
- Monitora mudanças de status do estoque em tempo real
- Envia apenas quando há **piora** no status (evita spam)
- Notificações categorizadas por urgência:
  - ⚠️ **BAIXO**: Quantidade ≤ estoque mínimo
  - 🚨 **CRÍTICO**: Quantidade ≤ estoque crítico
  - 🔴 **ESGOTADO**: Quantidade = 0

**Lógica de Detecção de Mudanças:**
```java
// Compara status anterior vs. atual antes de enviar notificação
if (!statusAnterior.equals(statusAtual)) {
    switch (statusAtual) {
        case "BAIXO":    → notificarEstoqueBaixo()
        case "CRITICO":  → notificarEstoqueCritico()
        case "ESGOTADO": → notificarEstoqueZerado()
    }
}
```

### Controle Transacional de Estoque

Ao confirmar um pedido:
1. Valida disponibilidade de **cada item** (`peca.podeVender(quantidade)`)
2. Atualiza estoque atomicamente
3. Desabilita produto se estoque = 0
4. Calcula preços (promocional tem prioridade)
5. Persiste pedido e itens
6. Dispara notificações

### Segurança JWT

**Geração de Token:**
- Subject: username do usuário
- Issuer: `*Gabriel Nunez*`
- Expiration: 7 dias
- Algorithm: HS256
- Secret: configurada em constante

**Validação:**
- Verifica subject válido
- Confirma issuer correto
- Valida expiração
- Filtro customizado (`TokenFilter`) processa requests

### CORS Multi-Ambiente

Configuração explícita para:
- `http://localhost:4200` (desenvolvimento Angular)
- `https://projetoreal.dev.br` (produção)
- `https://www.projetoreal.dev.br` (produção com www)

---

## 📡 Documentação da API

### 📮 Postman Collection (Recomendado)
A forma mais prática de testar a API é através do Postman:

**Collection completa com todos os endpoints:**
[📘 Documentação Postman](https://documenter.getpostman.com/view/37859421/2sBXVZpFDn)

A collection inclui:
- ✅ Todos os endpoints organizados por categoria
- ✅ Exemplos de requisições prontos para uso
- ✅ Variáveis de ambiente pré-configuradas
- ✅ Testes automatizados de resposta
- ✅ Documentação detalhada de cada endpoint

### Swagger UI (Opcional)
Também disponível via Swagger para visualização:
```
http://localhost:8080/swagger-ui.html
http://localhost:8080/v3/api-docs
```

> **💡 Dica:** Para desenvolvimento e testes, recomendamos usar o Postman pela praticidade e recursos avançados de teste.

---

## 🔌 Principais Endpoints

### 🔓 Públicos (Sem Autenticação)

#### Produtos
```http
GET /peca/todos
GET /peca/{id}
GET /peca/categoria/{idCategoria}
GET /peca/busca?nome={nome}
```

#### Pedidos
```http
POST /pedido
GET /pedido/search/{id}
```

#### Categorias
```http
GET /categoria_peca
GET /categoria_by_id?id={id}
```

#### Clientes
```http
GET /cliente/{id}
```

#### Fretes
```http
GET /fretes/prefixo/{cep}
```

### 🔒 Autenticados (Requer JWT)

#### Gestão de Pedidos
```http
GET    /pedido/status/{status}
PATCH  /pedido/status
GET    /pedido/periodo?inicio={data}&fim={data}
GET    /pedido/vendas-semana?inicio={data}&fim={data}
PUT    /pedido/{id}
```

#### Gestão de Produtos
```http
POST   /peca
PUT    /peca/{id}
DELETE /peca/{id}
POST   /peca/upload
```

#### Categorias
```http
POST   /categoria_peca
PUT    /categoria_peca/{id}
DELETE /categoria_peca/{id}
```

---

## 📝 Exemplos de Requisições

### Criar Pedido

**Request:**
```http
POST /pedido
Content-Type: application/json
```

```json
{
  "cliente": {
    "id": 15
  },
  "valorFrete": 25.00,
  "retirar": 0,
  "observacoes": "Entrega pela manhã, tocar campainha",
  "itensPedido": [
    {
      "peca": { "id": 42 },
      "qtdtItem": 2
    },
    {
      "peca": { "id": 87 },
      "qtdtItem": 1
    }
  ]
}
```

**Response:** `201 Created`
```json
{
  "id": 1523,
  "dataPedido": "2026-01-25",
  "valorTotal": 347.50,
  "valorFrete": 25.00,
  "status": 1,
  "observacoes": "Entrega pela manhã, tocar campainha",
  "cliente": {
    "id": 15,
    "nome": "João Silva"
  },
  "itensPedido": [
    {
      "id": 3401,
      "qtdtItem": 2,
      "precoUnitario": 89.90,
      "precoTotal": 179.80,
      "peca": {
        "id": 42,
        "nome": "Filtro de Óleo"
      }
    },
    {
      "id": 3402,
      "qtdtItem": 1,
      "precoUnitario": 142.70,
      "precoTotal": 142.70,
      "peca": {
        "id": 87,
        "nome": "Pastilha de Freio"
      }
    }
  ]
}
```

**Comportamento Automático:**
- ✅ Estoque dos produtos é reduzido
- ✅ Preços promocionais aplicados automaticamente
- ✅ Status inicial: `1` (NOVO_PEDIDO)
- ✅ Notificação enviada ao Telegram
- ✅ Produto desabilitado se estoque zerar
- ✅ Push notification se estoque ficar baixo/crítico

---

### Autenticação

**Request:**
```http
POST /login
Content-Type: application/json
```

```json
{
  "username": "admin",
  "password": "senha123"
}
```

**Response:** `200 OK`
```json
{
  "token": "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlzcyI6IipHYWJyaWVsIE51bmV6KiIsImV4cCI6MTczODAxNjQwMH0.xYz..."
}
```

**Uso do Token:**
```http
GET /pedido/status/1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

---

### Filtrar Pedidos (Exemplo Avançado)

**Request:**
```http
POST /pedido/filtro
Content-Type: application/json
Authorization: Bearer {token}
```

```json
{
  "dataInicio": "2026-01-01",
  "dataFim": "2026-01-31",
  "nome": "Silva",
  "novo": 1,
  "pago": 1,
  "cancelado": 0
}
```

**Response:** Lista de pedidos que atendem **todos** os critérios:
- Data entre 01/01 e 31/01
- Cliente com "Silva" no nome
- Status = Novo OU Pago

---

## 🚀 Como Executar

### Pré-requisitos

- **JDK 21** (Oracle ou OpenJDK)
- **MySQL 8.x**
- **Maven 3.8+**
- **Postman** (para testes da API)
- Conta **Telegram** (para criar bot)
- Projeto **Firebase** (para push notifications)

### 1. Clone o Repositório

```bash
git clone https://github.com/seu-usuario/oficina-mecanica-api.git
cd oficina-mecanica-api
```

### 2. Configure o Banco de Dados

Execute no MySQL:

```sql
CREATE DATABASE db_projetoreal CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE USER 'springuser'@'localhost' IDENTIFIED BY 'SuaSenhaSegura123!';

GRANT ALL PRIVILEGES ON db_projetoreal.* TO 'springuser'@'localhost';

FLUSH PRIVILEGES;
```

### 3. Configure Variáveis de Ambiente

Crie o arquivo `src/main/resources/application.properties`:

```properties
# Banco de Dados
spring.datasource.url=jdbc:mysql://localhost:3306/db_projetoreal?useTimezone=true&serverTimezone=America/Sao_Paulo
spring.datasource.username=springuser
spring.datasource.password=SuaSenhaSegura123!
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=update

# Swagger
springdoc.packagesToScan=com.gabriel_nunez.oficina_mecanica.controller
springdoc.api-docs.enabled=true

# Frontend URL (para notificações)
app.frontend.url=http://localhost:4200

# Telegram Bot
telegrambot_chat_id=SEU_CHAT_ID_AQUI
telegrambot_url=https://api.telegram.org/botSEU_TOKEN_AQUI/sendMessage
telegrambot_msg=Novo pedido #%PED% recebido! Acesse: ${app.frontend.url}/admin/pedidos/%PED%

# Logs (opcional, para debug)
logging.level.org.springframework.security=INFO
logging.level.com.gabriel_nunez.oficina_mecanica=DEBUG
```

### 4. Configure Firebase

1. Baixe o arquivo `serviceAccountKey.json` do Firebase Console
2. Coloque na raiz do projeto: `src/main/resources/firebase-adminsdk.json`
3. **IMPORTANTE:** Adicione ao `.gitignore`

### 5. Execute a Aplicação

```bash
mvn clean install
mvn spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

### 6. Acesse a Documentação

**Postman (Recomendado):**
1. Acesse: [Postman Collection](https://documenter.getpostman.com/view/37859421/2sBXVZpFDn)
2. Clique em "Run in Postman"
3. Configure a variável de ambiente `base_url` para `http://localhost:8080`

**Swagger UI (Opcional):**
- `http://localhost:8080/swagger-ui.html`
- `http://localhost:8080/v3/api-docs`

---

## 🧪 Testando a API

### Configurar Postman

1. **Importe a Collection:**
   - Acesse a [documentação Postman](https://documenter.getpostman.com/view/37859421/2sBXVZpFDn)
   - Clique em "Run in Postman" ou baixe o JSON

2. **Configure o Environment:**
   ```json
   {
     "base_url": "http://localhost:8080",
     "token": ""
   }
   ```

3. **Fluxo de Teste Básico:**

#### Passo 1: Autenticar (se necessário)
```
POST {{base_url}}/login
Content-Type: application/json

{
  "username": "admin",
  "password": "senha123"
}
```

Copie o token retornado e adicione à variável `{{token}}` no environment.

#### Passo 2: Listar Produtos (Público)
```
GET {{base_url}}/peca/todos
```

#### Passo 3: Criar Pedido (Público)
```
POST {{base_url}}/pedido
Content-Type: application/json

{
  "cliente": {"id": 1},
  "valorFrete": 15.00,
  "retirar": 0,
  "itensPedido": [
    {
      "peca": {"id": 1},
      "qtdtItem": 2
    }
  ]
}
```

#### Passo 4: Consultar Pedidos (Autenticado)
```
GET {{base_url}}/pedido/status/1
Authorization: Bearer {{token}}
```

### Testes com cURL (Alternativa)

**Listar produtos:**
```bash
curl http://localhost:8080/peca/todos
```

**Buscar produto específico:**
```bash
curl http://localhost:8080/peca/1
```

**Criar pedido:**
```bash
curl -X POST http://localhost:8080/pedido \
  -H "Content-Type: application/json" \
  -d '{
    "cliente": {"id": 1},
    "valorFrete": 15.00,
    "retirar": 0,
    "itensPedido": [
      {"peca": {"id": 1}, "qtdtItem": 2}
    ]
  }'
```

**Login:**
```bash
curl -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"senha123"}'
```

**Usar token (substitua TOKEN):**
```bash
curl http://localhost:8080/pedido/status/1 \
  -H "Authorization: Bearer TOKEN"
```

### Verificar Logs

Durante os testes, monitore os logs da aplicação para ver:
- ✅ Execução de queries SQL
- ✅ Notificações enviadas
- ✅ Mudanças de status de estoque
- ✅ Erros e validações

```bash
# Logs aparecem no console onde você executou mvn spring-boot:run
```

---

## 📊 Modelo de Dados

### Principais Entidades

**Pedido**
- `id` (PK)
- `dataPedido`
- `valorTotal`
- `valorFrete`
- `status` (1-7)
- `observacoes`
- `cliente_id` (FK)

**ItemPedido**
- `id` (PK)
- `pedido_id` (FK)
- `peca_id` (FK)
- `qtdtItem`
- `precoUnitario`
- `precoTotal`

**Peca** (Produto)
- `id` (PK)
- `nome`
- `descricao`
- `preco`
- `precoPromo`
- `quantidadeEstoque`
- `estoqueMinimo`
- `estoqueCritico`
- `disponivel` (0/1)
- `categoria_id` (FK)

**Cliente**
- `id` (PK)
- `nome`
- `email`
- `telefone`
- `cpf`
- `endereco`

---

## 🔐 Segurança - Boas Práticas

### ⚠️ NUNCA Versione

Adicione ao `.gitignore`:

```
application.properties
application-prod.properties
firebase-adminsdk.json
*.p12
.env
```

### ✅ Use Arquivo de Exemplo

Crie `application.properties.example`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/SEU_BANCO
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA

telegrambot_chat_id=SEU_CHAT_ID
telegrambot_url=https://api.telegram.org/botSEU_TOKEN/sendMessage

app.frontend.url=http://localhost:4200
```

### 🔒 Recomendações

- Altere a `SECRET_KEY` do JWT em produção
- Use HTTPS em produção (certificado SSL configurado)
- Implemente rate limiting
- Configure logging adequado
- Use variáveis de ambiente em produção

---

## 📈 Melhorias Futuras

Possíveis evoluções do projeto:

- [ ] Implementar testes unitários (JUnit 5 + Mockito)
- [ ] Testes de integração com TestContainers
- [ ] Cache com Redis para produtos
- [ ] Paginação de resultados
- [ ] Exportação de relatórios em PDF/Excel
- [ ] Integração com gateway de pagamento (Stripe/PagSeguro)
- [ ] Sistema de cupons de desconto
- [ ] API de rastreamento de entregas
- [ ] Auditoria de alterações (Spring Data Envers)
- [ ] Métricas e monitoring (Actuator + Prometheus)

---

## 👨‍💻 Autor

**Gabriel Núñez**

Desenvolvedor Backend Java | Spring Boot Specialist

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/gabriel-nunez-contasti/)
[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/gajonuco)
[![Email](https://img.shields.io/badge/Email-D14836?style=for-the-badge&logo=gmail&logoColor=white)](mailto:gajonuco@gmail.com)


---

<div align="center">

**⭐ Se este projeto foi útil, considere dar uma estrela!**

Made with ☕ and ❤️ by Gabriel Núñez

</div>
