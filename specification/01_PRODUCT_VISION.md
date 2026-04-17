# Marble & Arch — Product Vision

> **Versão:** 1.0  
> **Data:** 2026-06-10  
> **Status:** Draft  

---

## 1. Elevator Pitch

**Marble & Arch** é uma plataforma brasileira de compra e aluguel de imóveis que elimina a complexidade do processo imobiliário. O proprietário envia seu imóvel em um formulário simples, e a equipe Marble & Arch cuida de todo o restante — análise, publicação, contato com interessados e gestão do ciclo de negociação.

> *"Você envia. Nós cuidamos de tudo."*

---

## 2. Problema

O mercado imobiliário brasileiro sofre de três dores principais:

| Dor | Descrição |
|---|---|
| **Complexidade para o proprietário** | Cadastrar um imóvel em plataformas tradicionais exige criar conta, preencher dezenas de campos técnicos, tirar fotos profissionais, definir preço competitivo e gerenciar contatos. A maioria dos proprietários não tem tempo ou conhecimento para isso. |
| **Experiência fragmentada para o comprador** | O comprador precisa filtrar centenas de anúncios desatualizados, entrar em contato com diversos corretores diferentes, e não tem garantia de que o imóvel anunciado está realmente disponível. |
| **Falta de gestão para a imobiliária** | Corretores usam planilhas, WhatsApp e e-mail para gerenciar leads e imóveis. Não há visibilidade gerencial, métricas de performance, nem workflow estruturado. |

---

## 3. Público-Alvo (Personas)

A plataforma atende **6 tipos de usuários** com papéis e necessidades distintas:

### 3.1 — Visitante (Visitor)
- **Quem é:** Qualquer pessoa que acessa o site sem cadastro.
- **O que faz:** Navega pelo catálogo, filtra imóveis, visualiza detalhes.
- **Motivação:** Explorar o mercado, comparar opções.
- **Nível de acesso:** Somente leitura (páginas públicas).

### 3.2 — Proprietário (Property Owner)
- **Quem é:** Pessoa física ou jurídica que deseja vender ou alugar seu imóvel.
- **O que faz:** Envia um formulário com os dados do imóvel para análise. **Não precisa criar conta.**
- **Motivação:** Vender ou alugar seu imóvel sem complicação — "envio e esqueço".
- **Nível de acesso:** Formulário público de submissão.

### 3.3 — Interessado (Prospect / Buyer / Renter)
- **Quem é:** Pessoa que encontrou um imóvel no catálogo e quer saber mais.
- **O que faz:** Envia um formulário de contato (lead) demonstrando interesse em um imóvel.
- **Motivação:** Comprar ou alugar um imóvel com atendimento humano e personalizado.
- **Nível de acesso:** Formulário de contato público.

### 3.4 — Corretor (Broker / Agent)
- **Quem é:** Profissional responsável por analisar imóveis submetidos e atender interessados.
- **O que faz:**
  - Recebe imóveis automaticamente atribuídos para análise.
  - Valida dados, entra em contato com o proprietário.
  - Aprova ou rejeita imóveis para publicação.
  - Gerencia leads (interessados) atribuídos a ele.
- **Motivação:** Ter uma ferramenta organizada para gerenciar seu pipeline de trabalho.
- **Nível de acesso:** Dashboard de corretor (área autenticada).

### 3.5 — Gerente (Manager)
- **Quem é:** Profissional que supervisiona um grupo de corretores.
- **O que faz:**
  - Monitora métricas de performance do seu grupo.
  - Redistribui demandas entre corretores.
  - Visualiza dashboards gerenciais.
- **Motivação:** Garantir produtividade e qualidade do atendimento.
- **Nível de acesso:** Dashboard gerencial (área autenticada, escopo do grupo).

### 3.6 — Administrador (Admin)
- **Quem é:** Responsável técnico/operacional pela plataforma.
- **O que faz:**
  - Gerencia gerentes e grupos de corretores.
  - Configurações globais da plataforma.
  - Visão 360° de todos os dados.
- **Motivação:** Manter a plataforma operacional e eficiente.
- **Nível de acesso:** Acesso total (área autenticada).

---

## 4. Proposta de Valor

### Para o Proprietário
> **"Preencha um formulário. Nós fazemos o resto."**
- Sem necessidade de criar conta.
- Sem necessidade de entender de fotografia, precificação ou marketing.
- Um corretor dedicado entra em contato para validar e publicar.

### Para o Comprador/Locatário
> **"Encontre. Filtre. Entre em contato. Simples."**
- Catálogo curado — todos os imóveis são validados por um corretor antes de aparecer.
- Filtros avançados e intuitivos.
- Atendimento humano personalizado (não um robô).

### Para Corretores e Gerentes
> **"Seu pipeline, organizado. Suas métricas, visíveis."**
- Workflow estruturado: receber → analisar → aprovar → publicar.
- Dashboard com métricas reais.
- Atribuição automática de demandas.

---

## 5. Diferencial Competitivo

| Marble & Arch | Concorrentes (ZAP, VivaReal, OLX) |
|---|---|
| Imóveis curados — todos passam por validação humana | Qualquer pessoa publica qualquer coisa |
| Proprietário não precisa de conta | Obrigatório criar conta para anunciar |
| Gestão completa do ciclo pelo corretor | Proprietário gerencia sozinho |
| UX premium e sem fricção | Interfaces poluídas e complexas |
| Atendimento personalizado | Contato direto (sem mediação) |

---

## 6. Princípios de Design

1. **Premium & Sofisticado** — Visual elegante, tipografia cuidadosa, paleta sofisticada. A plataforma deve transmitir confiança e profissionalismo.
2. **Simplicidade Radical** — Menos cliques, menos campos, menos decisões. Cada interação deve ser intuitiva.
3. **Mobile-First** — A maioria dos brasileiros acessa a internet pelo celular. Todo design começa pelo mobile.
4. **Conteúdo Curado** — Nenhum imóvel aparece no catálogo sem validação. Qualidade sobre quantidade.
5. **Dados Visíveis** — Corretores e gerentes devem ter visibilidade instantânea do seu trabalho e resultados.

---

## 7. Modelo de Negócio

> [!NOTE]
> Como projeto de portfólio, o modelo de negócio é simulado, mas deve ser realista para demonstrar capacidade técnica.

- **Comissão sobre transação** — Percentual sobre venda ou primeiro aluguel.
- **Planos para Proprietários Premium** — Destaque no catálogo, fotos profissionais, tour virtual.
- **Assinatura para Imobiliárias** — Acesso a ferramentas de gestão com múltiplos corretores.

---

## 8. Visão de Longo Prazo (1-2 anos)

| Fase | Descrição |
|---|---|
| **MVP (atual)** | Submissão de imóveis, catálogo curado, dashboard de corretores e gerentes, sistema de leads |
| **v2 — Inteligência** | Busca por mapa com raio/região, sugestões de preço baseadas em dados de mercado, matching inteligente proprietário-comprador |
| **v3 — Ecossistema** | Tour virtual 360°, integração com cartórios digitais, assinatura eletrônica de contratos, calculadora de financiamento |

---

## 9. Contexto Técnico (Resumo)

- **Tipo de projeto:** Portfólio profissional full-stack (frontend + backend)
- **Repositório:** Monorepo (frontend Nuxt 3 + backend Spring Boot)
- **Deploy:** Docker (containerizado)
- **Design:** Será criado com Google Stitch AI, baseado nesta documentação

---

## 10. Glossário

| Termo | Definição |
|---|---|
| **Imóvel** | Propriedade cadastrada na plataforma (casa, apartamento, terreno, etc.) |
| **Submissão** | Ato do proprietário enviar os dados de um imóvel para análise |
| **Análise** | Processo onde o corretor valida os dados do imóvel submetido |
| **Aprovação** | Momento em que o corretor confirma que o imóvel está apto para publicação |
| **Lead** | Pessoa interessada que demonstrou intenção de compra/aluguel |
| **Pipeline** | Fluxo sequencial de trabalho do corretor |
| **Catálogo** | Listagem pública de imóveis aprovados e disponíveis |
