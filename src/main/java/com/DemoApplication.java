package com;

import com.rab.receive.Receiver;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class DemoApplication {

    public static final String topicExchangeName = "spring-boot-exchange";

    public static final String queueName = "spring-boot";

    @Bean
    Queue queue() {
        System.out.println(" Registering Queue "+queueName);
        return new Queue(queueName, false);
    }

    @Bean
    TopicExchange exchange() {
        System.out.println(" Registering Exchange "+topicExchangeName);
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        final String routingKey = "foo.bar.#";
        System.out.println(" Binding Exchange to Queue "+routingKey);
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        System.out.println(" Simple Message Container Adapter ");
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        System.out.println(" Addding Listener Adapter ");
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
