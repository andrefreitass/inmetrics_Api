# Projeto Desafio Inmetrics - API

### 🛠️ Ferramentas

 - Java 8
 - Maven 3.8.1
 - TestNg 6.14.3
 - Rest-Assured 3.3.0 
 - Log4j 1.2.17
 - Allure 2.8.1
 
### Configuração do projeto
- Realizar o import do projeto na IDE desejada;
- Na raiz do projeto mvn clean install;

### Configuração do relatório
- Baixar o allure results https://github.com/allure-framework/allure2/releases/tag/2.13.5
- Descompactar o allure na pasta desejada
- Criar a variavel de ambiente ALLURE_HOME o caminho da variável deve está no mesmo nivel que a pasta BIN

### Execução do projeto:
- Acessar ${basedir}/src/main/resources/suite executar o teste pela a IDE;

### Relatório será gerado no conforme abaixo:
- Após a execução da suite na pasta raiz será criado a pasta allure-results;
- No mesmo nível que a pasta allure-results executar o comando no promt de comando allure serve;
- Acessa a aba Behaviors, nesta aba todos os testes estarão vinculados com as história de usuário;


