package br.com.crawler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author Massao
 */
@RunWith(JUnit4.class)
public class BeanRedditThreadTest {

	private BeanRedditThread redditThreadNaoMuitoPopular;
	private BeanRedditThread redditThreadSemLikes;
	private BeanRedditThread redditThreadPopular;
	private BeanRedditThread redditThreadNeymarLevel;
	
	@Before
	public void setUp() {
		redditThreadNaoMuitoPopular = new BeanRedditThread("cats", 
				"Meeeowww",
				"https://www.reddit.com/r/cats/",
				"300", 
				"https://www.reddit.com/r/cats/comments/...");
		
		redditThreadSemLikes = new BeanRedditThread("cats", 
				"Hello! Kity",
				"https://www.reddit.com/r/cats/",
				"•", 
				"https://www.reddit.com/r/cats/comments/...");
		
		redditThreadPopular = new BeanRedditThread("cats", 
				"Where are my boots? ",
				"https://www.reddit.com/r/cats/",
				"6000", 
				"https://www.reddit.com/r/cats/comments/...");
		
		redditThreadNeymarLevel = new BeanRedditThread("cats", 
				"Thunder, thunder, thunder cats! Yoooooooooohhh...",
				"https://www.reddit.com/r/cats/",
				"23k", 
				"https://www.reddit.com/r/cats/comments/...");
	}
	
	@Test
	public void testaAsThreadQueEstaoBombando() {
		assertFalse(redditThreadNaoMuitoPopular.isOnTrendTopic());
		assertFalse(redditThreadSemLikes.isOnTrendTopic());
		assertTrue(redditThreadPopular.isOnTrendTopic());
		assertTrue(redditThreadNeymarLevel.isOnTrendTopic());
	}
	
	@Test
	public void testaRetornoThreadMensagem() {
		assertNotNull(redditThreadNaoMuitoPopular.toString());
		assertEquals(8, redditThreadNaoMuitoPopular.toString().split("\n").length);
		assertNotNull(redditThreadSemLikes.toString());
		assertEquals(8, redditThreadSemLikes.toString().split("\n").length);
		assertNotNull(redditThreadPopular.toString());
		assertEquals(8, redditThreadPopular.toString().split("\n").length);
		assertNotNull(redditThreadNeymarLevel.toString());
		assertEquals(8, redditThreadNeymarLevel.toString().split("\n").length);
	}
}
