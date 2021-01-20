package ru.volkov.secondbot.appconfig;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.volkov.secondbot.SecondTristanBot;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "telegrambot")
public class BotConfig {

    private String botPath;
    private String userName;
    private String token;

    @Bean
    public SecondTristanBot getSecondTristanBot() {

        SecondTristanBot secondTristanBot = new SecondTristanBot();
        secondTristanBot.setBotPath(botPath);
        secondTristanBot.setUserName(userName);
        secondTristanBot.setToken(token);

        return secondTristanBot;
    }

    public MessageSource messageSource(){
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

}
