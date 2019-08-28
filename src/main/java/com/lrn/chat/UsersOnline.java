package com.lrn.chat;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Repository
public class UsersOnline {
    private Map<String, String> users = new ConcurrentHashMap<>();

    public Map<String, String> getUsers() {
        return users;
    }
}
