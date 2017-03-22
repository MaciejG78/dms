package pl.com.bottega.dms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
//@EnableAsync
public class DmsApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(DmsApplication.class, args);
    }
}

