# RedditCrawler
Algoritmo que gera informações de threads que estão bombando no [Reddit](https://www.reddit.com/), dado um subreddit (subtema)

As informações informadas para o usuário são:

* Número de upvotes(likes do post)
* Título da Thread
* Link para o  Subreddit
* Links para comentários da Thread

A aplicação também contém uma integração com o Telegram, para adicionar a funcionalidade basta digitar o comando:

	/NadaPraFazer [+ Lista de subrredits] (ex.: /NadaPraFazer programming;dogs;brazil)
	
O link para o robo: t.me/TopThreadRedditBot

## Getting Started
Para a integração com o Telegram funcionar, esta aplicação precisa esta no ar. 

## Built With
**Gradle -** Dependency Management

## Frameworks utilizados
Para desenvolver esta aplicação, utilizei:

* [TelegramBots](https://github.com/rubenlagus/TelegramBots/wiki/Getting-Started)
* [Jsoup](https://jsoup.org/)
* [vdurmont:emoji](https://github.com/vdurmont/emoji-java)

