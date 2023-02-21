package com.example.demo.DLX;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class DlxConfig {

    private static final String DLX_NAME = "dlxExchange";
    private static final String DLX_QUEUE = "dlxQueue";
    private static final String DLX_BINDING_KEY = "dlxRk";

    //EXCHANGE do sistema
    private static final String EXCHANGE_NAME = "mainExchange";

    //CONSUMER
    private static final String CONSUMER_QUEUE = "queueConsumer";
    private static final String CONSUMER_BINDING_KEY = "bkConsumer";


    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("172.22.0.2");
        factory.setUsername("admin");
        factory.setPassword("pass123");
        factory.setPort(5672);

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        //Declarar as exchanges (main e dlx)

        channel.exchangeDeclare(DLX_NAME, "topic");
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        //Declarar as filas consumer e dlx
        channel.queueDeclare(DLX_QUEUE, false, false, false, null);


        Map<String, Object> map = new HashMap<String, Object>();
        map.put("x-message-ttl", 10000);
        map.put("x-dead-letter-exchange", DLX_NAME);
        map.put("x-dead-letter-routing-key", DLX_BINDING_KEY);
        channel.queueDeclare(CONSUMER_QUEUE, false, false, false, map);

        // BindingKey da dlx e consumer

        channel.queueBind(DLX_QUEUE, DLX_NAME, DLX_BINDING_KEY+".#");
        channel.queueBind(CONSUMER_QUEUE, EXCHANGE_NAME, CONSUMER_BINDING_KEY+".#");

        connection.close();


    }
}
