package com.example.demo.WorkQueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class SenderWork {

    private static  String NAME_QUEUE ="WORK";

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

        // declarar a fila que será utilizada
        // declarar a fila que será utilizada
        // nome da fila, exclusiva, autodelete, duerable, map(args)
        channel.queueDeclare(NAME_QUEUE, false, false, false, null);

        // criar a msg

        String message = ".............";

        // enviar a mensagem

        channel.basicPublish("", NAME_QUEUE, null, message.getBytes());

        System.out.println("[x] Sent '" + message+"'");
    }
}
