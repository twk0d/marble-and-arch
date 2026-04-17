# Marble & Arch — API Integration

> **Versão:** 2.1 (Revisão Completa e Exaustiva)  
> **Data:** 2026-06-11  

---

## 1. Fluxo de Requisição (Proxy)

Conforme estabelecido na arquitetura, o Frontend não consome o Spring Boot diretamente por questões de CORS, Segurança de Cookies e Nuxt SSR.

```text
Navegador -> GET /api/v1/properties -> Nuxt Nitro Proxy -> GET http://backend:8081/api/v1/properties
```

---

## 2. Padrões de Resposta REST

Todas as respostas da API seguem um Envelope Padrão para facilitar o tratamento de erros no front-end e padronizar o consumo das requisições assíncronas (`CompletableFuture`).

**Sucesso (200 OK / 201 Created)**
```json
{
  "success": true,
  "data": { ... },
  "message": "Operação realizada com sucesso"
}
```

**Erro (400 / 401 / 403 / 404 / 500)**
```json
{
  "success": false,
  "error": {
    "code": "VALIDATION_FAILED",
    "details": ["Campo 'price' é obrigatório"]
  },
  "message": "Erro na validação dos dados"
}
```

---

## 3. Catálogo Exaustivo de Endpoints

Abaixo estão todos os endpoints obrigatórios para que as funcionalidades do Frontend descritas no PRD operem perfeitamente.

### 3.1 Imóveis (Catálogo e Submissão)
- `GET /api/v1/properties` — Lista paginada e filtrada (Catálogo Público).
- `GET /api/v1/properties/{id}` — Detalhes completos do imóvel (Público).
- `POST /api/v1/properties/submit` — Envio de dados do imóvel pelo proprietário.
- `POST /api/v1/upload/images` — **[NOVO]** Upload multipart/form-data das fotos do imóvel (integração GCP Storage).
- `PUT /api/v1/broker/properties/{id}` — Atualização/Análise do imóvel (Corretor).
- `POST /api/v1/broker/properties/{id}/approve` — Aprovação e Publicação do imóvel (Corretor).
- `POST /api/v1/broker/properties/{id}/reject` — Rejeição do imóvel com motivo (Corretor).

### 3.2 Autenticação e Perfis (Segurança)
- `POST /api/v1/auth/login` — Autenticação (Retorna JWT que o Nuxt salva como cookie HttpOnly).
- `POST /api/v1/auth/register` — Cadastro de usuário cliente/visitante.
- `POST /api/v1/auth/forgot-password` — **[NOVO]** Solicitação de redefinição de senha (envia e-mail).
- `POST /api/v1/auth/reset-password` — **[NOVO]** Confirmação da nova senha usando token.
- `GET /api/v1/users/me` — Busca os dados do perfil do usuário logado.
- `PUT /api/v1/users/me` — Atualiza dados do perfil (nome, telefone, preferências).

### 3.3 CRM (Leads e Dashboards)
- `GET /api/v1/broker/dashboard/metrics` — **[NOVO]** Indicadores rápidos para o topo do Dashboard (count de imóveis, leads pendentes).
- `GET /api/v1/manager/dashboard/metrics` — **[NOVO]** Indicadores globais da equipe para o Gerente.
- `POST /api/v1/leads` — Visitante preenche formulário de contato demonstrando interesse no imóvel.
- `GET /api/v1/broker/leads` — Lista de leads atribuídos ao corretor.
- `PUT /api/v1/broker/leads/{id}/status` — Mudar status no funil de vendas (NEW, CONTACTED, CONVERTED, ARCHIVED).
- `POST /api/v1/broker/leads/{id}/notes` — **[NOVO]** Corretor adiciona uma anotação de histórico no Lead.

### 3.4 Agendamentos de Visitas
- `POST /api/v1/visits` — Cliente agenda uma visita informando datas e horários de preferência.
- `GET /api/v1/visits/me` — **[NOVO]** Cliente visualiza histórico e status de suas visitas marcadas.
- `GET /api/v1/broker/visits` — Corretor visualiza sua agenda completa.
- `PUT /api/v1/broker/visits/{id}/status` — Corretor aceita, propõe nova data, rejeita ou marca visita como concluída.

### 3.5 Favoritos e Notificações In-App
- `POST /api/v1/users/me/favorites` — Adiciona um imóvel à lista de favoritos do cliente logado.
- `DELETE /api/v1/users/me/favorites/{propertyId}` — Remove um imóvel dos favoritos.
- `GET /api/v1/users/me/favorites` — **[NOVO]** Retorna todos os cards de favoritos do cliente.
- `GET /api/v1/notifications` — Retorna alertas (alertas de SLA, novos leads, alteração de status).
- `PUT /api/v1/notifications/{id}/read` — Marca uma notificação específica como lida.

### 3.6 Financeiro, Metas e Gamificação
- `GET /api/v1/finance/commissions` — Extrato pessoal e histórico de pagamentos do corretor.
- `GET /api/v1/manager/commissions` — Visão gerencial de comissionamento de toda a equipe.
- `GET /api/v1/gamification/performance` — Perfil de performance, badges ganhos e metas do corretor.
- `GET /api/v1/manager/team-goals` — Gráficos de atingimento de cota mensal da equipe.

### 3.7 Market Analytics
- `GET /api/v1/analytics/regional` — Dashboard para o Gerente ver tendência de preços por região.
- `GET /api/v1/analytics/sales/export` — Gera e baixa PDF/CSV com relatórios de performance (export).

### 3.8 Central de Suporte e Ajuda
- `GET /api/v1/help/articles` — **[NOVO]** Retorna categorias, FAQ e Artigos para a Central de Ajuda/Guias.
- `GET /api/v1/help/articles/{slug}` — **[NOVO]** Detalhes do artigo/guia de investimento.

### 3.9 Painel Administrativo (Global)
- `GET /api/v1/admin/users` — Lista todos os perfis do sistema.
- `POST /api/v1/admin/users` — **[NOVO]** Admin cria um novo Corretor ou Gerente manualmente.
- `PUT /api/v1/admin/users/{id}/deactivate` — **[NOVO]** Desativação (Soft Delete) de um funcionário.
- `GET /api/v1/admin/groups` — Lista grupos/equipes estruturais.
- `POST /api/v1/admin/groups` — Admin cria um novo grupo atribuindo um Gerente.
- `PUT /api/v1/admin/groups/{id}` — **[NOVO]** Atualiza ou realoca membros de um grupo.
- `GET /api/v1/admin/configs` — **[NOVO]** Retorna parametrizações de negócio globais (SLA times, etc).

---

## 4. Integração Frontend (Fetchers do Nuxt)

Exemplo do fluxo otimizado utilizando os composables do Nuxt para consumir o proxy local (`/api/v1`) com a tipagem de resposta garantida pelo backend Java:

```typescript
// Exemplo de quick fetch na Homepage
const { data: response, error } = await useFetch<ApiResponse<PropertyListDto>>('/api/v1/properties', {
  query: { featured: true, limit: 4 },
  server: true // SSR amigável
});

// Respeitando o envelope padrão:
if (response.value?.success) {
  properties.value = response.value.data.items;
}
```
