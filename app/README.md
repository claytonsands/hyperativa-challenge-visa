
# 🛡️ Secure Card API

Esta aplicação é uma API RESTful criada com Spring Boot, dividida em dois modulos (`auth-module` e `api-module`). Ela permite:

- Armazenar cartões de forma segura com criptografia.
- Realizar upload de arquivos `.txt` com lotes de cartões (layout posicional).
- Consultar a existência de um cartão pela versão completa do número (criptografado).
- Validar estrutura dos dados com regras personalizadas.
- Gerenciar autenticação JWT.
- Utilizar PostgreSQL em container Docker.

---

## 📁 Estrutura do Projeto

```bash
secure-card-api/
├── app/                  # Módulo principal com ponto de entrada (main)
├──── auth-module/          # Módulo com lógica de autenticação (JWT, filtros)
├──── api-module/           # Módulo com regras de negócio e endpoints
├──── infra/               # Arquivos de infraestrutura (Docker, init.sql)
└────── docker-compose.yml    # Inicialização dos containers
```

---

## ⚙️ Pré-requisitos

- Java 21+
- Maven 3.8+
- Docker e Docker Compose

---

## 🚀 Como executar localmente

1. **Clone o repositório**  
```bash
git clone https://github.com/claytonsands/hyperativa-challenge-visa.git
cd hyperativa-challenge-visa/app/infra
```

2. **Suba o PostgreSQL com Docker Compose**  
```bash
docker-compose up -d
```

3. **Execute a aplicação**  
```bash
cd app
mvn spring-boot:run
```

> A API será executada em: `http://localhost:8080`

---

## 🧪 Testando com Swagger

Acesse:

```
http://localhost:8080/swagger-ui/index.html
```

Você poderá testar todos os endpoints por lá, inclusive o **upload de arquivos `.txt`** e os endpoints protegidos via JWT.

---

## 🔐 Autenticação

- A aplicação usa JWT para autenticação.
- Inicialmente, o usuário está em memória:

```json
{
  "username": "admin",
  "password": "1234"
}
```

- Para autenticar, use o endpoint:

```
POST /token
```

> Isso irá retornar o `accessToken` que deve ser usado no header das próximas requisições:
```http
Authorization: Bearer <token>
```

---

## 📥 Upload de TXT (Layout posicional)

- Endpoint:
```
POST /api/v1/batches/upload
```

- O arquivo deve seguir o seguinte layout:

Cabeçalho (Primeira linha):
```
[01-29] NOME DO LOTE
[30-37] DATA (yyyyMMdd)
[38-45] LOTE
[46-51] QTD DE REGISTROS
```
Corpo (Linhas do meio):
```
[01-01]IDENTIFICADOR DA LINHA   
[02-07]NUMERAÇÃO NO LOTE   
[08-26]NÚMERO DE CARTAO COMPLETO
```
Rodapé (Ultima linha):
```
[01-08]LOTE   
[09-14]QTD DE REGISTROS
```

---

## 🧾 Endpoints principais

| Método | Endpoint                 | Descrição                                  |
|--------|--------------------------|---------------------------------------------|
| POST   | `/token`                 | Login e geração do token JWT               |
| POST   | `/api/v1/cards`          | Cadastro de cartão único                   |
| POST   | `/api/v1/batches/upload`  | Upload de arquivo `.txt` com lote de cartões |
| POST   | `/api/v1/cards/lookup`   | Verifica se cartão existe na base de dados |
| GET    | `/swagger-ui/index.html` | Documentação interativa via Swagger        |

---

## 🧱 Banco de Dados

- Utiliza PostgreSQL com volume persistente.
- Arquivo `init.sql` cria a estrutura inicial (tabelas `batch` e `card`).
- Tabela `card`:
  - Armazena o número do cartão criptografado (campo `card_encrypted` como `BYTEA`)
  - Também armazena o número mascarado para retorno seguro (`**** **** **** 1234`)

---

## 🔐 Segurança

- Criptografia de dados sensíveis com AES-256.
- Tokens JWT assinam e validam as sessões.
- Upload e validações de conteúdo estão protegidos por autenticação.

---

## 🛠️ Tecnologias e Bibliotecas

- Java 21
- Spring Boot 3
- Spring Security + JWT
- PostgreSQL
- JPA (Hibernate)
- Swagger (springdoc-openapi)
- Docker & Docker Compose

---

## ✅ Testes e Validações

- Validação de tamanho do `cardNumber` (16 dígitos)
- Validação estrutural do layout `.txt` posicional
- Validação de datas e contagem de registros

---

## ✍️ Exemplo de requisição para lookup

```json
POST /api/v1/cards/lookup

{
  "card_number": "1234567890123456"
}
```

Resposta:
```json
{
  "id": 123
}
```

---

## 📌 Observações

- Os dados de cartão são criptografados no momento da persistência.
- Os endpoints não retornam o número completo do cartão por segurança.
- A criptografia usa uma chave AES fixa para o exemplo, mas deve ser protegida via segredo (ex: Vault, AWS Secrets Manager) em produção.

---

## 🧑‍💻 Autor

Criado com ❤ com ajuda do ChatGPT e evoluído por [Clayton Monteiro].
