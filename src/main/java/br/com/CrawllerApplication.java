package br.com;

import java.util.Scanner;

import br.com.crawler.RedditWebCrawler;

/**
 * Aplicação que apresenta as threads que estão bombando no momento no Reddit
 * Com base nos subreddits inserido pelo usuário
 * Exemplo: funny;news; 
 * 
 * @author Massao
 */
public class CrawllerApplication {

	private static RedditWebCrawler redditCrawler;
	private static Scanner scanner;
	
	public static void main(String[] args) {
		redditCrawler = new RedditWebCrawler();
		
		scanner = new Scanner(System.in);
		System.out.println("Digite subreddits (separados por ponto-vírgula(;)), para saber as threads que estão bombando!:");
		String subReddits = scanner.nextLine();
		String topRedditThreads = redditCrawler.getTopThreads(subReddits);
		System.out.println(topRedditThreads);
	}
}
