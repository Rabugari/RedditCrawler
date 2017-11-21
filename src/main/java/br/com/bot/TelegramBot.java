package br.com.bot;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.vdurmont.emoji.EmojiParser;

import br.com.TelegramBotApplication;
import br.com.crawler.RedditWebCrawler;

/**
 * Bot do aplicativo Telegram, responsável pela lógica de enviar os posts que estão bombando no Reddit,
 * obedecendo o comando /NadaPraFazer + nomes_de_subreddits (separados por ponto-vírgula)
 * 
 * Exemplo: /NadaPraFazer funny;news
 * 
 * Link: Framework utilizado: https://github.com/rubenlagus/TelegramBots/wiki/Getting-Started
 * 
 * Para subir a aplicação, executar: {@link TelegramBotApplication}
 * 
 * @author Massao
 */
public class TelegramBot extends TelegramLongPollingBot {

	private static final String BOT_NAME = "TopThreadRedditBot";
	private static final String TELEGRAM_TOKEN = "423508922:AAH0Wj4l-xPhtajVAmpog_ADEMKZ0TIJZe8";
	private static final String COMMAND_NAME = "/NadaPraFazer";
	
	private RedditWebCrawler webCrawler;
	
	@Override
	public void onUpdateReceived(final Update update) {
		try {
			if(update.hasMessage() && update.getMessage().hasText()) {
				
				String messageReceived = update.getMessage().getText();
			    long chatId = update.getMessage().getChatId();
			    
			    SendMessage message = new SendMessage();
			    message.setChatId(chatId);

			    if(messageReceived!=null && messageReceived.startsWith(COMMAND_NAME)) {
			    	webCrawler = new RedditWebCrawler();
			    	sendMessage(messageReceived, message);
			    }else {
			    	message.setText(EmojiParser.parseToUnicode("Como ? não entendi ... :sweat:"));
			    	sendMessage(messageReceived, message);
			    }
			}
		} catch (TelegramApiException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao processar robo Telegram");
		}
	}

	private void sendMessage(final String messageReceived, final SendMessage message) throws TelegramApiException {
		List<String> subReddits = webCrawler.splitThreads(messageReceived.replaceAll(COMMAND_NAME, "").trim());
		
		for(String subReddit : subReddits) {
		 	String topThreads = webCrawler.getThreads(subReddit.trim());
		 	if(StringUtils.isEmpty(topThreads))
				message.setText(EmojiParser.parseToUnicode("Não há threads populares o sub-reddit ("+subReddit+")  :disappointed:"));
			else
				message.setText(topThreads);
		 	sendMessage(messageReceived, message);
		}
	}

	@Override
	public String getBotUsername() {
		return BOT_NAME;
	}

	@Override
	public String getBotToken() {
		return TELEGRAM_TOKEN;
	}
}
