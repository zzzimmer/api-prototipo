# API Gestão de Eventos

## Descrição:
A API fornece endpoints criados para alimentar um sistema de gerenciamento de eventos voltado exclusivamente para organizadores. 
Com essa ferramenta, é possível:
1 - Criar eventos 
2 - Visualizar eventos criados
3 - Visualizar lista de convidados para um evento
4 - Adicionar um convidado ao evento e enviar este convite por e-mail

### Especificações:
- Java 17
- Spring Boot 3.5.6
- Maven

### Dependências
- Spring Web (
- Spring Data JPA (SDJPA)
- MySql Connector J
- Bean Validation
- Spring Security
- Java Json Web Token (JWT)
- Spring Starter Mail
- DevTools
- Lombok
- MapStruct

## Recursos:
- Padrão Repository
- Banco de Dados MySql criado a partir de ``` spring.jpa.hibernate.ddl-auto ``` com base no Mapeamento Objeto-Relacional (ORM) nas entidades.
- Spring Security: Os recrusos de segurança permitem realizar login, logout, validação de requisições através de tokens nos Headerers.
- Criptografia de senhas através de ```BCryptPasswordEncoder ``` para guardar senhas em Hash no banco de dados.
- Validação de tempo de 2 horas nos tokens.
- Enviou automatico de E-mail a partir da criação de um convite na interface para o convidado do evento. 

## Endpoints:
Endereço padrão: http://localhost:8080/usuario

1. /criar-evento
Descrição: Retorna informações da versão da API e dados de produção
Método: POST
URL: http://localhost:8080/usuario/criar-evento
Resposta: JSON -> DetalhesEventoDTO

## Entidades:

## Como rodar:

### Configurar variaveis de ambiente:

### Configurar banco de dados:

### Coleção de requisições Insomnia:

### Acessar Front-End:

[link do repositório](https://github.com/zzzimmer/pwa-projeto-integrador)

## 
