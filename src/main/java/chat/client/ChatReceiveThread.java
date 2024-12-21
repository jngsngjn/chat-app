package chat.client;

import lombok.SneakyThrows;

import java.io.DataInputStream;

public class ChatReceiveThread implements Runnable {

    private final DataInputStream input;

    public ChatReceiveThread(DataInputStream input) {
        this.input = input;
    }

    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            System.out.println(input.readUTF());
        }
    }
}