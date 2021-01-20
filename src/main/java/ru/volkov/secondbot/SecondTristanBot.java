package ru.volkov.secondbot;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Getter
@Setter
public class SecondTristanBot extends TelegramWebhookBot {
    private final static Logger LOGGER = LoggerFactory.getLogger(SecondTristanBot.class);

    private String botPath;
    private String userName;
    private String token;

    public SecondTristanBot() {

    }

    public SecondTristanBot(DefaultBotOptions options) {
        super(options);
    }

    @Override
    public String getBotPath() {
        return botPath;
    }

    @Override
    public String getBotUsername() {
        return userName;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public BotApiMethod onWebhookUpdateReceived(Update update) {

        if(update.getMessage() != null && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            try {
                execute(new SendMessage(String.valueOf(chatId), "Hi, " + update.getMessage().getText()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

