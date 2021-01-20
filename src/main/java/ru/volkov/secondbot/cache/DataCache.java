package ru.volkov.secondbot.cache;

import ru.volkov.secondbot.api.BotState;
import ru.volkov.secondbot.api.UserProfileData;

public interface DataCache {

    void setUsersCurrentBotState(int userId, BotState state);

    BotState getUsersCurrentBotState(int userId);

    UserProfileData getUserProfileDate(int userId);

    void saveUserProfileData(int userId, UserProfileData data);
}
