package com.example.demo.PuConfirmation;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class SenderSecondPubConfirmation {


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
        String message = "This is my ";
        int setOfMessages = 10;
        int outMessages = 0;
        String bodyMessage = null;

        // enviar a mensagem
        for (int i = 0; i < setOfMessages; i++) {
            bodyMessage = message + i;
            channel.basicPublish(NAME_EXCHANGE, "", null, bodyMessage.getBytes());
            System.out.println("Sent the menssage" + bodyMessage);
            outMessages++;

            if (outMessages == setOfMessages) {
                channel.waitForConfirmsOrDie(5_000);
                System.out.println("[x] Message confirmed");
                outMessages = 0;
            }

        }

        if (outMessages != 0) {
            System.out.println(bodyMessage);
            channel.waitForConfirmsOrDie(5_000);
            System.out.println("[x] Message confirmed");
        }
        System.out.println("Done!!");


    }
}
