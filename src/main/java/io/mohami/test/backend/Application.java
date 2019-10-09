package io.mohami.test.backend;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * @author andrey
 */
@SpringBootApplication
@ComponentScan(basePackages = {"io.mohami.test.backend.controller", "io.mohami.test.backend.service"})
public class Application {
    public static void main(String[] args) throws Exception {
        new SpringApplication(Application.class).run(args);
    }
    @Bean
    @Autowired
    public ExecutorService cachedThreadPool() {
        return Executors.newCachedThreadPool();
    }
    
}
