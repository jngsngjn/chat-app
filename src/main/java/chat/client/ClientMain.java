package chat.client;

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
        try (Socket socket = new Socket(HOST, SERVER_PORT);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

            Scanner sc = new Scanner(System.in);

            nameProcess(sc, output, input);
            System.out.println(WELCOME_MSG);

            while (true) {
                String command = sc.nextLine();
                if (command.equals(EXIT)) {
                    output.writeUTF(EXIT);
                    return;
                }

                if (command.equals(USERS)) {
                    output.writeUTF(USERS);
                    String usernames = input.readUTF();
                    // TODO 연결만 맺고 이름을 입력하지 않은 클라이언트가 있을 때 빈 문자열 출력되는 이슈
                    System.out.println("현재 사용자 목록: " + usernames);
                }
            }
        }
    }

    private static void nameProcess(Scanner sc, DataOutputStream output, DataInputStream input) throws IOException {
        while (true) {
            System.out.print("이름을 입력하세요: ");
            name = sc.nextLine();
            if (!name.isEmpty()) {
                output.writeUTF(name);
                if (input.readInt() == DUPLICATE_NAME) {
                    System.out.println("현재 사용 중인 이름입니다.");
                    continue;
                }
                Thread.currentThread().setName(name);
                break;
            }
        }
    }
}