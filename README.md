# Taygeta API
<p align="center">
<img src="https://github.com/andersonhsporto/taygeta-api/blob/master/img/star.jpg" width="300px" alt="Taygeta Star"/><br>
</p>

  &emsp;Taygeta é uma [API REST](https://www.redhat.com/pt-br/topics/api/what-is-a-rest-api)
que implementa uma variação do desafio [Mars Rover](https://code.google.com/archive/p/marsrovertechchallenge/), utilizando o framework
[Spring](https://spring.io/projects/spring-boot).

</br>

Uma versão em modo [interface de linha de comando](https://en.wikipedia.org/wiki/Command-line_interface) está disponível na branch [0-cli](https://github.com/andersonhsporto/taygeta-api/tree/0-cli).

## Ferramentas Utilizadas

* [JAVA 18](https://www.java.com/pt-BR/) - Linguagem de programação (JDK 18).
* [Spring](https://spring.io/projects/spring-boot) - Framework MVC.
* [Apache Maven 3.8.6](https://maven.apache.org/) - Gerenciador de dependências.
* [IntelliJ](https://www.jetbrains.com/idea/) - IDE para desenvolvimento.
* [Docker](https://www.docker.com/) - Serviço de virtualização.
* [PostgreSQL](https://www.postgresql.org/) - Banco de dados.


## Métodos
Requisições para a API devem seguir os padrões:
| Método  | Rota | Descrição |
|:---:    |:---: |:---:      |
| `GET`              | `/api/v1/planets`| Retorna uma lista ( JSON ) com os dados de todos os planetas cadastrados |
| `POST`             | `/api/v1/planets`| Adiciona um novo planeta utilizando uma string para representar a area deste  |
| `PUT`              | `/api/v1/planets`| Atualiza o tamanho do planeta utilizando o id deste no banco de dados </br>e uma string para representar a área deste |
| `GET`             | `/api/v1/planets/{planetId}` | Retorna os dados ( JSON ) de um planeta utilizando o id deste no banco de dados  |
| `DELETE`          | `/api/v1/planets/{planetId}` | Deleta um planeta e suas respectivas sondas utilizando </br>o id deste no banco de dados |
| `GET`             | `/api/v1/planets/{planetId}/probes` | Retorna uma lista ( JSON ) com os dados de todas as sondas </br>de um respectivo planeta utilizando o id deste no banco de dados  |
| `DELETE`          | `/api/v1/planets/{planetId}/probes` | Deleta todas as sondas de um respectivo planeta </br>utilizando o id deste no banco de dados |
| `GET`             | `/api/v1/probes` | Retorna uma lista ( JSON ) com todas as sondas cadastradas no banco de dados |
| `POST`            | `/api/v1/probes` | Adiciona uma nova sonda ao banco de dados utilizando, </br>id do planeta, ponto cardinal, posição x e y |
| `PUT`             | `/api/v1/probes` | Movimenta uma sonda utilizando o id desta no banco de dados </br>e uma sequência de movimentos  |
| `GET`             | `/api/v1/probes/{probeId}` | Retorna os dados ( JSON ) de uma sonda utilizando o id desta no banco de dados |
| `DELETE`          | `/api/v1/probes/{probeId}` | Deleta uma sonda utilizando id desta no banco de dados |

Os detalhes sobre como utilizar estes métodos ( Swagger UI ) estão disponíveis rota `/swagger-ui/`: </br>
  &emsp; Uma demonstração desta está disponível no link: [https://tay-prod-taygeta-ovkkud.mo1.mogenius.io/swagger-ui/](https://tay-prod-taygeta-ovkkud.mo1.mogenius.io/swagger-ui/)
 
 
### Variável de ambiente
 
Este projeto utiliza as seguintes variáveis de ambiente

| Variável           | Descrição                              |
| :-----------------:| :----------------------------------:   |
| POSTGRES_USER      | Usuário do banco de dados              |
| POSTGRES_PASSWORD  | Senha do banco de dados                |
| DATABASE_HOST      | Host para conexão com o banco de dados |


## Inicialização

Para iniciar a api utilize o comando:

```sh
  docker-compose up
```

Este comando irá iniciar um contêiner com a api na porta `8080` e um contêiner com o banco de dados utilizando a porta `5432`.

Para executar somente os testes utilize o comando:


```sh
  mvn test
```

## Dependências
&emsp;As dependências são declaradas no arquivo [pom.xml](https://github.com/andersonhsporto/taygeta-api/blob/master/pom.xml).

| Dependência                     | Descrição                                                          | Versão  |
| :----------------------------:  | :---------------------------------------------------------------: | :-----: |
| Postgresql                      | JDBC Driver para o banco de dados Postgresql                      | 42.5.0  |
| H2database                      | Banco de dados relacional escrito em Java que funciona em memória | 2.1.214 |
| Spring-boot-starter-data-jpa    | Responsável por conectar a aplicação Spring como o banco de dados | 2.7.4   |
| Spring-boot-starter-web         | Responsável pela camada MVC do Spring                             | 2.7.4   |
| Junit-jupiter                   | Framework para testes unitários                                   | 5.9.1   |
| Springfox-swagger-ui            | Documentação JSON API para aplicações Spring                      | 3.0.0   |


## Exemplos
<details>
  <summary>Consultar todos os planetas cadastrados [ GET ]</summary>
 
#### *Planetas [ /api/v1/planets ]*

&emsp;Ao utilizar este método na rota referente aos planetas, a api retorna uma lista ( json ) com os dados de todos os planetas cadastrados.

Exemplo: `/api/v1/planets/` irá retornar um json com os dados de todos os planetas cadastrados.

#### Adicionar Novo Planeta [ POST ]

  &emsp;Para criar um novo planeta é necessário utilizar o parâmetro query `area` este parâmetro,
  &emsp; utiliza uma string com o caractere x como delimitador entre altura e largura
do retângulo utilizado para representar o planeta.

| Parâmetro | Descrição |
|:---:|:---:|
| `area` | String utilizada para representar a area do planeta |

Exemplo: `/api/v1/planets?area=4x2` irá criar um planeta com quatro unidades de largura e duas unidades de altura.

</details>

<details>
  <summary> Editar tamanho de um planeta [ PUT ]</summary>
 
  #### *Planetas [ /api/v1/planets ]*

  &emsp;Para editar o tamanho de um planeta previamente cadastrado no banco de dados é necessário utilizar o parâmetro query `planetId`, este parâmetro é um número inteiro utilizado para identificar o planeta no banco de dados, além disso, é necessário informar o novo tamanho do planeta utilizando uma string com o caractere x como delimitador entre altura e largura do retângulo utilizado para representar o planeta.
&emsp;Todas as sondas que estão fora das novas dimensões do planeta serão deletadas.

| Parâmetro | Descrição |
|:---:|:---:|
| `planetId` | Id do planeta no banco de dados |
| `area` | String utilizada para representar a área do planeta |

Exemplo: `/api/v1/planets?area=5x5&id=1` irá alterar o tamanho do planeta id 1, para cinco unidades de altura e largura.

</details>

<details>
  <summary> Consultar planeta por id [ GET ]</summary>

 #### *Planetas /api/v1/planets/{planetId}*

&emsp;Para consultar um planeta previamente cadastrado no banco de dados é necessário utilizar o parâmetro route `planetId`, este parâmetro é um número inteiro utilizado para identificar o planeta no banco de dados.

Exemplo: `/api/v1/planets/1` irá retornar um json com os dados referente ao planeta.

| Parâmetro | Descrição |
|:---:|:---:|
| `planetId` | Id do planeta no banco de dados |

Exemplo: `/api/v1/planets/4` irá retornar um json com os dados do planeta.

</details>

<details>
  <summary> Deletar um planeta por id [ DELETE ]</summary>
 
 #### *Planetas /api/v1/planets/{planetId}*

&emsp;Para deletar um planeta previamente cadastrado no banco de dados é necessário utilizar o parâmetro route `planetId`, este parâmetro é um número inteiro utilizado para identificar o planeta no banco de dados.
&emsp;Todas as sondas deste planeta são deletadas ao utilizar este método.

| Parâmetro | Descrição |
|:---:|:---:|
| `planetId` | Id do planeta no banco de dados |

Exemplo: `/api/v1/planets/4` deleta o planeta id 4 e todas as sondas associadas a este.

</details>

<details>
  <summary> Consultar todas as sondas cadastradas [ GET ]</summary>

#### *Sondas [ /api/v1/probes ]*

&emsp;Ao utilizar este método na rota referente aos planetas, a api retorna uma lista ( json ) com os dados de todos os planetas cadastrados.

Exemplo: `/api/v1/probes/` irá retornar um json com os dados de todas as sondas cadastradas.

</details>

<details>
  <summary> Adicionar nova sonda [ POST ]</summary>
 
  #### *Sondas [ /api/v1/probes ]*

  &emsp;Para criar uma nova sonda é necessário utilizar os parâmetros query `direction`, `planetId`, `X` e `Y`. Estes parâmetros representam a posição inicial da sonda no planeta.
  &emsp;A posição inicial da sonda deve ser um dos quatro pontos cardinais ( Norte, Sul, Leste, Oeste ) em inglês ou português.

| Parâmetro | Descrição |
|:---:|:---:|
| `direction` | Ponto cardinal inicial da sonda |
| `planetId` | Id do planeta no banco de dados |
| `X` | Coordenada x da sonda |
| `Y` |  Coordenada y da sonda |

Exemplo: `/api/v1/probes?direction=NORTE&planetId=1&x=4&y=2` irá criar uma nova sonda, no planeta id 1, nas coordenadas x4 y2, apontada para o norte.

</details>

<details>
  <summary> Mover sonda [ PUT ]</summary>
 
  #### *Sondas [ /api/v1/probes ]*

&emsp;Para mover uma sonda é necessário utilizar os parâmetros query `movements` e `probeId`.
&emsp;O parâmetro `movements` é uma string onde cada caractere desta representa um movimento da sonda: :
- `M` -> Mover a sonda uma unidade para frente.
- `L` -> Virar a sonda para a esquerda (90 graus)
- `R` -> Virar a sonda para a direita (90 graus)

| Parâmetro | Descrição |
|:---:|:---:|
| `movements` | Sequencia de movimentos da sonda |
| `probeId` | Id da sonda no banco de dados |

Exemplo: `/api/v1/probes?movements=LMLMLMLMM&probeId=1` irá mover a sonda de id 1 para uma nova direção.

</details>

<details>
  <summary> Consultar sonda por id  [ GET ]</summary>
 
  #### *Sondas [ /api/v1/probes/{probeId} ]*

&emsp;Para consultar uma sonda previamente cadastrada no banco de dados é necessário utilizar o parâmetro route `probeId`, este parâmetro é um número inteiro utilizado para identificar a sonda no banco de dados.

| Parâmetro | Descrição |
|:---:|:---:|
| `probeId` | Id da sonda no banco de dados |

Exemplo: `/api/v1/probes/1` retorna um json com os dados da sonda id 1.

</details>

<details>
  <summary> Deletar sonda por id  [ DELETE ]</summary>
 
  #### *Sondas [ /api/v1/probes/{probeId} ]*

&emsp;Para deletar uma sonda previamente cadastrada no banco de dados é necessário utilizar o parâmetro route `probeId`, este parâmetro é um número inteiro utilizado para identificar a sonda no banco de dados.

| Parâmetro | Descrição |
|:---:|:---:|
| `probeId` | Id da sonda no banco de dados |

Exemplo: `/api/v1/probes/1` deleta a sonda id 1 do banco de dados.
</details>

---


<p align=left> <b>Minhas informações de contato 📬</b></p>
<p align=left>
<a href="https://github.com/andersonhsporto" target="_blank"><img src="https://img.shields.io/badge/Github-181717?logo=Github&logoColor=white"/></a>  
<a href="mailto:anderson.higo2@gmail.com" target="_blank"><img src="https://img.shields.io/badge/Gmail-EA4335?logo=Gmail&logoColor=white"/></a>
<a href= "https://www.linkedin.com/in/andersonhsporto/"target="_blank"><img src="https://img.shields.io/badge/linkedin-%230077B5.svg?logo=linkedin&logoColor=white"/></a>
