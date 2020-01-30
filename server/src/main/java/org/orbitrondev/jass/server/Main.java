package org.orbitrondev.jass.server;

import org.orbitrondev.jass.lib.Message;

public class Main {
    public static void main(String[] args) {
        Message m = new Message("Hello, I'm the server!");
        System.out.println(m.getMessage());
    }
}
