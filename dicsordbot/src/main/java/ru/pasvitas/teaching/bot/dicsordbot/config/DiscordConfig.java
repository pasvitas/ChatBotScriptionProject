package ru.pasvitas.teaching.bot.dicsordbot.config;

import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DiscordConfig {

    @Bean
    public JDA configBot() throws LoginException {
        JDABuilder builder = JDABuilder.createDefault("Token");
        builder.disableCache(CacheFlag.ACTIVITY);
        builder.setBulkDeleteSplittingEnabled(false);
        builder.setActivity(Activity.playing("testing"));
        return builder.build();
    }
}
