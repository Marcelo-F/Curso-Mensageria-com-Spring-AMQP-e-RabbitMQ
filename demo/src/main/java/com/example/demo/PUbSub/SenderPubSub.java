package com.example.demo.PUbSub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class SenderPubSub {


    private static  String NAME_EXCHANGE ="fanoutExchange";

    private static  String TYPE_EXCHANGE ="fanout";

    public static void main(String[] args) throws Exception{

        // primeiro criar a conexão
        //setar as informações para cria-la
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


        channel.exchangeDeclare(NAME_EXCHANGE,TYPE_EXCHANGE);
        String message = "Hello!! This is a pub/sub system !";

        // enviar a mensagem

        channel.basicPublish(NAME_EXCHANGE, "", null, message.getBytes());

        System.out.println("[x] Sent '" + message+"'");
    }
}
