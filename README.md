# Trabalho em Clojure - Desenvolvimento de um Todo List

## Nome do Aluno

Cristie

## Link do Tutorial

[https://profsergiocosta.notion.site/Tutorial-Clojure-ClojureScript-Construindo-uma-Aplica-o-Persistente-e-Reativa-2a5cce975093807aa9f0f0cb0cf69645](https://profsergiocosta.notion.site/Tutorial-Clojure-ClojureScript-Construindo-uma-Aplica-o-Persistente-e-Reativa-2a5cce975093807aa9f0f0cb0cf69645)

## Descrição Breve

Este projeto é uma aplicação "Todo List" full-stack desenvolvida com Clojure e ClojureScript, seguindo o tutorial da disciplina de Programação Funcional.

**Tecnologias:**
- **Backend:** Clojure, Ring, Reitit, next.jdbc, SQLite
- **Frontend:** ClojureScript, Reagent, shadow-cljs
- **Ambiente:** Docker, Docker Compose

## Como Rodar

Este projeto é containerizado com Docker e é a forma recomendada de executá-lo para evitar problemas de ambiente.

### Pré-requisitos

- Docker
- Docker Compose

### Instruções

1.  **Clone o repositório:**
    ```bash
    git clone <URL do repositório>
    cd clojure-todo-list
    ```

2.  **Inicie a aplicação com Docker Compose:**
    ```bash
    docker compose up -d
    ```
    Este comando irá construir a imagem Docker (pode levar alguns minutos na primeira vez) e iniciar os serviços de backend e frontend em background.

3.  **Acesse a aplicação:**
    Abra seu navegador e acesse `http://localhost:8000`.

4.  **Para parar a aplicação:**
    ```bash
    docker compose down
    ```

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
    "title": "Minha nova tarefa",
    "description": "Detalhes da tarefa"
  }
  ```

### Alternar o status de um "todo"

- **Método:** `POST`
- **Path:** `/api/todos/:id/toggle`
- **Exemplo de Uso:**
  ```bash
  curl -X POST http://localhost:3000/api/todos/1/toggle
  ```
