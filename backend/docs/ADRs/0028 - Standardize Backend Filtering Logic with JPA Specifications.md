# ADR-B001: Padronização da Lógica de Filtros no Backend (Spring Data JPA Specifications)

## Status
Aceito

## Contexto
O backend necessitava de uma forma flexível e performática para lidar com múltiplos critérios de busca (filtros avançados do Catálogo) sem gerar código redundante ou consultas ineficientes ao banco de dados.

## Decisão
Decidimos utilizar **Spring Data JPA Specifications** através de Specifications Dinâmicas:

1. **Polimorfismo de Filtros:** A lógica de busca atravessa diferentes tipos de detalhes de imóvel (Subclasses da entidade principal) utilizando `Joins` dinâmicos (LEFT JOIN) conforme a necessidade da query.
2. **Consultas sob demanda:** Filtros nulos não geram clásulas `WHERE` estáticas desnecessárias, reduzindo a carga do banco.

## Consequências
- API de busca extremamente flexível e extensível.
- Separação clara entre o DTO da requisição e a lógica de persistência.
