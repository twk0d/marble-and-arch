# Marble & Arch — Backend (Spring Boot)

API central responsável por processar todas as regras de negócio, persistência e comunicação do portal Marble & Arch.

## Stack Tecnológico

*   **Linguagem:** Java 24 (compilando em compatibilidade v21)
*   **Framework:** Spring Boot 3.4.7
*   **Modularidade:** Spring Modulith 1.3.7
*   **Banco de Dados:** PostgreSQL 16
*   **Segurança:** Spring Security + JWT
*   **Migrações:** Flyway

## Padrões Arquiteturais e DDD

Conforme documentado na pasta `docs/ADRs`, a API adota uma estrutura de **Monólito Modular** baseada em Domain Driven Design (DDD):
1. **Separação:** Módulos estritamente separados representando Bounded Contexts.
2. **CQRS e Event-Driven:** Separação entre Queries (Leitura) e Commands (Escrita), interligados via Domain Events assíncronos no Spring.
3. **Padrão Details:** Propriedades variadas (Casas, Aptos, Lotes) são tratadas por composição polimórfica para limpar o modelo de dados.

## Instalação e Execução (Local)

Certifique-se de ter o JDK configurado na sua máquina e um servidor PostgreSQL rodando localmente (ou use o `docker-compose` da raiz do projeto).

```bash
# Baixar dependências e compilar
./gradlew build -x test

# Rodar aplicação localmente
./gradlew runDev
```

A API iniciará na porta `8081` (padrão) e todas as rotas operam sob o prefixo `/api/v1/...`.
