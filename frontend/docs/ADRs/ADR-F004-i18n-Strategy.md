# ADR-F004: Estratégia de Internacionalização e Formatação (i18n)

## Status
Aceito

## Contexto
O mercado imobiliário lida massivamente com dados formatados sensíveis: Moedas (R$, Valores muito grandes), Datas de Agendamento, Status de Leads e Comissões. Além disso, embora o portal seja focado no mercado nacional hoje, a fundação deve prever a adoção de outros idiomas (ex: Guias para investidores estrangeiros).

## Decisão
Implementar o módulo oficial `@nuxtjs/i18n`.
1. **Arquivos de Dicionário:** Os textos fixos da UI (botões, mensagens de erro, alertas do formulário) serão movidos para `/i18n/locales/pt-BR.json` e `/en.json`.
2. **Number & DateTime Formatters:** Utilizaremos as funções integradas do `vue-i18n` para formatar nativamente todas as exibições de preço (`$n(1000000, 'currency')`) e datas de visitas.

## Consequências
- **Consistência Numérica:** Acaba com a confusão de formatos de vírgula e ponto flutuante em preços de imóveis no front-end.
- **Preparado para o Futuro:** O sistema já nasce internacionalizado.
- **Atenção no SSR:** Deve-se ter cuidado na formatação de datas (Timestamps do backend vs Timezones locais) para evitar problemas de hidratação (Hydration Mismatch) entre Servidor e Client.
