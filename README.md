# PingPay API

Bem-vindo ao repositório da API PingPay\! Este projeto é uma solução de backend robusta, desenvolvida para gerenciar transações financeiras e contas de usuários, com foco em segurança, escalabilidade e facilidade de documentação.

-----

## 🛠️ Tecnologias e Ferramentas

* **Linguagem de Programação:** Java
* **Framework:** Spring Boot
* **Controle de Versão:** Git & GitHub
* **Gerenciador de Dependências:** Maven

-----

## ⚙️ Dependências Implementadas

Para garantir a segurança, autenticação e documentação eficiente da API, estas dependências foram cuidadosamente integradas:

* ### Spring Security

  Utilizado para configurar a **autenticação** e **autorização** na aplicação. O Spring Security oferece um framework poderoso e altamente configurável para proteger sua aplicação contra acesso não autorizado, garantindo que apenas usuários autenticados e com as permissões corretas possam acessar determinados recursos da API.

* ### JWT (JSON Web Tokens)

  Implementado para um mecanismo de autenticação **stateless** (sem estado). Após a autenticação bem-sucedida, um JWT é gerado e retornado ao cliente. Este token, assinado digitalmente, é usado para verificar a identidade do usuário em requisições subsequentes, eliminando a necessidade de sessões no servidor e proporcionando maior escalabilidade e desempenho para a API.

* ### Swagger (OpenAPI)

  Integrado para **documentação interativa** e **testes** da API. O Swagger gera automaticamente uma interface web que permite aos desenvolvedores e consumidores da API visualizar todos os *endpoints* disponíveis, seus métodos, parâmetros de requisição e modelos de resposta. Isso facilita significativamente a compreensão e o uso da API, acelerando o processo de desenvolvimento e integração.

-----

## 🚀 Como Rodar o Projeto

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/edudsfonec/PingPay.git
    ```
2.  **Navegue até o diretório do projeto:**
    ```bash
    cd PingPay
    ```
3.  **Construa o projeto com Maven:**
    ```bash
    mvn clean install
    ```
4.  **Execute a aplicação:**
    ```bash
    mvn spring-boot:run
    ```

A API estará disponível em `http://localhost:8080` (ou na porta configurada).
A documentação do Swagger estará acessível em `http://localhost:8080/swagger-ui.html`.
-----