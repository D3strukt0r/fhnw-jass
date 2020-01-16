package org.orbitrondev.server;

import org.orbitrondev.lib.Message;

public class Main {
    public static void main(String[] args) {
        Message m = new Message("Hello, I'm the server!");
        System.out.println(m.getMessage());
    }
}
