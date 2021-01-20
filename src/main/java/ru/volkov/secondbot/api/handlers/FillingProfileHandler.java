package ru.volkov.secondbot.api.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.volkov.secondbot.api.BotState;
import ru.volkov.secondbot.api.InputMessageHandler;
import ru.volkov.secondbot.cache.DataCacheImpl;
import ru.volkov.secondbot.service.ReplyMessageService;

@Component
public class FillingProfileHandler implements InputMessageHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(FillingProfileHandler.class);

    private DataCacheImpl cache;
    private ReplyMessageService messageService;

    @Autowired
    public FillingProfileHandler(
            DataCacheImpl cache,
            ReplyMessageService messageService) {
        this.cache = cache;
        this.messageService = messageService;
    }

    @Override
    public SendMessage handle(Message message) {
        return null;
    }

    @Override
    public BotState getHandlerName() {
        return null;
    }
}
