package ru.volkov.secondbot.cache;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.volkov.secondbot.api.BotState;
import ru.volkov.secondbot.api.UserProfileData;

import java.util.HashMap;
import java.util.Map;

@Component

public class DataCacheImpl implements DataCache{
    private final static Logger LOGGER = LoggerFactory.getLogger(DataCacheImpl.class);

    private Map<Integer, BotState> usersBotStates = new HashMap<>();
    private Map<Integer, UserProfileData> usersProfileData = new HashMap<>();

    @Override
    public void setUsersCurrentBotState(int userId, BotState state) {
        usersBotStates.put(userId, state);
    }

    @Override
    public BotState getUsersCurrentBotState(int userId) {
        BotState state = usersBotStates.get(userId);
        if(state == null) {
            state = BotState.ASK_DESTINY;
        }
        return state;
    }

    @Override
    public UserProfileData getUserProfileDate(int userId) {
        UserProfileData data = usersProfileData.get(userId);
        if(data == null) {
            data = new UserProfileData();
        }
        return data;
    }

    @Override
    public void saveUserProfileData(int userId, UserProfileData data) {
        usersProfileData.put(userId, data);
    }
}
