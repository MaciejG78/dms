package pl.com.bottega.dms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Created by maciek on 18.02.2017.
 */

@SpringBootApplication
public class DmsApplication {


    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(DmsApplication.class, args);
    }
}
