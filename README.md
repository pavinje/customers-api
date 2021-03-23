# customers-api

Observações:
    - Tentei fazer o possível para adequar as melhores práticas de desenvolvimento e cobertura de testes unitários, no entanto a falta de tempo, devido ao meu trabalho atual, impedirem que eu investisse mais tempo para melhoria do código.

Banco de dados:
    - Utilizei o H2, como banco de dados em memória, também para ganhar tempo, no entanto a ferramenta de migração, tanto quanto a conexão podem apontar para outro banco de dados alterando as variáveis no .properties.

Teste Unitários:
    - Comecei o projeto utilizando TDD, no entanto também por problemas de tempo, acabei implementando somente cenários para 1 operação, entendo que o correto é uma cobertura de no minímo 80% de testes e só não foi feito por falta de tempo.

RUN:

Executar os seguintes comandos na raiz para rodar normal.
    - mvn clean package install
    - java -jar target/customers-0.0.1-SNAPSHOT

Executar o seguintes comandos na raiz para rodar em container
    - docker build . -t customers-api
    - docker run customers-api

POSTMAN:
    https://www.getpostman.com/collections/79a3131501f706e0c705

SWAGGER:
    /resources/swagger/openapi.yaml