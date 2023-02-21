package com.example.demo.DLX;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Vector;

public class SenderDlx {


    private static String NAME_EXCHANGE = "mainExchange";

    private static String TYPE_CHANGE = "topic";

    private static String ROUTING_KEY = "bkConsumer.test1";


    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("172.22.0.2");
        factory.setUsername("admin");
        factory.setPassword("pass123");
        factory.setPort(5672);

        Connection connection = factory.newConnection();
        System.out.println("Conecction  -> " + connection.hashCode());

        //criar um novo canal
        Channel channel = connection.createChannel();
        AMQP.Confirm.SelectOk selectOk = channel.confirmSelect();

        System.out.println(selectOk);

        channel.exchangeDeclare(NAME_EXCHANGE, TYPE_CHANGE);

        // criar a msg
        String message ="This is a test!";
        // enviar a mensagem

        channel.basicPublish(NAME_EXCHANGE, ROUTING_KEY, null, message.getBytes());


        System.out.println("Done!!");



    }
}
