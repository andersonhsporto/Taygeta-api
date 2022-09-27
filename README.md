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
| Método  | Rota | Descrição |
|:---:    |:---: |:---:      |
| `GET`              | `/api/v1/planets`| Retorna uma lista ( JSON ) com os dados de todos os planetas cadastrados |
| `POST`             | `/api/v1/planets`| Adiciona um novo planeta utilizando uma string para representar a area deste  |
| `PUT`              | `/api/v1/planets`| Atualiza o tamanho do planeta utilizando o id deste no banco de dados </br>e uma string para representar a area deste |
| `GET`             | `/api/v1/planets{planetId}` | Retorna os dados ( JSON ) de um planeta utilizando o id deste no banco de dados  |
| `DELETE`          | `/api/v1/planets{planetId}` | Deleta um planeta e suas respectivas sondas utilizando </br>o id deste no banco de dados |
| `GET`             | `/api/v1/planets{planetId}/probes` | Retorna uma lista ( JSON ) com os dados de todas as sondas </br>de um respectivo planeta utilizando o id deste no banco de dados  |
| `DELETE`          | `/api/v1/planets{planetId}/probes` | Deleta todas as sondas de um respectivo planeta </br>utilizando o id deste no banco de dados |
| `GET`             | `/api/v1/probes` | Retorna uma lista ( JSON ) com todas as sondas cadastradas no banco de dados |
| `POST`            | `/api/v1/probes` | Adiciona uma nova sonda ao banco de dados utilizando, </br>id do planeta, ponto cardinal, posição x e y |
| `PUT`             | `/api/v1/probes` | Movimenta uma sonda utilizando o id desta no banco de dados </br>e uma sequência de movimentos  |
| `GET`             | `/api/v1/probes/{probeId}` | Retorna os dados ( JSON ) de uma sonda utilizando o id desta no banco de dados |
| `DELETE`          | `/api/v1/probes/{probeId}` | Deleta uma sonda utilizando id desta no banco de dados |

A documentação Swagger está disponível na rota: ```/swagger-ui/```: </br>
  &emsp; Uma demonstração desta documentação está disponível no link: [https://tay-prod-taygeta-ovkkud.mo1.mogenius.io/swagger-ui/](https://tay-prod-taygeta-ovkkud.mo1.mogenius.io/swagger-ui/)


## Inicialização

Para iniciar a api basta executar o comando:

```sh
  docker-compose up
```

Este comando irá iniciar um container com a api na porta 8080 e um container com o banco de dados que irá utilizar a porta 5432.




