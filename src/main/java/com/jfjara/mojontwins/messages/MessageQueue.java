package com.jfjara.mojontwins.messages;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class MessageQueue {

    private static MessageQueue instance;
    private List<String> messages;

    private MessageQueue() {
        messages = new LinkedList<>();
    }

    public static MessageQueue getInstance() {
        if (instance == null) {
            instance = new MessageQueue();
        }
        return instance;
    }

    public void enqueue(String message) {
        messages.add(message);
    }

    public String extract() {
        if (messages.isEmpty()) {
            return null;
        }
        String msg = messages.get(0);
        messages.remove(msg);
        return msg;
    }

    public void showMessages() {
        while (!messages.isEmpty()) {
            String m = extract();
            System.out.println(m);
        }
    }

    public boolean hasMessages() {
        return !messages.isEmpty();
    }


}
