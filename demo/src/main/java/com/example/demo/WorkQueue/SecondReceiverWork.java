package com.example.demo.WorkQueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

public class SecondReceiverWork {

    private static  String NAME_QUEUE ="WORK";


    private static void doWork(String task) throws InterruptedException {
        for(char ch: task.toCharArray()){
            if(ch == '.') Thread.sleep(1000);
        }
    }

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

        // declarar a fila que será utilizada
        // declarar a fila que será utilizada
        // nome da fila, exclusiva, autodelete, duerable, map(args)
        channel.queueDeclare(NAME_QUEUE, false, false, false, null);

        DeliverCallback deliverCallback = (ConsumerTag, delivery)->{
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("[x] Received message '" + message+"'");

            try{
                try {
                    doWork(message);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }finally {
                System.out.println("[x] Done");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        // receber a mensagem

        boolean autoAck = false; //ack is true/on
        channel.basicConsume(NAME_QUEUE,autoAck, deliverCallback, ConsumerTagStrategy ->{});


    }
}
