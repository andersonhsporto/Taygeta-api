# Taygeta CLI
<p align="center">
<img src="https://github.com/andersonhsporto/Taygeta-planet-probe/blob/master/img/star.jpg" width="300px" alt="Taygeta Star"/><br>
</p>

Taygeta é uma [interface de linha de comando](https://en.wikipedia.org/wiki/Command-line_interface) 
capaz de criar planetas, sondas e simular a movimentação / colisão destas por meio de comandos.




## Ferramentas Utilizadas

* [JAVA 18](https://www.java.com/pt-BR/) - Linguagem de programação (JDK 18).
* [Apache Maven 3.8.6](https://maven.apache.org/) - Gerenciador de dependências.
* [IntelliJ](https://www.jetbrains.com/idea/) - IDE para desenvolvimento.
* [Docker](https://www.docker.com/) - Serviço de virtualização.


## Inicialização

Para executar o projeto é necessário gerar o artefato *.jar*, uma forma de executar essa ação no linux é utilizando o comando:

```sh
  mvn package
```

Este comando irá gerar um *.jar* de nome `Taygeta-cli-1.0-SNAPSHOT.jar`, para executar este artefato basta utilizar o comando:

```sh
  java -jar Taygeta-cli-1.0-SNAPSHOT.jar
```

### Docker

Para executar este projeto no docker, basta utilizar o seguinte comando para gerar uma imagem deste projeto:

```sh
  docker build -t taygeta --build-arg JAR_FILE=Taygeta-cli-1.0-SNAPSHOT .
```

Este comando irá gerar uma imagem com o nome `taygeta`, para visualizar o projeto no contêiner basta utilizar o comando:

```sh
  docker run -it --rm taygeta
```

### Lista de Comandos
Comando | Definição
---|---------
`add-planet` | Adicionar novo planeta
`add-probe` | Adicionar sonda a um planeta existente
`move-probe` | Mover sonda existente
`list all` ou `list total` | Listar todos os planetas e suas respectivas sondas
`list planets` ou `list planet` | Listar todos os planetas
`list probes` ou `list probe` | Listar todas as sondas
`undo` | Desfazer comando atual
`help` ou `?` | Exibir lista de comandos
`exit`|  Encerrar o programa

### Exemplos:

Para adicionar um planeta é necessário utilizar o comando `add-planet` e inserir a altura e largura
do retângulo que representa a área deste planeta, utilizando o caractere x como delimitador (exemplo: `10x10`). 

<p align="center">
 <img src="https://github.com/andersonhsporto/Taygeta-planet-probe/blob/master/img/add-planet.gif" width="500px" alt="add-planet"/><br>
</p>

Para adicionar uma sonda a um planeta previamente cadastrado é necessário utilizar o comando `add-probe`, este comando irá solicitar o ID do planeta, coordenada x, coordenada y e o [ponto cardeal](https://pt.wikipedia.org/wiki/Ponto_cardeal) em inglês ou português.

<p align="center">
 <img src="https://github.com/andersonhsporto/Taygeta-planet-probe/blob/master/img/add-probe.gif" width="500px" alt="add-probe"/><br>
</p>

Para mover uma sonda previamente cadastrada é necessário usar o comando `move-probe`, este comando irá solicitar o ID do planeta, ID da sonda que será movimentada e a sequência de movimentos:

- `M` -> Mover a sonda uma unidade de área para frente
- `L` -> Virar a sonda para a esquerda (90 graus)
- `R` -> Virar a sonda para a direita (90 graus)

*Observação: se algum destes movimentos resultar em uma colisão o comando é cancelado.*

<p align="center">
 <img src="https://github.com/andersonhsporto/Taygeta-planet-probe/blob/master/img/move-probe.gif" width="500px" alt="move-probe"/><br>
</p>


Os comandos `list` exibem informações sobre as sondas e planetas cadastrados.

<p align="center">
 <img src="https://github.com/andersonhsporto/Taygeta-planet-probe/blob/master/img/list.gif" width="500px" alt="list-probe"/><br>
</p>

Utilize o comando `help` ou `?` para exibir uma lista com todos os comandos disponíveis.

<p align="center">
 <img src="https://github.com/andersonhsporto/Taygeta-planet-probe/blob/master/img/help.gif" width="500px" alt="help"/><br>
</p>


Utilize o comando `undo` para desfazer o comando atual ou caso queira encerrar a aplicação utilize o comando `exit`.

<p align="center">
 <img src="https://github.com/andersonhsporto/Taygeta-planet-probe/blob/master/img/undo.gif" width="500px" alt="undo"/><br>
</p>


<br>
<br>

<p align=left> <b>Minhas Informações de contato 📬</b> </p>
<p align=left>
<a href="https://github.com/andersonhsporto" target="_blank"><img src="https://img.shields.io/badge/Github-181717?logo=Github&logoColor=white"/></a>  
<a href="mailto:anderson.higo2@gmail.com" target="_blank"><img src="https://img.shields.io/badge/Gmail-EA4335?logo=Gmail&logoColor=white"/></a> 
<a href= "https://www.linkedin.com/in/andersonhsporto/"target="_blank"><img src="https://img.shields.io/badge/linkedin-%230077B5.svg?logo=linkedin&logoColor=white"/></a> 


