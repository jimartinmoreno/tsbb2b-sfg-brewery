package guru.springframework.brewery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling // Enables Spring's scheduled task execution capability
@EnableAsync // Enables Spring's asynchronous method execution capability, similar to functionality found in Spring's <task:*> XML namespace.
@SpringBootApplication
public class TsbbSfgBreweryApplication {

    public static void main(String[] args) {
        SpringApplication.run(TsbbSfgBreweryApplication.class, args);
    }

}
