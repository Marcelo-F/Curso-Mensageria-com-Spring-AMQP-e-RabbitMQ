package com.example.demo.DLX;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

public class ReceiverDLX {
    private static final String CONSUMER_QUEUE = "queueConsumer";

    public static void main(String[] args) throws Exception{

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("172.22.0.2");
        factory.setUsername("admin");
        factory.setPassword("pass123");
        factory.setPort(5672);


        Connection connection = factory.newConnection();


        Channel channel = connection.createChannel();


        DeliverCallback deliverCallback = (ConsumerTag, delivery)->{
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("[x] Received message '" + message+"'");

        };


       channel.basicConsume(CONSUMER_QUEUE,true, deliverCallback, ConsumerTagStrategy ->{});

    }
}
