# Imersão Alura Java_☕: API de Langs

* Essa API contém as imagens das 10 linguagens de progamação mais usadas em 2023 segundo o site [Hostinger](https://www.hostinger.com.br/tutoriais/linguagens-de-programacao-mais-usadas).
* A lista de linguagens não segue a ordem mostrada no site, ela é ordenada pela quantidade de votos recebidos pelos usuários da API onde cada voto respresenta o uso da lingaguem pelo usuário.
* Por exemplo, se a linguagem Java contém 15 votos significa que 15 usuários da API utilizam dessa linguagem de programação.

Utilizando a API:

* Filtrar lista pelo nome da linguagem:

    >http://localhost:8080/langs/Java

<p align="center">
  <img src="https://github.com/Lukasveiga/ImersaoAluraLangApi/blob/main/imgresults/img1.png?raw=true" width="800" height="100">
</p>

* Cadastrar voto na linguagem desejada:

  >http://localhost:8080/langs/vote/Java
  >
  >
  >http://localhost:8080/langs/vote/SQL

* Listar todas as linguagens de programação cadastradas:
    >http://localhost:8080/langs
<p align="center">
  <img src="https://github.com/Lukasveiga/ImersaoAluraLangApi/blob/main/imgresults/img2.png?raw=true" width="800" height="400">
</p>
<br>
Como rodar via Docker:<br><br>

* Faça o pull da imagem [Docker Image](https://hub.docker.com/r/lukasveiga/imersaoaluralangapi):
    
    ```
    docker pull lukasveiga/imersaoaluralangapi
    ```
* Configure a porta e.g. Ports: 8080 e rode a imagem:

    ```
    docker run lukasveiga/imersaoaluralangapi
    ```
* Acesse a api pelo path: http://localhost:8080/langs (Para o exemplo da porta configurada como 8080)
    

  *******
Fonte das logos: [Programming Languages Logos](https://github.com/abrahamcalf/programming-languages-logos)
