package ru.volkov.secondbot.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BotStateContext {
    private final static Logger LOGGER = LoggerFactory.getLogger(BotStateContext.class);

    private Map<BotState, InputMessageHandler> messageHandlers = new HashMap<>();

    @Autowired
    public BotStateContext(List<InputMessageHandler> messageHandlers) {
        messageHandlers.forEach(handler -> this.messageHandlers.put(handler.getHandlerName(), handler));
    }

    public SendMessage processInputMessage(BotState currentState, Message message){
        InputMessageHandler currentMessageHandler = findMessageHandler(currentState);
        return currentMessageHandler.handle(message);
    }

    private InputMessageHandler findMessageHandler(BotState currentState){
        if(isFillingProfileState(currentState)){
            return messageHandlers.get(BotState.FILLING_PROFILE);
        }
        return messageHandlers.get(currentState);
    }

    private boolean isFillingProfileState(BotState currentState) {
        switch (currentState) {
            case ASK_AGE:
            case ASK_NAME:
            case ASK_SONG:
            case ASK_COLOR:
            case ASK_MOVIE:
            case ASK_GENDER:
            case ASK_NUMBER:
            case ASK_DENSITY:
            case FILLING_PROFILE:
            case PROFILE_FILLED:
                return true;
            default:
                return false;
        }
    }
}
