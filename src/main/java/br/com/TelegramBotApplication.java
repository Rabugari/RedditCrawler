package br.com;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import br.com.bot.TelegramBot;

/**
 * Telegram Bot para te informar os post bombando do Reddit, quando você não tiver nada para fazer
 * 
 *  Link para o bot: t.me/TopThreadRedditBot
 * 
 * @author Massao
 */
public class TelegramBotApplication {

	public static void main(String[] args) {
		try {
			ApiContextInitializer.init();
			TelegramBotsApi botsApi = new TelegramBotsApi();
			botsApi.registerBot(new TelegramBot());
			
			Logger.getLogger(TelegramBotApplication.class.getName()).log(Level.INFO, "I'm back baby...");
			
		} catch (TelegramApiRequestException e) {
			Logger.getLogger(TelegramBotApplication.class.getName()).log(Level.SEVERE, e.getMessage());
		}
	}
}
