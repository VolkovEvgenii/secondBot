package ru.volkov.secondbot.api.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.volkov.secondbot.api.BotState;
import ru.volkov.secondbot.api.InputMessageHandler;
import ru.volkov.secondbot.cache.DataCacheImpl;
import ru.volkov.secondbot.service.ReplyMessageService;

@Component
public class AskDestinyHandler implements InputMessageHandler {
    private final static Logger LOGGER = LoggerFactory.getLogger(AskDestinyHandler.class);

    private DataCacheImpl cache;
    private ReplyMessageService replyMessageService;

    public AskDestinyHandler(
            DataCacheImpl cache,
            ReplyMessageService replyMessageService) {
        this.cache = cache;
        this.replyMessageService = replyMessageService;
    }

    @Override
    public SendMessage handle(Message message) {
        return processUserInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.ASK_DESTINY;
    }

    private SendMessage processUserInput(Message message) {
        int userId = message.getFrom().getId();
        long chatId = message.getChatId();

        SendMessage replyToUser = replyMessageService.getReplyMessage(String.valueOf(chatId), "reply.askDestiny");
        cache.setUsersCurrentBotState(userId, BotState.FILLING_PROFILE);
        return replyToUser;
    }
}
