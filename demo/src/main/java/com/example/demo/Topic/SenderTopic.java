package com.example.demo.Topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class SenderTopic {


    private static  String NAME_EXCHANGE ="topicExchange";

    private static String TYPE_EXCHANGE ="topic";

    private static String ROUTING_KEY ="quick.orange.rabbit";

    private static String ROUTING_KEY_2 ="quick.rabbit";

    private static String ROUTING_KEY_3 ="rabbit.orange";

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

        // criar a msg
        String message = "Hello!! This is a RabbitMQ system !";
        String secondMessage ="Hello! this is the message with the routing key";
        String message3 = "The last message end to send";

        // enviar a mensagem

        channel.basicPublish(NAME_EXCHANGE, ROUTING_KEY, null, message.getBytes());
        channel.basicPublish(NAME_EXCHANGE, ROUTING_KEY_2, null, secondMessage.getBytes());
        channel.basicPublish(NAME_EXCHANGE, ROUTING_KEY_3, null, message3.getBytes());

        System.out.println("[x] Sent '" + message+"'");
        System.out.println("[x] Sent '" + secondMessage+"'");
        System.out.println("[x] Sent '" + message3+"'");
    }
}
