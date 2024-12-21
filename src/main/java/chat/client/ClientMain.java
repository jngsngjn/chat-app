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

    public static void main(String[] args) {
        Thread thread = null;
        ChatReceiveThread chatReceiveThread = null;

        try (Socket socket = new Socket(HOST, SERVER_PORT);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream());
             Scanner sc = new Scanner(System.in)) {

            nameInit(sc, output, input);
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

                if (command.equals(CHANGE)) {
                    nameChange(sc, output, input);
                }

                if (command.equals(JOIN)) {
                    output.writeUTF(JOIN);
                    System.out.println("채팅방에 입장했습니다.");
                    if (thread == null) {
                        chatReceiveThread = new ChatReceiveThread(input);
                        thread = new Thread(chatReceiveThread);
                        thread.start();
                    } else {
                        chatReceiveThread.setRunning(true);
                    }
                    chattingMod(output, sc, chatReceiveThread);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private static void chattingMod(DataOutputStream output, Scanner sc, ChatReceiveThread chatReceiveThread) throws IOException {
        while (true) {
            String msg = sc.nextLine();
            if (!msg.isEmpty()) {
                if (msg.equals(EXIT)) {
                    System.out.println("채팅을 종료합니다.");
                    chatReceiveThread.setRunning(false);
                    output.writeUTF(EXIT);
                    break;
                }
                output.writeUTF(msg);
            }
        }
    }

    private static void nameInit(Scanner sc, DataOutputStream output, DataInputStream input) throws IOException {
        while (true) {
            System.out.print(ENTER_NAME);
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

    private static void nameChange(Scanner sc, DataOutputStream output, DataInputStream input) throws IOException {
        System.out.print(ENTER_NAME);
        String newName = sc.nextLine();

        if (!newName.isEmpty()) {
            if (newName.equals(name)) {
                System.out.println("현재 사용 중인 이름과 같습니다.");
                return;
            }

            output.writeUTF(CHANGE);
            output.writeUTF(newName);

            if (input.readInt() == DUPLICATE_NAME) {
                System.out.println("현재 사용 중인 이름입니다.");
                return;
            }

            name = newName;
            System.out.println("이름 변경이 완료되었습니다.");
            Thread.currentThread().setName(name);
        }
    }
}