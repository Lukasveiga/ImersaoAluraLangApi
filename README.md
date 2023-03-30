# Imersão Alura Java_☕: API de Langs

* Essa API contém as imagens das 10 linguagens de progamação mais usadas em 2023 segundo o site [Hostinger](https://www.hostinger.com.br/tutoriais/linguagens-de-programacao-mais-usadas).
* A lista de linguagens não segue a ordem mostrada no site, ela é ordenada pela quantidade de votos recebidos pelos usuários da API onde cada voto respresenta o uso da lingaguem pelo usuário.
* Por exemplo, se a linguagem Java contém 15 votos significa que 15 usuários da API utilizam dessa linguagem de programação.

Utilizando a API:

* Filtrar lista pelo nome da linguagem:<br><br>
    >`http://localhost:8080/langs/Java`

<p align="center">
  <img src="imgresults/img1.png" width="750">
</p>

* Cadastrar voto na linguagem desejada:<br><br>
  >`http://localhost:8080/langs/vote/Java`
  >
  >
  >`http://localhost:8080/langs/vote/SQL`

* Listar todas as linguagens de programação cadastradas:<br><br>
    >`http://localhost:8080/langs`

<p align="center">
  <img src="imgresults/img2.png" width="750">
</p>


  *******
Fonte das logos: [Programming Languages Logos](https://github.com/abrahamcalf/programming-languages-logos)
