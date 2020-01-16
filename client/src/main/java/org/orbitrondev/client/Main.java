package org.orbitrondev.client;

import org.orbitrondev.lib.Message;

public class Main {
    public static void main(String[] args) {
        Message m = new Message("Hello, I'm the client!");
        System.out.println(m.getMessage());
    }
}
