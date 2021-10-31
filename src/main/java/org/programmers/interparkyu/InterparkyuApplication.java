package org.programmers.interparkyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class InterparkyuApplication {

    public static void main(String[] args) {
        SpringApplication.run(InterparkyuApplication.class, args);
    }

}
