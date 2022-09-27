# Taygeta API
<p align="center">
<img src="https://github.com/andersonhsporto/taygeta-api/blob/master/img/star.jpg" width="300px" alt="Taygeta Star"/><br>
</p>

Taygeta é uma [API REST](https://www.redhat.com/pt-br/topics/api/what-is-a-rest-api)
que implementa uma variação do desafio [Mars Rover](https://code.google.com/archive/p/marsrovertechchallenge/), utilizando o framework 
[spring](https://spring.io/projects/spring-boot).

## Ferramentas Utilizadas

* [JAVA 18](https://www.java.com/pt-BR/) - Linguagem de programação (JDK 18).
* [Apache Maven 3.8.6](https://maven.apache.org/) - Gerenciador de dependências.
* [IntelliJ](https://www.jetbrains.com/idea/) - IDE para desenvolvimento.
* [Docker](https://www.docker.com/) - Serviço de virtualização.
* [Spring](https://spring.io/projects/spring-boot) - Framework MVC.
* [Postgresql](https://www.postgresql.org/) - Banco de dados.


## Métodos
Requisições para a API devem seguir os padrões:
| Método             | Rota         | Descrição                                                                                    |
|:---: |:---: |:---: |
| `GET`              | `/api/v1/planets`| Retorna um lista ( JSON ) com os dados de todos os planetas cadastrados |
| `POST`             | `/api/v1/planets`| Adiciona um novo planeta utilizando uma string para representar a area deste  |
| `PUT`              | `/api/v1/planets`| Atualiza o tamanho do planeta utilizando o id do planeta no banco de dados e uma string para representar a area deste |
| `POST`             | `/users`         | creates a user in the DB (object user to be includued in request's body)                       |
| `PUT`              | `/users/:idplanetsplanets`     | updates an already created user in the DB (object user to be includued in request's body)      |
| `DELETE`           | `/users/:id`     | deletes a user from the DB (JWT token user ID must be the same as the user you want to delete) |


## Inicialização

Para iniciar a api basta executar o comando:

```sh
  docker-compose up
```

Este comando irá iniciar um container com a api na porta 8080 e um container com o banco de dados que irá utilizar a porta 5432.




