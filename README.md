# Conjunto de APIS

## Paths

### Authentication Controller

#### `/auth/user`
- **PUT**
  - **Operation ID**: `transformAdminInUser`
  - **Request Body**: `DTOTransform`
  - **Responses**:
    - `200 OK`

#### `/auth/user/put/{id}/desactive`
- **PUT**
  - **Operation ID**: `desactive`
  - **Parameters**:
    - `id` (in path, required, type: integer)
  - **Responses**:
    - `200 OK`

#### `/auth/user/put/{id}/active`
- **PUT**
  - **Operation ID**: `active`
  - **Parameters**:
    - `id` (in path, required, type: integer)
  - **Responses**:
    - `200 OK`

#### `/auth/user/atualizar/{id}`
- **PUT**
  - **Operation ID**: `update`
  - **Parameters**:
    - `id` (in path, required, type: integer)
  - **Request Body**: `UpdateUser`
  - **Responses**:
    - `200 OK`

#### `/auth/admin`
- **PUT**
  - **Operation ID**: `transformUserInAdmin`
  - **Request Body**: `DTOTransform`
  - **Responses**:
    - `200 OK`

#### `/auth/register`
- **POST**
  - **Operation ID**: `signUp`
  - **Request Body**: `SignUpRequest`
  - **Responses**:
    - `200 OK`

#### `/auth/refresh`
- **POST**
  - **Operation ID**: `refresh`
  - **Request Body**: `RefreshTokenRequest`
  - **Responses**:
    - `200 OK`

#### `/auth/login`
- **POST**
  - **Operation ID**: `signin`
  - **Request Body**: `SigninRequest`
  - **Responses**:
    - `200 OK`

### Other Endpoints

#### `/roteiro`
- **GET**
  - **Operation ID**: `getRoteiros`
  - **Responses**:
    - `200 OK`

- **POST**
  - **Operation ID**: `postRoteiroDeViagens`
  - **Request Body**: `DTORoteiroCityEData`
  - **Responses**:
    - `200 OK`

#### `/clima/city/real`
- **POST**
  - **Operation ID**: `postClimaReal`
  - **Request Body**: `DTOClimaNome`
  - **Responses**:
    - `200 OK`

#### `/clima/city/historico`
- **POST**
  - **Operation ID**: `postClimaHistorico`
  - **Request Body**: `DTOClimaNomeCidadeEData`
  - **Responses**:
    - `200 OK`

#### `/url`
- **GET**
  - **Operation ID**: `getAllUrls`
  - **Parameters**:
    - `pageable` (query, required)
  - **Responses**:
    - `200 OK`

- **POST**
  - **Operation ID**: `postUrl`
  - **Request Body**: `DTOUrlLonga`
  - **Responses**:
    - `200 OK`

### Authentication Flow (JWT)

- **Login Endpoint** (`/auth/login`):
  - To authenticate and receive a JWT token, a valid user needs to provide credentials (email and password).
  - **Request Body**: `SigninRequest`
  - **Responses**:
    - `200 OK`: Returns a JWT token upon successful authentication.

- **Refresh Token Endpoint** (`/auth/refresh`):
  - To refresh the JWT token, a valid refresh token needs to be provided.
  - **Request Body**: `RefreshTokenRequest`
  - **Responses**:
    - `200 OK`: Returns a new JWT token.

- **JWT Authorization**:
  - Once the user is authenticated and receives a JWT token, all subsequent requests to the API must include the token in the `Authorization` header as `Bearer <JWT Token>`.

---

## Components

### DTOTransform
```json
{
  "email": "string"
}
```

### UpdateUser
```json
{
  "username": "string",
  "email": "string",
  "password": "string",
  "userRole": "string",
  "active": "boolean"
}
```
### SignUpRequest
```json
{
  "id": "integer",
  "username": "string",
  "email": "string",
  "password": "string",
  "userRole": "string",
  "active": "boolean"
}
```
### SigninRequest
```json
{
  "email": "string",
  "password": "string"
}
```

### RefreshTokenRequest
```json 
{
  "token": "string"
}
```
### Pageable
```json 
{
  "page": "integer",
  "size": "integer",
  "sort": ["string"]
}
```

# Documentação no Swagger

A documentação da API pode ser acessada via Swagger, onde as rotas e endpoints estão descritos de forma interativa. Acesse a interface do Swagger para visualizar as operações disponíveis, os parâmetros e exemplos de respostas:

### Acesse a URL do Swagger:
Normalmente, a URL para acessar a documentação Swagger é algo como: 

### Visualização de Endpoints:
Na interface do Swagger, você poderá visualizar todos os endpoints disponíveis da API, incluindo os métodos GET, POST, PUT, DELETE, etc.

### Interação com a API:
Através do Swagger, é possível testar as rotas diretamente, fornecendo parâmetros e enviando requisições sem precisar de outro cliente HTTP.

### Exemplos de Respostas:
Para cada endpoint, a documentação inclui exemplos de requisições e respostas, permitindo entender melhor como a API funciona.

A documentação Swagger facilita a integração e utilização da API, oferecendo uma maneira intuitiva de explorar e testar a funcionalidade da aplicação.


