package com.example.demo.PuConfirmation;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Vector;

public class SenderPubConfirmation {


    private static String NAME_EXCHANGE = "fanoutExchange";

    private static String TYPE_CHANGE = "fanout";


    public static void main(String[] args) throws Exception {

        // primeiro criar a conexão
        //setar as informações para cria-la
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("172.22.0.2");
        factory.setUsername("admin");
        factory.setPassword("pass123");
        factory.setPort(5672);

        System.out.println("User: " + factory.getUsername());
        System.out.println("Passwoard: " + factory.getPassword());

        Connection connection = factory.newConnection();
        System.out.println("Conecction  -> " + connection.hashCode());

        //criar um novo canal
        Channel channel = connection.createChannel();
        AMQP.Confirm.SelectOk selectOk = channel.confirmSelect();

        System.out.println(selectOk);

        channel.exchangeDeclare(NAME_EXCHANGE, TYPE_CHANGE);

        // criar a msg


        Vector<String> message= new Vector<String>(3);
        message.add("Hello world");
        message.add("This is sencond message");
        message.add("This is the final message");

        // enviar a mensagem
        for (int i =0; i<3; i++){
            String body = message.get(i);
            channel.basicPublish(NAME_EXCHANGE, "", null, body.getBytes());
            System.out.printf("{i} -- Sent '" + body + "'",i);

            channel.waitForConfirmsOrDie(5_000);
            System.out.println("[x] Message confirmed");
        }
        System.out.println("Done!!");



    }
}
