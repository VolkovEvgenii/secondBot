package ru.volkov.secondbot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.volkov.secondbot.SecondTristanBot;

@RestController
public class SecondBotController {

    private final SecondTristanBot secondTristanBot;

    @Autowired
    public SecondBotController(SecondTristanBot secondTristanBot) {
        this.secondTristanBot = secondTristanBot;
    }

    @PostMapping(value = "/")
    public BotApiMethod<?> onUpdateRecieved(@RequestBody Update update){
        return secondTristanBot.onWebhookUpdateReceived(update);
    }
}
