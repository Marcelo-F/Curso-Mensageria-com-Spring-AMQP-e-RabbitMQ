package com.example.demo.Routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class SenderRT {


    private static  String NAME_EXCHANGE ="directExchange";

    private static String ROUTING_KEY ="routingKeyTest";

    private static String ROUTING_KEY_SECOND ="secondRoutingKeyTest";

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


        channel.exchangeDeclare(NAME_EXCHANGE,"direct");

        // criar a msg

        String message = "Hello!! This is a RabbitMQ system !";
        String secondMessage = "Hello!! This is a routing key based system !";

        // enviar a mensagem

        channel.basicPublish(NAME_EXCHANGE, ROUTING_KEY, null, message.getBytes());
        channel.basicPublish(NAME_EXCHANGE, ROUTING_KEY_SECOND, null, secondMessage.getBytes());

        System.out.println("[x] Sent '" + message+"'");
        System.out.println("[x] Sent '" + secondMessage+"'");
    }
}
