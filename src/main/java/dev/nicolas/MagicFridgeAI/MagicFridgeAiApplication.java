package dev.nicolas.MagicFridgeAI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MagicFridgeAiApplication {

	public static void main(String[] args) {

		System.setProperty("io.netty.noUnsafe", "true");
		SpringApplication.run(MagicFridgeAiApplication.class, args);
	}

}
