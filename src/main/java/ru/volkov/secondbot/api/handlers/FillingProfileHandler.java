package ru.volkov.secondbot.api.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.volkov.secondbot.api.BotState;
import ru.volkov.secondbot.api.InputMessageHandler;
import ru.volkov.secondbot.api.UserProfileData;
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
        return BotState.FILLING_PROFILE;
    }

    private SendMessage processUsersInput(Message inputMessage) {
        String usersAnswer = inputMessage.getText();
        int userId = inputMessage.getForwardFrom().getId();
        String chatId = String.valueOf(inputMessage.getChatId());

        UserProfileData userData = cache.getUserProfileDate(userId);
        BotState state = cache.getUsersCurrentBotState(userId);

        SendMessage replyToUser = null;

        if(state.equals(BotState.ASK_NAME)){
            replyToUser = messageService.getReplyMessage(chatId, "reply.askName");
            cache.setUsersCurrentBotState(userId, BotState.ASK_AGE);
        }

        if (state.equals(BotState.ASK_AGE)) {
            userData.setName(usersAnswer);
            replyToUser = messageService.getReplyMessage(chatId, "reply.askAge");
            cache.setUsersCurrentBotState(userId, BotState.ASK_GENDER);
        }

        if (state.equals(BotState.ASK_GENDER)) {
            replyToUser = messageService.getReplyMessage(chatId, "reply.askGender");
            userData.setAge(Integer.parseInt(usersAnswer));
            cache.setUsersCurrentBotState(userId, BotState.ASK_NUMBER);
        }

        if (state.equals(BotState.ASK_NUMBER)) {
            replyToUser = messageService.getReplyMessage(chatId, "reply.askNumber");
            userData.setGender(usersAnswer);
            cache.setUsersCurrentBotState(userId, BotState.ASK_COLOR);
        }

        if (state.equals(BotState.ASK_COLOR)) {
            replyToUser = messageService.getReplyMessage(chatId, "reply.askColor");
            userData.setNumber(Integer.parseInt(usersAnswer));
            cache.setUsersCurrentBotState(userId, BotState.ASK_MOVIE);
        }

        if (state.equals(BotState.ASK_MOVIE)) {
            replyToUser = messageService.getReplyMessage(chatId, "reply.askMovie");
            userData.setColor(usersAnswer);
            cache.setUsersCurrentBotState(userId, BotState.ASK_SONG);
        }

        if (state.equals(BotState.ASK_SONG)) {
            replyToUser = messageService.getReplyMessage(chatId, "reply.askSong");
            userData.setMovie(usersAnswer);
            cache.setUsersCurrentBotState(userId, BotState.PROFILE_FILLED);
        }

        if (state.equals(BotState.PROFILE_FILLED)) {
            userData.setSong(usersAnswer);
            cache.setUsersCurrentBotState(userId, BotState.ASK_DESTINY);
            replyToUser = new SendMessage(chatId, String.format("%s %s", "Данные по вашей анкете", userData));
        }

        cache.saveUserProfileData(userId, userData);

        return replyToUser;
    }
}
