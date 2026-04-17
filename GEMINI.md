# Marble & Arch — Global Project Context

Bem-vindo ao monorepo do projeto **Marble & Arch**. Este arquivo contém as regras e diretrizes globais para as duas frentes do projeto: Frontend (Nuxt 3) e Backend (Spring Boot 3). Siga estas diretrizes rigorosamente.

---

## 🖥️ FRONTEND (Diretório `/frontend`)

Este diretório contém o front-end da aplicação Marble & Arch.

### Tecnologias & Convenções
- **Framework:** Nuxt 3 (Vue 3)
- **Componentes:** [Nuxt UI Pro](https://ui3.nuxt.com/) (Prioridade Absoluta)
- **Estado:** Pinia
- **Internacionalização:** @nuxtjs/i18n
- **Estilização:** Vanilla CSS / Tailwind (através do Nuxt UI)

### Mandatos de Desenvolvimento (UI)
1.  **Prioridade Absoluta:** Sempre utilize os componentes do Nuxt UI Pro. Não crie componentes customizados ou use tags HTML puras se existir um componente correspondente na biblioteca.
2.  **Styleguide como Fonte de Verdade:** O arquivo `app/pages/styleguide.vue` (visualizável em `/styleguide`) é a referência definitiva para Tipografia, Cores, Bordas, Sombras e padrões de UI.
3.  **Consulta às Docs:** Sempre consulte a documentação local (exportada do Nuxt UI Pro v4.8.2) antes de implementar qualquer interface.
4.  **Refatoração:** Ao editar arquivos existentes, refatore elementos para usar os equivalentes do Nuxt UI Pro.
5.  **Consistência Reativa:** Utilize os utilitários de CSS centralizados (`main.css`) e chaves de tradução (`i18n`).
6.  **Integração:** As chamadas para a API usam o `$fetch` do Nuxt apontando para `/api/...` (Nitro Proxy).

---

## ⚙️ BACKEND (Diretório `/backend`)

Este diretório contém a API Monolítica Modular em Java.
Você é um arquiteto sênior em ecossistemas Java, Spring, DDD e arquiteturas reativas.

### Stack de Tecnologias
- **Core:** Spring Boot 3.4.7, Java 24, Gradle.
- **Produtividade:** Lombok, Spring Boot DevTools, Spring Validation.
- **Web e Segurança:** Spring Web (MVC), Spring Security (JWT).
- **Persistência:** Spring Data JPA, Hibernate ORM 6.6.18, PostgreSQL, ULID para chaves primárias.
- **Nativo:** Compilação nativa com GraalVM (Spring Boot GraalVM Native Support 0.10.6).
- **Infra (GCP):** Google Cloud Support, Cloud Storage, Cloud SQL.
- **Modularidade:** Spring Modulith 1.3.7.

### Padrões Arquiteturais e DDD
1.  **Monólito Modular:** Módulos separados representando Bounded Contexts.
2.  **Clean Architecture:** Separação clara entre `domain`, `application`, `adapter` e `infrastructure`.
3.  **Rich Domain Model:** Lógica de negócio e estado residem nos próprios objetos de domínio.
4.  **Injeção de Dependência:** Favorecer injeção via construtor. Único `ApplicationContext` global (ADR-0025).
5.  **CQRS:** Separação entre leitura (Queries) e escrita (Commands) no mesmo banco físico.
6.  **Orientado a Eventos:** Comunicação entre módulos via Domain Events, usando `CompletableFuture` e `EventBus` em memória (ADR-0024 - bloqueio para resultado).
7.  **Padrão Details:** Usar Composição sobre Herança para variações de agregados (ex: tipos de Property) (ADR-0023).

### Princípios de Codificação
- **Imutabilidade:** Preferência para objetos imutáveis (DTOs, Value Objects).
- **Tratamento de Erros:** Estratégia "fail-fast" com exceções tratadas globalmente (`BussinessRuleValidationException`).
- **Testes:** Unitários para domínio, integração para persistência, e ArchUnit/Spring Modulith para arquitetura.
- **Linguagem Ubíqua:** Nomes de classes e métodos devem seguir os termos de negócio (Property, Lead, Visit, etc).

---
> Consulte o diretório `/specification` na raiz para ler os diagramas, arquitetura completa, endpoints de API e fluxo de telas (UX Flows).
