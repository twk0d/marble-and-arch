# Marble & Arch — Frontend (Nuxt 3)

Aplicação web premium desenvolvida para o ecossistema imobiliário da Marble & Arch.

## Stack Tecnológico

*   **Framework:** Nuxt 3 (Vue 3)
*   **UI Components:** Nuxt UI Pro v4.8.2
*   **Estilização:** Tailwind CSS v4
*   **Gerenciamento de Estado:** Pinia
*   **Internacionalização:** @nuxtjs/i18n

## Padrões Arquiteturais

Conforme documentado em `docs/ADRs`, o frontend segue regras estritas:
1. **Nuxt UI Pro Exclusivo:** Todo o design é construído obrigatoriamente utilizando os componentes nativos (`<UCard>`, `<UButton>`, etc) para evitar colcha de retalhos.
2. **Nitro BFF:** O frontend não acessa a porta do Java diretamente. Ele bate na própria API (`/api/...`) e a engine do Nitro intercepta e repassa para o backend, garantindo segurança de cookies.

## Instalação e Execução (Local)

Certifique-se de estar utilizando o Node.js v20+.

```bash
# Instalar dependências
npm install

# Rodar em ambiente de desenvolvimento (com hot-reload)
npm run dev
```

A aplicação estará disponível em `http://localhost:3000`.
