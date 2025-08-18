package com.balazsholczer.serviceactivator;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageQueue {
    
    private final BlockingQueue<Message> queue;
    private final ServiceActivator serviceActivator;
    private volatile boolean running = false;
    
    public MessageQueue(ServiceActivator serviceActivator) {
        this.queue = new LinkedBlockingQueue<>();
        this.serviceActivator = serviceActivator;
        System.out.println("MessageQueue: Created message queue");
    }
    
    public void sendMessage(Message message) {
        try {
            queue.put(message);
            System.out.println("MessageQueue: Queued message - " + message.getId());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("MessageQueue: Interrupted while queuing message");
        }
    }
    
    public void startProcessing() {
        running = true;
        Thread processingThread = new Thread(() -> {
            System.out.println("MessageQueue: Started message processing thread");
            
            while (running) {
                try {
                    Message message = queue.take();
                    System.out.println("MessageQueue: Dequeued message - " + message.getId());
                    serviceActivator.activateService(message);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            
            System.out.println("MessageQueue: Message processing thread stopped");
        });
        
        processingThread.setDaemon(true);
        processingThread.start();
    }
    
    public void stopProcessing() {
        running = false;
        System.out.println("MessageQueue: Stopping message processing");
    }
    
    public int getQueueSize() {
        return queue.size();
    }
}