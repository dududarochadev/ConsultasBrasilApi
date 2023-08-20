Esta aplicação é um microserviço com o objetivo de disponibilizar os dados de Modelo de carros baseados na Api de Fipe do parceiro Api Brasil.

Requisitos para executar a aplicação no VSCode: Extensão "Spring Boot Extension Pack" (que contém outras 3 extensões).
Para executar a aplicação deve-se clonar a aplicação para o VSCode e executa-la pela aba da extensão Spring Boot Dashboard.
Após executar, a aplicação estará rodando na porta 8080.

Exemplo de requisição para a aplicação localmente:
![image](https://github.com/dududarochadev/ConsultasBrasilApi/assets/114098227/afa6eac3-81be-4812-87f3-7b4f0d898423)

Os possíveis endpoints são:
Modelos:
- O objeto de retorno é o objeto Modelo, que contém a descrição do modelo, a descrição da marca do modelo e a data de inclusão no banco de dados. 

GET /modelos
- Retorna a lista de todos modelos disponíveis no banco de dados.
GET /modelos/marca/{id}
- Retorna a lista de todos modelos de uma determinada marca.
GET /modelos/ano/{ano}
- Retorna a lista de todos modelos de um determinado ano.
GET /modelos/data/{data}
- Retorna a lista de todos modelos que foram consultados na data informada.
