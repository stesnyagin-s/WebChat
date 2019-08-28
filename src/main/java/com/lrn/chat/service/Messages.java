package com.lrn.chat.service;

import org.springframework.stereotype.Repository;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Repository
public class Messages {
    private Queue<String> messages = new ConcurrentLinkedQueue<>();

    public Queue<String> getMessages() {
        return messages;
    }
}
