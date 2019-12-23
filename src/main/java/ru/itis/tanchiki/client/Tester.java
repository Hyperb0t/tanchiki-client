package ru.itis.tanchiki.client;


import java.io.IOException;

public class Tester {
    public static void main(String[] args) throws InterruptedException, IOException {
        Client client = new Client();
        client.start();
    }
}
