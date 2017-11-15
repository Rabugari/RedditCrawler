package br.com.crawler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author Massao
 */
@RunWith(JUnit4.class)
public class RedditWebCrawlerTest {

	private RedditWebCrawler redditCrawler;
	
	@Before
	public void setUp() {
		redditCrawler = new RedditWebCrawler();
	}
	
	@Test
	public void testOSplit() {
		assertEquals(1, redditCrawler.splitThreads("cats ;").size());
		assertEquals(2, redditCrawler.splitThreads("cats ;dogs ;;").size());
		assertEquals(3, redditCrawler.splitThreads("cats ;dogs;birds;").size());
		assertEquals(0, redditCrawler.splitThreads(";;;").size());
	}
	
	@Test
	public void teoricamenteSempreViraVazio() {
		String threads = redditCrawler.getThreads("cumbuca");
		assertTrue(StringUtils.isEmpty(threads));
	}
	
	@Test
	public void teoricamentSempreViraPreenchido() {
		String topThreads = redditCrawler.getTopThreads("popular;funny;AskReddit;");
		assertTrue(!StringUtils.isEmpty(topThreads));
	}
}
