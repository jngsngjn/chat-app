package chat;

import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import static chat.config.ChatConst.*;

/**
 * 클라이언트 메인 클래스
 * - 서버에 연결하여 사용자 이름 등록 및 명령어를 서버에 전송
 */
@Slf4j
public class ClientMain {

    public static String name = "";

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket(HOST, SERVER_PORT)) {
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.print("이름을 입력하세요: ");
                name = sc.nextLine();
                if (!name.isEmpty()) {
                    output.writeUTF(name);
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
                    output.writeUTF(USERS);
                    String usernames = input.readUTF();
                    System.out.println("현재 사용자 목록: " + usernames);
                }
            }
        }
    }
}