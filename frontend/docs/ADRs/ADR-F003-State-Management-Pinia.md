# ADR-F003: Gerenciamento de Estado Global com Pinia

## Status
Aceito

## Contexto
A plataforma Marble & Arch compartilha diversos estados vitais através de páginas profundamente aninhadas:
1. **Dados do Usuário e Sessão:** Perfil, Papel (Corretor vs Cliente vs Admin), Nível de Acesso, Avatar.
2. **Favoritos:** Uma lista de IDs de imóveis que devem alterar os ícones de coração em tempo real na Homepage, Catálogo e Detalhes.
3. **Filtros do Catálogo:** Filtros de pesquisa globais que o usuário digita na Homepage e que precisam persistir ao navegar para a página de Resultados.
4. **Carrinho de Submissão:** O formulário de "Anunciar Imóvel" (6 passos) que compartilha dados ao longo da jornada.

## Decisão
Utilizaremos o **Pinia** como biblioteca oficial de Store e estado global.
1. As Stores serão divididas por domínio de negócio: `useAuthStore`, `usePropertyFormStore`, `useFavoritesStore`.
2. Para persistência de informações entre recarregamentos da página, o Pinia deve ser conjugado com cookies ou `localStorage` (como `useCookie` nativo do Nuxt).

## Consequências
- **Reatividade Previsível:** O fluxo de dados será unidirecional, usando o pattern Composition API (Vue 3) nativamente dentro do Pinia.
- O Pinia é suportado nativamente pelo Nuxt 3 e funciona perfeitamente com SSR, evitando bugs de "State Pollution" no servidor.
