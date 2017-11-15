package br.com.crawler;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/**
 * Bean responsável por gerenciar as informações da thread do subreddit
 * @author Massao
 */
public class BeanRedditThread implements Serializable {

	private static final int MIN_POINTS_TO_ENTER_ON_TRENDING_TOPICS = 5000;

	private static final long serialVersionUID = 1L;

	private String subRedditName;
	private String threadName;
	private String threadLink;
	private Long upVotes;
	private String linkToComments;

	public BeanRedditThread(final String subRedditName, final String threadName, final String threadLink, 
			final String upVotes, final String linkToComemnts) {
		this.subRedditName = subRedditName;
		this.threadName = threadName;
		this.threadLink = threadLink;
		linkToComments = linkToComemnts;
		setUpVotes(upVotes);
	}
	
	/**
	 * Como o Reddit usa o k para representar o milhar, precisaremos fazer a conversão
	 * @param upVotes
	 */
	public void setUpVotes(String upVotes) {
		if(upVotes.contains("k"))
			upVotes = upVotes.replace("k", "000").replace(".", "");

		if(StringUtils.isNumeric(upVotes))
			this.upVotes = Long.parseLong(upVotes);
		else
			this.upVotes = 0L;
	}
	
	/**
	 * Um post popular, é um com mais de 5k de likes
	 * @return
	 */
	public boolean isOnTrendTopic() {
		if(upVotes >= MIN_POINTS_TO_ENTER_ON_TRENDING_TOPICS)
			return true;
		return false;
	}
	
	public Long getUpVotes() {
		return upVotes;
	}
	
	/* 
	 * Retorno a mensagem com informações da thread
	 */
	@Override
	public String toString() {
		StringBuilder threadInfo = new StringBuilder();
		
		threadInfo.append("\n \n ****** Sub-Reddit: " + this.subRedditName + " ******\n");
		threadInfo.append("\n Nome da thread: " + this.threadName);
		threadInfo.append("\n Número de likes: " + this.upVotes);
		threadInfo.append("\n Link para o sub-reddit: " +  this.threadLink);
		threadInfo.append("\n Links para Comentários: " + this.linkToComments);
		
		return threadInfo.toString();
	}
}
