# Marble & Arch — UX Flows e Sitemap

> **Versão:** 2.0 (Revisão Stitch/Figma)  
> **Data:** 2026-06-11  

---

## 1. Sitemap Consolidado

A plataforma agora conta com navegação pública, área autenticada do cliente (CRM), e toda a operação imobiliária (Dashboards).

```mermaid
mindmap
  root((Marble & Arch))
    Público
      Homepage
      Catálogo de Imóveis
      Detalhes do Imóvel
      Submissão Proprietário (Step 1 ao 6)
      Central de Ajuda
      Guia de Investimento
    Autenticação
      Login
      Cadastro
      Recuperar Senha
    Cliente (Logado)
      Perfil do Usuário
      Meus Favoritos
      Central de Notificações
      Agendamento de Visita
    Corretor
      Dashboard
      Gestão de Leads
      Análise de Imóveis
      Extrato de Comissões
      Performance e Gamificação
    Gerente
      Dashboard Global
      Gestão de Comissões de Equipe
      Metas de Equipe
      Regional Market Analytics
      Relatórios Exportáveis
    Admin
      Gestão de Usuários
      Gestão de Grupos
      Configurações Globais
```

---

## 2. Fluxo Principal: Navegação e Agendamento do Cliente

O fluxo mais importante do cliente agora termina na conversão ativa: o **Agendamento de Visitas**.

```mermaid
graph TD
    A[Homepage] -->|Busca/Filtro| B(Catálogo de Imóveis)
    B -->|Ver Imóvel| C{Detalhes do Imóvel}
    C -->|Favoritar| D[Meus Favoritos]
    C -->|Contato Rápido| E[Lead Registrado]
    C -->|Quero Visitar| F[Agendar Visita: Dados]
    F --> G[Agendar Visita: Data e Hora]
    G --> H[Visita Agendada com Sucesso]
    H --> I[Central de Notificações avisa o Corretor]
```

---

## 3. Fluxo Financeiro e Gamificação (Corretor / Gerente)

A nova jornada do corretor expandiu de apenas analisar imóveis para também gerenciar sua própria vida financeira e metas.

```mermaid
graph TD
    A[Corretor Logado] -->|Clica no Menu Financeiro| B(Extrato de Comissões)
    B -->|Solicitar Saque| C[Financeiro Interno]
    A -->|Clica em Performance| D(Painel de Recompensas)
    D --> E[Desbloqueio de Badges/Níveis]
    
    F[Gerente Logado] -->|Clica em Metas| G(Metas de Equipe)
    F -->|Clica em Analytics| H(Regional Market Analytics)
```

---

## 4. Requisitos de Interface (Baseado nas Telas do Stitch)

### Header e Navegação
- **Estado Público:** Logo na esquerda. Links centrais: "Comprar", "Alugar", "Anunciar Imóvel", "Ajuda". Direita: Botão secundário "Entrar" e primário "Criar Conta".
- **Estado Autenticado (Cliente):** Adiciona ícone de "Sino" (Notificações) e "Coração" (Favoritos). Avatar do usuário abrindo dropdown (Perfil, Sair).
- **Estado Autenticado (Corretor/Gerente):** Sidebar de navegação profunda contendo módulos de Leads, Imóveis, Financeiro, Analytics, Configurações.

### Modais e Drawers
- Utilizar os componentes do `Nuxt UI Pro` como `<USlideover>` para filtros no catálogo em dispositivos móveis.
- O Quick View de imóveis no catálogo deve utilizar `<UModal>`.
- Todos os relatórios exportáveis e extratos de comissões devem ser exibidos dentro de tabelas responsivas `<UTable>`.
