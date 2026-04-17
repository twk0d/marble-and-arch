# ADR-F006: Componentes de Domínio e Busca Reativa (Debounce)

## Status
Aceito

## Contexto
O portal possui um Catálogo de Imóveis denso. Os usuários não devem ter atrito na hora de pesquisar (clicar no botão "Buscar" repetidamente), e os componentes visuais que exibem preços e atributos do imóvel não podem estar duplicados no código.

## Decisão
Resgatamos e consolidamos as seguintes diretrizes de UX/Arquitetura de antigas revisões:

1. **Busca Reativa (Sem Botão de Submit):** Os filtros do catálogo serão gerenciados pelo Pinia e ligados (via `watch` com `debounce` de 500ms) diretamente à chamada da API. Quando o usuário altera um filtro (ex: preço máximo), o sistema aguarda meio segundo e atualiza a grid automaticamente.
2. **Abstração de Domínio:** Componentes que lidam com a renderização formatada de atributos sensíveis do imóvel devem ser abstraídos. Exemplo: um `<PriceDisplay>` isolado para cuidar das formatações monetárias ou um `<PropertyStats>` que renderiza Quartos/Banheiros/Vagas consistentemente na Homepage e na tela de Detalhes.

## Consequências
- A experiência de busca fica instantânea ("tempo real").
- O código do Catálogo de Busca se torna mais enxuto, delegando a formatação visual aos componentes utilitários de domínio.
