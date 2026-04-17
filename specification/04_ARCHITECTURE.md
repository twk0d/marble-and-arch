# Marble & Arch — Arquitetura de Software

> **Versão:** 2.1 (Revisão Completa de Entidades)  
> **Data:** 2026-06-11  

---

## 1. Topologia do Sistema (Monorepo)

O projeto é estruturado em um monorepo para facilitar o desenvolvimento, deploy e garantir o uso correto do Docker local. O repositório contém dois diretórios principais:

```
marble-and-arch/
├── frontend/             # Aplicação Nuxt 3 (UI + Nitro Proxy)
│   ├── app/              # Componentes, Páginas, Composables
│   └── nuxt.config.ts    # Configurações do Nitro
│
├── backend/              # Aplicação Spring Boot (API Java 24)
│   ├── src/main/java/    # Código fonte (Domain Driven Design)
│   └── pom.xml           # Dependências
│
└── docker-compose.yml    # Orquestrador local
```

## 2. Padrão de Comunicação (BFF Proxy)

O frontend Nuxt não faz chamadas diretas para o backend a partir do client-side. Ele utiliza a engine **Nitro** como um Backend-for-Frontend (BFF).

1. O navegador faz requisições para `/api/...` no Nuxt.
2. O Nuxt Nitro intercepta e faz o proxy para `http://backend:8081/api/...`.
3. Evitamos problemas de CORS e mantemos tokens de acesso seguros no cookie `HttpOnly`.

---

## 3. Arquitetura de Dados (Diagrama de Entidades Estendido)

Para suportar exaustivamente todos os endpoints definidos na API e no PRD (como artigos de ajuda, imagens de imóveis, anotações de leads e hierarquia de times), o modelo de dados completo é:

```mermaid
erDiagram
    %% Core System & Hierarchy
    GROUP {
        UUID id PK
        String name "Ex: Filial Sul, Equipe A"
        UUID manager_id FK "Leader of the group"
    }

    USER {
        UUID id PK
        String name
        String email
        String password_hash
        String role "VISITOR, OWNER, BROKER, MANAGER, ADMIN"
        UUID group_id FK "Nullable"
    }

    %% Property Domain
    PROPERTY {
        UUID id PK
        String title
        String description
        String type "HOUSE, APARTMENT, etc"
        String purpose "SALE, RENT"
        Decimal price
        String status "PENDING, APPROVED, REJECTED"
        UUID broker_id FK "Assigned to"
    }

    PROPERTY_IMAGE {
        UUID id PK
        UUID property_id FK
        String url "GCP Storage Link"
        Boolean is_primary
        Integer display_order
    }

    ADDRESS {
        UUID id PK
        UUID property_id FK
        String zipcode
        String state
        String city
        String neighborhood
        String street
        String number
    }

    %% CRM & Operações
    LEAD {
        UUID id PK
        UUID property_id FK
        String name
        String email
        String phone
        String status "NEW, CONTACTED, CONVERTED"
        UUID broker_id FK
    }

    LEAD_NOTE {
        UUID id PK
        UUID lead_id FK
        UUID broker_id FK
        String content
        DateTime created_at
    }

    VISIT {
        UUID id PK
        UUID property_id FK
        UUID client_id FK
        UUID broker_id FK
        DateTime scheduled_date
        String status "SCHEDULED, COMPLETED, CANCELLED"
    }

    FAVORITE {
        UUID id PK
        UUID property_id FK
        UUID user_id FK
    }

    %% Financeiro & Engajamento
    COMMISSION {
        UUID id PK
        UUID broker_id FK
        UUID property_id FK
        Decimal amount
        String status "PENDING, PAID"
        DateTime created_at
    }

    PERFORMANCE_GOAL {
        UUID id PK
        UUID user_id FK "Corretor ou Gerente"
        Integer points_earned
        String current_badge
    }

    %% Help Center
    ARTICLE {
        UUID id PK
        String title
        String slug
        String content "Markdown ou HTML"
        String category "FAQ, GUIDES"
        Boolean is_published
    }

    %% Notificações
    NOTIFICATION {
        UUID id PK
        UUID user_id FK
        String title
        String message
        Boolean is_read
        DateTime created_at
    }

    GLOBAL_CONFIG {
        String key PK
        String value
        String description
    }

    %% Relacionamentos
    GROUP ||--o{ USER : "contains"
    USER ||--o{ PROPERTY : "manages"
    PROPERTY ||--o{ PROPERTY_IMAGE : "has"
    PROPERTY ||--o| ADDRESS : "has"
    PROPERTY ||--o{ LEAD : "receives"
    USER ||--o{ LEAD : "works on"
    LEAD ||--o{ LEAD_NOTE : "has"
    USER ||--o{ VISIT : "participates"
    PROPERTY ||--o{ VISIT : "has"
    USER ||--o{ FAVORITE : "saves"
    USER ||--o{ COMMISSION : "earns"
    USER ||--o{ NOTIFICATION : "receives"
```

---

## 4. Stack Tecnológico Base

### Frontend (Diretório `frontend/`)
- **Framework:** Nuxt 3 (Vue.js 3)
- **UI Component Library:** Nuxt UI Pro v4.8.2
- **Estilização:** Tailwind CSS (Variáveis de CSS no `main.css`)
- **Estado:** Pinia

### Backend (Diretório `backend/`)
- **Linguagem:** Java 24
- **Framework:** Spring Boot 3.4.7
- **Banco de Dados:** PostgreSQL 16
- **Autenticação:** Spring Security + JWT
- **Migrações:** Flyway ou Liquibase

### Infraestrutura
- Docker & Docker Compose para desenvolvimento local.
- GCP Cloud Storage para Imagens e Arquivos.
