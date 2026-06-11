# Marble & Arch — Monorepo

Bem-vindo ao repositório central da **Marble & Arch**, a plataforma premium de gestão e catálogo imobiliário.

Este repositório está configurado como um monorepo, dividindo claramente as responsabilidades de Frontend, Backend e Especificações de Produto.

## Estrutura do Projeto

*   `/frontend` — Aplicação SPA/SSR construída em Nuxt 3 e Vue 3, utilizando a biblioteca Nuxt UI Pro. Serve tanto o catálogo público quanto as áreas autenticadas (CRM, Dashboards).
*   `/backend` — API centralizada (Modular Monolith) construída em Java e Spring Boot 3, projetada com Clean Architecture, CQRS e Orientação a Eventos.
*   `/specification` — O "Cérebro" do projeto. Contém todos os documentos de produto (PRD), fluxos de UX, diagramas de arquitetura, contrato de APIs e o Design System base.

> **Importante:** Todo o contexto profundo da inteligência do projeto está detalhado no arquivo `GEMINI.md` localizado na raiz. Leia-o para compreender os mandatos de desenvolvimento.

## Como iniciar o ambiente local (Docker)

A forma mais rápida de subir o ecossistema completo é através do Docker Compose na raiz do projeto. Ele subirá o banco de dados PostgreSQL, compilará o backend em Java e iniciará o servidor do Nuxt em modo dev.

```bash
# Na raiz do projeto:
docker-compose up --build
```

Acesse os serviços:
*   **Frontend UI:** `http://localhost:3000`
*   **Backend API:** `http://localhost:8081`
*   **Banco de Dados:** `localhost:5433` (Usuário: `user` / Senha: `password`)

Para comandos de desenvolvimento isolados (sem Docker), consulte o arquivo `README.md` dentro da pasta `/frontend` ou `/backend`.
