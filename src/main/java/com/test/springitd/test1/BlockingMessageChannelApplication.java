package com.test.springitd.test1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.TextMessage;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootApplication
@ImportResource("classpath:integration.xml")
public class BlockingMessageChannelApplication {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private ApplicationContext spring;

    public static void main(String[] args) {
        SpringApplication.run(BlockingMessageChannelApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            IntStream.range(1000, 1030).forEach(
                    msg -> jmsTemplate.send(ActiveMQConfig.QUEUE1, s -> s.createTextMessage(String.valueOf(msg)))
            );

            Thread.sleep(6000);

            jmsTemplate.setReceiveTimeout(1000);
            while (true) {
                TextMessage msg = (TextMessage) jmsTemplate.receive(ActiveMQConfig.QUEUE1);
                if (msg != null) {
                    System.err.println("Left:" + msg.getText());
                    continue;
                } else {
                    System.err.println("Empty Queue");
                    break;
                }
            }

            SpringApplication.exit(spring, () -> 1);
        };
    }
}