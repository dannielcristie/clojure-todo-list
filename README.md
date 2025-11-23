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
    cd <nome do repositório>
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
