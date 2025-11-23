# Clojure - Todo List

## Nome do Aluno : Danniel Cristie 

## Descrição

Este projeto é uma aplicação "Todo List" full-stack desenvolvida com Clojure e ClojureScript.

**Tecnologias:**
- **Backend:** Clojure, SQLite
- **Frontend:** ClojureScript, Reagent, shadow-cljs
- **Ambiente:** Docker, Docker Compose

## Como Rodar


### Rodando com Docker

#### Pré-requisitos

- Docker
- Docker Compose

#### Instruções

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/dannielcristie/clojure-todo-list.git
    cd clojure-todo-list
    ```

2.  **Inicie a aplicação com Docker Compose:**
    ```bash
    docker compose up -d
    ```
  

3.  **Acesse a aplicação:**
    Abra seu navegador e acesse `http://localhost:8000`.

4.  **Para parar a aplicação:**
    ```bash
    docker compose down
    ```

### Rodando Localmente

Para executar o projeto diretamente na sua máquina, você precisará ter o ambiente Clojure e Node.js configurado.

#### Pré-requisitos

- Java JDK (versão 11 ou superior)
- Clojure CLI
- Node.js e npm

#### Instruções

1.  **Clone o repositório e instale as dependências:**
    ```bash
    git clone https://github.com/dannielcristie/clojure-todo-list.git
    cd clojure-todo-list
    npm install
    ```

2.  **Inicie o servidor de backend:**
    Em um terminal, execute:
    ```bash
    clj -M:run
    ```
    O servidor estará rodando na porta `3000`.

3.  **Inicie o servidor de frontend:**
    Em **outro** terminal, execute:
    ```bash
    npx shadow-cljs watch app
    ```
    O servidor de desenvolvimento do frontend estará rodando na porta `8000`.

4.  **Acesse a aplicação:**
    Abra seu navegador e acesse `http://localhost:8000`.

## Backend API Endpoints

A API do backend é acessível na porta `3000`.

### Listar todos os "todos"

- **Método:** `GET`
- **Path:** `/api/todos`
- **Exemplo de Resposta:**
  ```json
  {
    "todos": [
      {
        "id": 1,
        "title": "Minha primeira tarefa",
        "description": null,
        "completed": 0,
        "created_at": "..."
      }
    ]
  }
  ```

### Criar um novo "todo"

- **Método:** `POST`
- **Path:** `/api/todos`
- **Exemplo de Payload:**
  ```json
  {
    "title": "Minha nova tarefa"
  }
  ```

### Alternar o status de um "todo"

- **Método:** `POST`
- **Path:** `/api/todos/:id/toggle`
- **Exemplo de Uso:**
  ```bash
  curl -X POST http://localhost:3000/api/todos/1/toggle
  ```

### Deletar um "todo"

- **Método:** `DELETE`
- **Path:** `/api/todos/:id`
- **Exemplo de Uso:**
  ```bash
  curl -X DELETE http://localhost:3000/api/todos/1
  ```
