package chat;

import chat.manager.ClientManager;
import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import static chat.config.ChatConst.*;

// TODO 이름을 서버에서 제공해야 함
@Slf4j
public class ClientMain {

    public static String NAME = "";
    public static ClientManager clientManager = ClientManager.getInstance();

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket(HOST, SERVER_PORT)) {
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.print("이름을 입력하세요: ");
                NAME = sc.nextLine();
                if (!NAME.isEmpty()) {
                    clientManager.add(NAME);
                    break;
                }
            }

            System.out.println(WELCOME_MSG);
            while (true) {
                String command = sc.nextLine();
                if (command.equals(EXIT)) {
                    return;
                }

                if (command.equals(USERS)) {
                    System.out.println(clientManager.allUsers());
                }
            }
        }
    }
}