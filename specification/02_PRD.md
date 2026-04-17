# Marble & Arch — PRD (Product Requirements Document)

> **Versão:** 2.0 (Revisão Stitch/Figma)  
> **Data:** 2026-06-11  
> **Status:** Draft  
> **Referência:** [Product Vision](./01_PRODUCT_VISION.md)

---

## Índice

1. [Visão Geral](#1-visão-geral)
2. [Personas e Permissões](#2-personas-e-permissões)
3. [Módulos Funcionais — Core](#3-módulos-funcionais--core)
   - [M01 — Homepage](#m01--homepage)
   - [M02 — Catálogo e Busca](#m02--catálogo-e-busca)
   - [M03 — Detalhes do Imóvel](#m03--detalhes-do-imóvel)
   - [M04 — Submissão de Imóvel](#m04--submissão-de-imóvel)
   - [M05 — Autenticação e Perfis](#m05--autenticação-e-perfis)
4. [Módulos Funcionais — CRM & Engajamento](#4-módulos-funcionais--crm--engajamento)
   - [M06 — Agendamento de Visitas](#m06--agendamento-de-visitas)
   - [M07 — Favoritos e Notificações](#m07--favoritos-e-notificações)
   - [M08 — Central de Ajuda e Conteúdo](#m08--central-de-ajuda-e-conteúdo)
5. [Módulos Funcionais — Operação Imobiliária](#5-módulos-funcionais--operação-imobiliária)
   - [M09 — Gestão de Leads e Dashboards](#m09--gestão-de-leads-e-dashboards)
   - [M10 — Extratos e Comissões](#m10--extratos-e-comissões)
   - [M11 — Gamificação e Metas](#m11--gamificação-e-metas)
   - [M12 — Regional Market Analytics](#m12--regional-market-analytics)
6. [Módulos Funcionais — Administração](#6-módulos-funcionais--administração)
   - [M13 — Painel Global (Admin)](#m13--painel-global-admin)
   - [M14 — Sistema de E-mails](#m14--sistema-de-e-mails)

---

## 1. Visão Geral

Este documento detalha **todos os requisitos funcionais** da plataforma Marble & Arch, incluindo os módulos expandidos gerados pelo Google Stitch. 

**Convenções:**
- 🟢 **Core** — Essencial para funcionamento
- 🟡 **Advanced** — Funcionalidade Enterprise
- `[Persona]` — Quem executa a ação

---

## 2. Personas e Permissões

| Funcionalidade | Visitante | Cliente Cadastrado | Corretor | Gerente | Admin |
|---|:---:|:---:|:---:|:---:|:---:|
| Buscar imóveis | ✅ | ✅ | ✅ | ✅ | ✅ |
| Favoritar imóveis | ❌ | ✅ | ✅ | ✅ | ✅ |
| Agendar visita | ❌ | ✅ | ❌ | ❌ | ❌ |
| Submeter imóvel | ✅ | ✅ | ❌ | ❌ | ❌ |
| Gerenciar Leads e Imóveis | ❌ | ❌ | ✅ | ✅ | ✅ |
| Ver Extrato de Comissões | ❌ | ❌ | ✅ | ✅ | ✅ |
| Ver Gamificação/Recompensas | ❌ | ❌ | ✅ | ❌ | ❌ |
| Ver Analytics de Mercado | ❌ | ❌ | ❌ | ✅ | ✅ |
| Configurações Globais | ❌ | ❌ | ❌ | ❌ | ✅ |

---

## 3. Módulos Funcionais — Core

### M01 — Homepage 🟢
- **Objetivo:** Primeira impressão premium com busca rápida, categorias, imóveis em destaque e propostas de valor.
- **Features:** Hero section com CTA, carrossel de destacados, navegação simples.

### M02 — Catálogo e Busca 🟢
- **Objetivo:** Filtragem profunda de imóveis.
- **Features:** Filtros reativos (tipo, preço, quartos, localização). Quick view de imóveis, toggle dark/light mode, paginação dinâmica.

### M03 — Detalhes do Imóvel 🟢
- **Objetivo:** Informações completas para conversão.
- **Features:** Galeria de fotos lightbox, lista de amenidades, descrição textual rica, integração direta com os botões "Favoritar" e "Agendar Visita".

### M04 — Submissão de Imóvel 🟢
- **Objetivo:** Captação de novos imóveis pelo proprietário sem fricção.
- **Features:** Stepper de 6 passos (Dados, Endereço com auto-completar via CEP, Características, Upload de Múltiplas Fotos, Contato, Revisão).

### M05 — Autenticação e Perfis 🟢
- **Objetivo:** Gestão de usuários.
- **Features:** Login, Cadastro, Recuperar Senha, Perfil de Usuário para edição de dados pessoais e preferências. Proteção de rotas via middleware.

---

## 4. Módulos Funcionais — CRM & Engajamento

### M06 — Agendamento de Visitas 🟡
- **Objetivo:** Transformar um Lead em uma visita presencial marcada e automatizada.
- **Features:**
  - Formulário "Dados e Preferências" do cliente.
  - Seleção em calendário de "Data e Hora" baseado na disponibilidade.
  - Tela de "Visita Agendada com Sucesso".
  - Corretor recebe o agendamento em seu dashboard.

### M07 — Favoritos e Notificações 🟡
- **Objetivo:** Reter o cliente logado.
- **Features:**
  - Página "Meus Favoritos" com cards salvos.
  - "Central de Notificações" in-app (alertas de alteração de preço, novas mensagens, mudanças de status da visita).

### M08 — Central de Ajuda e Conteúdo 🟡
- **Objetivo:** Auto-serviço e Autoridade de Mercado.
- **Features:**
  - Central de Suporte e Ajuda (FAQ e contatos rápidos).
  - Guia de Investimento (Artigos editoriais sobre o mercado imobiliário).

---

## 5. Módulos Funcionais — Operação Imobiliária

### M09 — Gestão de Leads e Dashboards 🟢
- **Objetivo:** Organizar o funil de vendas.
- **Features:**
  - Dashboard do Corretor (Métricas rápidas e imóveis em análise).
  - Dashboard do Gerente (Controle de equipes).
  - Pipeline visual de Leads (Novo > Contatado > Convertido).

### M10 — Extratos e Comissões 🟡
- **Objetivo:** Transparência financeira total.
- **Features:**
  - **Extrato do Corretor:** Visão detalhada de valores a receber, comissões pagas, histórico de transações.
  - **Gestão de Equipe (Gerente):** Visão de pagamentos da equipe inteira.

### M11 — Gamificação e Metas 🟡
- **Objetivo:** Incentivar a performance.
- **Features:**
  - **Painel de Performance:** Gráficos individuais de vendas x aluguéis.
  - **Recompensas:** Sistema de badges/níveis alcançados baseado no volume de conversões.
  - **Metas de Equipe:** Dashboard para o gerente acompanhar o atingimento de cotas.

### M12 — Regional Market Analytics 🟡
- **Objetivo:** Inteligência de Dados.
- **Features:**
  - Dashboard avançado com mapas e gráficos sobre preço médio por m², tendências de bairros e relatórios exportáveis. Disponível para gerentes e admins.

---

## 6. Módulos Funcionais — Administração

### M13 — Painel Global (Admin) 🟢
- **Objetivo:** Controle completo.
- **Features:**
  - Gestão de Usuários (CRUD completo de todos os perfis).
  - Gestão de Grupos (Criação de hierarquias corretor/gerente).
  - Configurações Globais (Parametrizações de sistema, chaves de API, etc).

### M14 — Sistema de E-mails 🟢
- **Objetivo:** Avisos transacionais fora da plataforma.
- **Features:** E-mails automáticos para confirmação de agendamento, imóveis aprovados e avisos críticos.

---
*Este PRD anula versões anteriores e serve de base para os novos diagramas de fluxo e arquitetura do Marble & Arch.*
