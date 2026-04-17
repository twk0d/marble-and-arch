# ADR-F005: Tratamento Global de Erros de Negócio e Validações

## Status
Aceito

## Contexto
A API Java foi desenhada (no seu `GEMINI.md`) com uma abordagem "fail-fast", retornando Exceções de Regra de Negócio de forma padronizada. Por exemplo, tentar agendar uma visita num horário já ocupado retornará um HTTP 400 com `code: VALIDATION_FAILED` e detalhes no payload. O frontend precisa reagir a isso sem encher as páginas de `try/catch` redundantes.

## Decisão
1. **Interceptador Global:** Utilizaremos o Plugin nativo do Nuxt de fetch interceptors (`useFetch` com `onResponseError`) para monitorar centralmente todas as falhas de API.
2. **Feedback ao Usuário:** Quando o servidor emitir um erro 400 (Violação de Negócio), o interceptador invocará automaticamente o sistema de *Toasts* (Notificações Flutuantes) do Nuxt UI Pro (`useToast().add({...})`) mostrando a mensagem enviada pelo back-end.
3. **Validação Frontend Independente:** Formulários grandes (como Anunciar Imóvel e Cadastro) devem utilizar esquemas do **Zod** ou **Yup** associados aos componentes do Nuxt UI Pro para prevenir requisições inválidas antes mesmo delas saírem da máquina do cliente.

## Consequências
- **UX Limpa:** O usuário recebe retornos imediatos.
- **DRY (Don't Repeat Yourself):** Não precisamos codificar alertas de erro após cada chamada assíncrona.
- Os schemas de validação no frontend precisarão estar sempre em conformidade com as restrições da camada de domínio do Java (tamanhos de texto, validação de e-mail).
