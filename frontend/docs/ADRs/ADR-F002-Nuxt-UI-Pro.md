# ADR-F002: Nuxt UI Pro como Única Fonte de Verdade para Componentização

## Status
Aceito

## Contexto
Temos 20 fluxos de tela altamente elaborados exportados pelo Google Stitch, contendo dashboards, tabelas complexas, steppers de cadastro de imóveis, e cards. Historicamente, criar componentes customizados com Tailwind nativo para cada um desses elementos gera débito técnico massivo, inconsistência visual ("colcha de retalhos") e muito tempo de manutenção.

## Decisão
Decidimos que o **Nuxt UI Pro v4.8.2** será a biblioteca base e a única fonte de verdade para os componentes do sistema.
1. É estritamente **proibido** criar componentes nativos (como `<div>` estilizadas para botões ou inputs) se já existir um correspondente como `<UButton>` ou `<UInput>`.
2. Componentes visuais do design gerado por IA deverão ser alcançados adaptando as *props* e as classes utilitárias (Tailwind) dos componentes do Nuxt UI Pro.
3. A página `app/pages/styleguide.vue` atuará como um "catálogo vivo" mockado. Sempre que uma variante for customizada, ela deve ser registrada lá.

## Consequências
- **Aceleração:** Desenvolvimento incrivelmente mais rápido e padronizado.
- **Acessibilidade Inerente:** Componentes do Nuxt UI (baseados no Headless UI) já vem com toda a acessibilidade (WAI-ARIA) tratada nativamente.
- **Limitação de Flexibilidade:** Em raros casos onde o design da IA for extremamente alienígena aos padrões do Nuxt UI Pro, o design deverá ser ligeiramente simplificado para se adequar ao componente oficial em prol da manutenibilidade.
