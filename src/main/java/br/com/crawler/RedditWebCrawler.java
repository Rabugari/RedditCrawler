package br.com.crawler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Classe com o papel de spider para navegar nos foruns do Reddit
 *  e definir, ordenar os post que estão em alta
 *  
 * @author Massao
 */
public class RedditWebCrawler {
	
	private static final String REDDIT_DOMAIN = "http://www.reddit.com";
	private static final String SUB_REDDIT_DOMAIN = REDDIT_DOMAIN + "/r/";
	
	private static final String USER_AGENT = "\"Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6\")";
	private static final String DELIMITADOR_PARA_THREADS = ";";

	/**
	 * Recupera informações [message mode] das threads mais famosas do momento, dado subreddits 
	 * @param rawSubReddits - 1-n subreddits- separados por ;
	 * @return
	 */
	public String getTopThreads(String rawSubReddits) {
		StringBuilder sbAllTopThreads = new StringBuilder();

		List<String> subReddits = splitThreads(rawSubReddits);

		for(String subReddit : subReddits) {
			String topThreads = getThreads(subReddit.trim());

			if(StringUtils.isEmpty(topThreads))
				sbAllTopThreads.append( "Não há threads populares o sub-reddit ("+subReddit+") =/ ");
			else
				sbAllTopThreads.append(topThreads);
		}

		return sbAllTopThreads.toString();
	}

	/**
	 * Separa os subreddtis de acordo com o delimitador
	 * @param threads
	 * @return
	 */
	public List<String> splitThreads(String threads) {
		return Arrays.asList(threads.split(DELIMITADOR_PARA_THREADS));
	}

	/**
	 * Dado o subReddit, pesquiso as threads famosas do momento
	 * @param subReddit - 1 subreddit
	 * @return
	 */
	public String getThreads(String subReddit) {

		StringBuilder sbThreadRank = new StringBuilder();
		List<BeanRedditThread> topThreads = new ArrayList<>();
		Document document;

		try {
			if(StringUtils.isEmpty(subReddit))
				return "Sub-reddit vazio. Não foi possível pesquisar ;( ";

			document = Jsoup.connect(SUB_REDDIT_DOMAIN + subReddit).userAgent(USER_AGENT).timeout(0).get();

			Elements threads = document.select("div[class~=thing.id.*]");

			Element elementLinkThread= document.select("link[rel=canonical]").first();
			String threadLink = elementLinkThread.attr("href");

			for(Element thread : threads) {
				String subRedditName = thread.attr("data-subreddit");
				String linkToComments = thread.attr("data-permalink");
				String upVotes = thread.select("div.score.likes").html();
				String threadName = thread.select("a[class~=title.may-blank,*]").html();

				BeanRedditThread beanTopThread = new BeanRedditThread(subRedditName, threadName, threadLink, upVotes, REDDIT_DOMAIN + linkToComments);
				if(beanTopThread.isOnTrendTopic())
					topThreads.add(beanTopThread);
			}

			//ordenamos as threads de acordo com sua pontuacao
			topThreads.sort((BeanRedditThread b1, BeanRedditThread b2)->b1.getUpVotes().compareTo(b2.getUpVotes()));

		} catch(HttpStatusException he) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao acessar o site" + he.getMessage());
		}catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Ao processa sub-reddit" + e.getMessage());
		} 
		topThreads.forEach(thread -> sbThreadRank.append(thread.toString()));
		return sbThreadRank.toString();	
	}
}