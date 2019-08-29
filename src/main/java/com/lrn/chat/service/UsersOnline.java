package com.lrn.chat.service;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UsersOnline {
    private Map<String, String> users = new ConcurrentHashMap<>();

    public Map<String, String> getUsers() {
        return users;
    }
}
