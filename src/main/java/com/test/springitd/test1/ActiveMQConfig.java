package com.test.springitd.test1;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;

@Configuration
public class ActiveMQConfig {
    public static final String QUEUE1 = "queue1";

    @Bean
    public Queue queue1() {
        return new ActiveMQQueue(QUEUE1);
    }

    private final static String BROKER_URL = "tcp://localhost:61616";

    @Bean
    public BrokerService brokerService() throws Exception {
        BrokerService broker = new BrokerService();
        broker.addConnector(BROKER_URL);
        broker.setPersistent(false);
        broker.start();
        return broker;
    }

    @Bean
    public ActiveMQConnectionFactory jmsConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(BROKER_URL);
        return connectionFactory;
    }
}