package com.example.demo.Topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

public class ReceiverTopic {
    private static  String BIND_KEY ="*.*.rabbit";

    private static  String NAME_EXCHANGE ="topicExchange";

    private static String TYPE_EXCHANGE ="topic";


    public static void main(String[] args) throws Exception{

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("172.22.0.2");
        factory.setUsername("admin");
        factory.setPassword("pass123");
        factory.setPort(5672);

        Connection connection = factory.newConnection();
        System.out.println("Conecction  -> " + connection.hashCode());

        //criar um novo canal

        Channel channel = connection.createChannel();

        String nameQueue = channel.queueDeclare().getQueue();

        channel.exchangeDeclare(NAME_EXCHANGE, TYPE_EXCHANGE);
        channel.queueBind(nameQueue, NAME_EXCHANGE, BIND_KEY);

        DeliverCallback deliverCallback = (ConsumerTag, delivery)->{
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("[x] Received message '" + message+"'");

        };

       channel.basicConsume(nameQueue,true, deliverCallback, ConsumerTagStrategy ->{});

    }
}
