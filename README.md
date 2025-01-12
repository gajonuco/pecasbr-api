# Oficina Mecânica API

**Versão:** 0.0.1-SNAPSHOT

Este projeto é uma API RESTful desenvolvida em **Spring Boot** para gerenciamento de uma oficina mecânica. A aplicação permite o cadastro, edição, exclusão e listagem de clientes, serviços, peças e orçamentos, incluindo funcionalidades robustas de validação, tratamento de erros e integração com banco de dados PostgreSQL.

---

## Tecnologias Utilizadas

- **Java**: Versão 17 ou superior.
- **Spring Boot**: Framework para criação de aplicações Java.
- **Spring Data JPA**: Abstração de acesso a dados.
- **Hibernate Validator**: Validação de dados com Bean Validation.
- **PostgreSQL**: Banco de dados relacional.
- **Lombok**: Reduz a verbosidade do código.
- **Postman**: Para testes de endpoints.

---

## Requisitos para Execução

1. **Java 17 ou superior**.
2. **Maven** para gerenciar dependências e build.
3. **PostgreSQL** instalado e configurado.
4. Ferramentas como **Postman** (opcional) para testar a API.

---

## Configuração do Banco de Dados

Certifique-se de que o PostgreSQL esteja em execução e crie o banco de dados necessário:

```sql
CREATE DATABASE oficina_mecanica;
```

### Credenciais de Conexão

No arquivo `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/oficina_mecanica
spring.datasource.username=postgres
spring.datasource.password=123456
spring.jpa.hibernate.ddl-auto=update
```

**Nota:** Atualize `username` e `password` conforme suas credenciais.

---

## Instalação e Execução

1. Clone o repositório:

   ```bash
   git clone https://github.com/seu-usuario/oficina-mecanica.git
   ```

2. Acesse o diretório do projeto:

   ```bash
   cd oficina-mecanica
   ```

3. Compile e instale as dependências com o Maven:

   ```bash
   mvn clean install
   ```

4. Execute a aplicação:

   ```bash
   mvn spring-boot:run
   ```

---

## Endpoints Disponíveis

### Clientes

| Método | Endpoint             | Descrição                   |
| ------ | -------------------- | --------------------------- |
| GET    | `/api/clientes`      | Lista todos os clientes.    |
| POST   | `/api/clientes`      | Cadastra um novo cliente.   |
| PUT    | `/api/clientes/{id}` | Edita um cliente existente. |
| DELETE | `/api/clientes/{id}` | Deleta um cliente pelo ID.  |

### Peças

| Método | Endpoint          | Descrição                 |
| ------ | ----------------- | ------------------------- |
| GET    | `/api/pecas`      | Lista todas as peças.     |
| POST   | `/api/pecas`      | Cadastra uma nova peça.   |
| PUT    | `/api/pecas/{id}` | Edita uma peça existente. |
| DELETE | `/api/pecas/{id}` | Deleta uma peça pelo ID.  |

### Serviços

| Método | Endpoint             | Descrição                   |
| ------ | -------------------- | --------------------------- |
| GET    | `/api/servicos`      | Lista todos os serviços.    |
| POST   | `/api/servicos`      | Cadastra um novo serviço.   |
| PUT    | `/api/servicos/{id}` | Edita um serviço existente. |
| DELETE | `/api/servicos/{id}` | Deleta um serviço pelo ID.  |

### Orçamentos

| Método | Endpoint                      | Descrição                   |
| ------ | ----------------------------- | --------------------------- |
| GET    | `/api/orcamentos`             | Lista todos os orçamentos.  |
| POST   | `/api/orcamentos`             | Cadastra um novo orçamento. |
| POST   | `/api/orcamentos/especificos` | Lista orçamentos por IDs.   |

---
 
 [Link da Collection: Oficina](https://automaes-teste.postman.co/workspace/Team-Workspace~3aafc675-8fb6-4587-8df5-de3bbc545674/collection/37859421-c02baa7f-7b07-402c-b012-d6ddc5f10e0d?action=share&creator=37859421)

## Dependências do Projeto

As principais dependências estão definidas no arquivo `pom.xml`:

```xml
<dependencies>
   <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-jpa</artifactId>
   </dependency>
   <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
   </dependency>
   <dependency>
      <groupId>org.postgresql</groupId>
      <artifactId>postgresql</artifactId>
      <scope>runtime</scope>
   </dependency>
   <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <optional>true</optional>
   </dependency>
   <dependency>
      <groupId>jakarta.validation</groupId>
      <artifactId>jakarta.validation-api</artifactId>
      <version>3.0.2</version>
   </dependency>
</dependencies>
```

---

## Testando com Postman

Um arquivo de coleção do **Postman** foi incluído para facilitar os testes. Importe o arquivo `Oficina.postman_collection.json` no Postman e execute os endpoints conforme necessário.

### Exemplo de Requisição

#### Cadastro de Cliente

**Endpoint:** `POST /api/clientes`

**Body:**

```json
{
   "nome": "João da Silva",
   "email": "joao.silva@example.com",
   "telefone": "123456789",
   "endereco": {
       "rua": "Rua das Flores",
       "numero": "123",
       "bairro": "Centro",
       "cidade": "São Paulo",
       "estado": "SP",
       "cep": "01001-000"
   }
}
```

**Resposta esperada:**

```json
{
    "id": 1,
    "nome": "João da Silva",
    "email": "joao.silva@example.com",
    "telefone": "123456789",
    "endereco": {
        "rua": "Rua das Flores",
        "numero": "123",
        "bairro": "Centro",
        "cidade": "São Paulo",
        "estado": "SP",
        "cep": "01001-000"
    }
}
```

---

## Autor

**Gabriel José Núñez Contasti**

- GitHub: [https://github.com/gajonuco](https://github.com/gajonunco)
- LinkedIn: [https://linkedin.com/in/gabriel-nunez-contasti](https://linkedin.com/in/gabriel-nunez-contasti)

---

**Boas Práticas Aplicadas:**

1. Arquitetura bem estruturada utilizando padrões REST.
2. Tratamento de exceções detalhado para melhorar a experiência do usuário.
3. Validação robusta de entradas com Bean Validation.
4. Uso de DTOs para desacoplar a lógica interna da exposição de dados.

**Nota:** Caso encontre problemas ou queira sugerir melhorias, envie um Pull Request!

