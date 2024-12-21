package chat.client;

import lombok.Setter;
import lombok.SneakyThrows;

import java.io.DataInputStream;

@Setter
public class ChatReceiveThread implements Runnable {

    private final DataInputStream input;
    private boolean running = true;

    public ChatReceiveThread(DataInputStream input) {
        this.input = input;
    }

    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            if (running) {
                System.out.println(input.readUTF());
            }
        }
    }
}