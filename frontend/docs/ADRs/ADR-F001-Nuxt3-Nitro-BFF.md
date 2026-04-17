# ADR-F001: Adoção do Nuxt 3 como Framework Core e Nitro como BFF

## Status
Aceito

## Contexto
O projeto **Marble & Arch** engloba desde um portal público com forte necessidade de SEO (Catálogo de Imóveis, Homepage, Artigos) até áreas internas altamente dinâmicas (Dashboards do Corretor, CRM, Extratos Financeiros). 
Precisamos de um framework web que entregue performance, facilidade de roteamento, excelente SEO e que ofereça uma camada segura para conversar com a nossa API Java sem expor tokens ou sofrer com bloqueios de CORS no navegador.

## Decisão
Adotamos o **Nuxt 3** (baseado em Vue 3) como o framework principal do frontend.

1. **Renderização Híbrida:** Utilizaremos Server-Side Rendering (SSR) nativo para as páginas públicas (melhor indexação e performance inicial) e Client-Side Rendering (CSR) para as páginas autenticadas pesadas (dashboards).
2. **Nitro Engine como BFF (Backend-for-Frontend):** O Nuxt traz o Nitro, um servidor embutido. Decidimos que o client-side (navegador) **nunca** fará chamadas diretas para `http://backend:8081`. O navegador chamará `http://localhost:3000/api/...`, e o Nitro interceptará essa rota fazendo o proxy no lado do servidor para a API Java. 

## Consequências
- **Segurança:** Tokens JWT gerados pelo backend podem ser armazenados em cookies `HttpOnly` lidos apenas pelo servidor do Nuxt, mitiga ataques XSS.
- **Evita CORS:** Todas as requisições browser-servidor acontecem na mesma origem.
- **Curva de Aprendizado:** A equipe precisa dominar a engine de roteamento do Nuxt e seus composables globais (`useFetch`, `useAsyncData`) no lugar das tradicionais chamadas `axios`.
