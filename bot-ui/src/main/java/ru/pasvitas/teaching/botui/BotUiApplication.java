package ru.pasvitas.teaching.botui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class BotUiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BotUiApplication.class, args);
	}

}
