package com.example.demo.Routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

public class SecondReceiverRT {
    private static  String BIND_KEY_NAME ="secondRoutingKeyTest";

    private static  String NAME_EXCHANGE ="directExchange";


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

        //criar um novo canal

        Channel channel = connection.createChannel();
        System.out.println("Channel -> " + channel);




        DeliverCallback deliverCallback = (ConsumerTag, delivery)->{
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("[x] Received message '" + message+"'");

        };

        String nameQueue = channel.queueDeclare().getQueue(); // O servidor irá determinar um nome randomico para esta fila, ela será temporaria

        //declaração da extende
        channel.exchangeDeclare(NAME_EXCHANGE, "direct");

        channel.queueBind(nameQueue, NAME_EXCHANGE,BIND_KEY_NAME);
        channel.basicConsume(nameQueue,true, deliverCallback, ConsumerTagStrategy ->{});

    }
}
