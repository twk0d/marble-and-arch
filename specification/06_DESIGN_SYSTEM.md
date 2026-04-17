# Marble & Arch — Guia de Estilo e Design System

> **Versão:** 2.0 (Alinhada aos designs gerados por IA)  
> **Data:** 2026-06-11  
> **Fonte da Verdade:** Componentes do Nuxt UI Pro e designs AI-generated.

Este documento consolida as diretrizes para a construção das interfaces do Marble & Arch. O objetivo é reproduzir os designs em alta fidelidade gerados por IA que estão na pasta `frontend/docs/Design/`, utilizando **exclusivamente** a biblioteca do Nuxt UI Pro v4.8.2.

As antigas paletas de cores engessadas foram removidas. Agora, as cores e tokens visuais devem ser extraídos diretamente das telas de design de referência e parametrizadas de forma reativa através do Nuxt UI Pro e Tailwind.

---

## 1. Diretrizes de Componentização (Mandato Absoluto)

1. **Uso Exclusivo do Nuxt UI Pro:** Toda a UI deve ser construída mapeando os elementos visuais dos arquivos de design diretamente para os componentes do Nuxt UI Pro correspondentes. A documentação da versão v4.8.2 deve ser consultada de forma contínua.
2. **Proibição de Tags Nativas Puras (Regra Geral):** Sempre que existir um componente equivalente no Nuxt UI Pro (ex: `<UCard>`, `<UButton>`, `<UInput>`, `<UTable>`, `<UModal>`), ele **DEVE** ser utilizado em vez das tags nativas (como `<div>`, `<button>`, `<input>`).
3. **Componentes Personalizados Restritos:** A criação de componentes customizados do zero só é permitida caso seja comprovadamente impossível atingir o resultado visual do design através da configuração dos componentes do Nuxt UI Pro. Qualquer criação customizada exigirá autorização prévia.
4. **Styleguide Vivo e Atualizado:** A página `app/pages/styleguide.vue` funciona como um catálogo/registro vivo de todo o sistema. Ela serve exclusivamente para mostrar os componentes e como eles funcionam isoladamente, portanto, deve **sempre utilizar dados mockados** (fictícios) e não depender de requisições de API. Sempre que um novo componente for introduzido, customizado ou alterado na plataforma, ele deve ser obrigatoriamente refletido e exibido de forma congruente no styleguide para manter a consistência visual geral do projeto em um só lugar.
5. **Padrões Globais:** Os padrões globais de espaçamento, bordas e sombras devem estar parametrizados e refletidos no Styleguide oficial do projeto (`app/pages/styleguide.vue`) e nas classes utilitárias configuradas (`main.css`), sendo consumidos pelos componentes do Nuxt.

---

## 2. Tipografia Oficial

Embora o design visual e as cores venham das telas geradas pela IA, a base tipográfica do projeto permanece estrita para garantir a identidade premium da marca. Duas famílias de fontes do Google Fonts (`@nuxt/fonts`) devem ser utilizadas:

1. **Sora:** Exclusiva para Headings (Títulos).
2. **Inter:** Exclusiva para Body (Textos de interface, descrições, modais e formulários).

### 2.1 — Escala Tipográfica (Classes Tailwind)

As seguintes classes de utilitários tipográficos devem ser empregadas para manter o rigor no frontend:

| Classe Tailwind | Fonte | Tamanho | Peso | Uso Indicado |
|---|---|---|---|---|
| `.text-h1` | Sora | 3rem (48px) | 800 | Hero title, títulos master de páginas. |
| `.text-h2` | Sora | 2.25rem (36px) | 700 | Títulos de seção grandes. |
| `.text-h3` | Sora | 1.875rem (30px) | 600 | Preços em destaque, títulos de modais grandes. |
| `.text-h4` | Sora | 1.5rem (24px) | 500 | Títulos de Cards e Widgets. |
| `.text-h5` | Sora | 1.25rem (20px) | 500 | Títulos secundários ou blocos menores. |
| `.text-body-lg` | Inter | 1.125rem (18px) | 400 | Textos de destaque em parágrafos de conteúdo. |
| `.text-body-base` | Inter | 1rem (16px) | 400 | Texto padrão do sistema (padrão base). |
| `.text-body-sm` | Inter | 0.875rem (14px) | 400 | Metadados, labels e descrições menores. |
| `.text-body-xs` | Inter | 0.75rem (12px) | 400 | Badges, tags pequenas e alertas de sistema. |
| `.text-body-bold` | Inter | 1rem | 600 | Palavras em destaque no texto comum. |
| `.text-body-muted`| Inter | 1rem | 400 | Texto de baixa ênfase (ex: descrições em input). |

*(As cores dos textos serão configuradas usando os tokens dinâmicos que serão extraídos das telas finais, garantindo o funcionamento perfeito do Dark Mode / Light Mode do Nuxt UI Pro).*
