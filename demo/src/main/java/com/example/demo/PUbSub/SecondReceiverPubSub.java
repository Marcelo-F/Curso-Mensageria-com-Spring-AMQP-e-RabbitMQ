package com.example.demo.PUbSub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

public class SecondReceiverPubSub {
    private static  String NAME_QUEUE =  "broadcast";
    private static  String NAME_EXCHANGE ="fanoutExchange";
    public static void main(String[] args) throws Exception{

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("172.22.0.2");
        factory.setUsername("admin");
        factory.setPassword("pass123");
        factory.setPort(5672);

        System.out.println("User: " + factory.getUsername());
        System.out.println("Passwoard: "+factory.getPassword());

        Connection connection = factory.newConnection();
        System.out.println("Conecction  -> " + connection.hashCode());


        Channel channel = connection.createChannel();
        System.out.println("Channel -> " + channel);
        channel.queueDeclare(NAME_QUEUE, false, false, false, null);

        channel.exchangeDeclare(NAME_EXCHANGE, "fanout");
        channel.queueBind(NAME_QUEUE, NAME_EXCHANGE,"");




        DeliverCallback deliverCallback = (ConsumerTag, delivery)->{
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("[x] Received message '" + message+"'");

        };


        //declaração da extende

        channel.basicConsume(NAME_QUEUE,true, deliverCallback, ConsumerTagStrategy ->{});

    }
}
