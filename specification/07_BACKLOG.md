# Marble & Arch — Backlog de Desenvolvimento e Fases

> **Versão:** 2.0 (Revisão Stitch/Figma)  
> **Data:** 2026-06-11  

---

Devido ao escopo massivo revelado pelas telas exportadas pela IA, dividimos o projeto em **5 Fases Lógicas** para permitir entregas progressivas.

## Fase 1 — Fundação & UX Core (Semanas 1-2)

**Objetivo:** Setup do monorepo, infraestrutura base e toda a interface pública de visualização.
- [ ] Criar monorepo (pasta `/frontend` e `/backend`).
- [ ] Configurar Spring Boot (PostgreSQL + JWT + Flyway).
- [ ] Configurar Nuxt 3 (Tailwind, Nuxt UI Pro, Pinia, i18n).
- [ ] **Backend:** Criar CRUD de Usuários e Imóveis (Properties).
- [ ] **Frontend:** Implementar `Homepage`, `Catálogo de Imóveis` (com filtros) e `Detalhes do Imóvel`.
- [ ] **Frontend:** Implementar fluxo de `Submissão de Imóvel` (Stepper).

---

## Fase 2 — CRM de Clientes (Semanas 3-4)

**Objetivo:** Permitir que clientes se cadastrem, interajam e agendem visitas.
- [ ] **Backend:** Criar entidades `Lead`, `Visit`, `Favorite` e Endpoints de Agendamento.
- [ ] **Frontend:** Páginas de `Login` e `Cadastro`.
- [ ] **Frontend:** Implementar fluxo de captura de contato na página do imóvel.
- [ ] **Frontend:** Implementar funcionalidade "Meus Favoritos".
- [ ] **Frontend:** Implementar formulários de `Agendar Visita`.
- [ ] **Frontend:** Criar `Perfil do Usuário`.

---

## Fase 3 — Dashboards de Operação (Semanas 5-6)

**Objetivo:** O motor de trabalho interno para corretores e gerentes.
- [ ] **Backend:** API de relatórios e atribuição automática de leads/imóveis.
- [ ] **Frontend:** Implementar `Dashboard do Corretor` (visão geral, métricas).
- [ ] **Frontend:** Tela de `Análise de Imóvel` (aprovar/rejeitar submissões).
- [ ] **Frontend:** Tela de `Gestão de Leads` e `Agenda de Visitas` do Corretor.
- [ ] **Frontend:** Implementar `Dashboard do Gerente` (visão da equipe).
- [ ] **Frontend:** Tela do Painel `Administrador Global` (CRUD de Grupos e Usuários).

---

## Fase 4 — Financeiro & Metas (Semanas 7-8)

**Objetivo:** Transparência financeira e engajamento da equipe de vendas.
- [ ] **Backend:** Entidade `Commission` e rotinas de cálculo de comissionamento.
- [ ] **Frontend:** Desenvolver tela de `Extrato de Comissões` para corretores.
- [ ] **Frontend:** Desenvolver `Gestão de Comissões de Equipe` para o gerente.
- [ ] **Backend:** Engine de pontuação para Gamificação (`BrokerPerformance`).
- [ ] **Frontend:** Painéis de `Recompensas e Gamificação` (Badges).
- [ ] **Frontend:** Painel de `Metas de Equipe`.

---

## Fase 5 — Inteligência de Mercado & Suporte (Semanas 9-10)

**Objetivo:** Entregas de nível Enterprise e encerramento.
- [ ] **Backend:** Queries complexas para Analytics e agregação de dados.
- [ ] **Frontend:** Implementar dashboards de `Regional Market Analytics`.
- [ ] **Frontend:** Exportação de relatórios em CSV/PDF.
- [ ] **Frontend:** Criar a `Central de Notificações` In-App.
- [ ] **Frontend:** Estruturar a `Central de Ajuda` e `Guias de Investimento` (CMS Simples).
- [ ] **Revisão:** Testes de carga e deploy final em produção.
