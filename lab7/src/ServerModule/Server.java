package ServerModule;

import ServerModule.util.RequestManager;
import ServerModule.util.RequestReceivingThread;
import ServerModule.util.ResponseSenderThread;
import common.util.Request;
import common.util.Response;
import common.util.ResponseCode;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class Server {
    private int port;
    private RequestManager requestManager;
    private InetSocketAddress address;
    private ServerSocketChannel serverSocketChannel;
    private Scanner scanner;

    private ForkJoinPool forkJoinPool = new ForkJoinPool(5);

    private Request request;
    private Response response;

    public Server(int port, RequestManager requestManager) throws IOException {
        this.port = port;
        this.requestManager = requestManager;
        checkInput();
    }

    private Response executeRequest(Request request) {
        return requestManager.manage(request);
    }

    public void run() throws IOException {
        System.out.println("Сервер запущен.");
        address = new InetSocketAddress(port);
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(address);
        while (true) {
            try {
                SocketChannel socketChannel = serverSocketChannel.accept();
                RequestReceivingThread readThread = new RequestReceivingThread(requestManager, request, socketChannel);
                readThread.start();
                try {
                    readThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                request = readThread.getRequest();
                forkJoinPool.submit(() -> {
                    response = requestManager.manage(request);
                }).get();
                ResponseSenderThread sendThread = new ResponseSenderThread(response, socketChannel);
                sendThread.start();
                sendThread.join();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                System.out.println("При чтении запроса произошла ошибка многопоточности!");
            }
        }
    }

    /**
     * Метод, запускающий новый поток для реализациии особых серверных комманд.
     */
    private void checkInput() {
        scanner = new Scanner(System.in);
        Runnable userInput = () -> {
            try {
                while (true) {
                    String[] userCommand = (scanner.nextLine().trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                    if (userCommand[0].equals("exit")) {
                        System.out.println("Сервер заканчивает работу!");
                        System.exit(0);
                    }

                }
            } catch (Exception ignored) {
            }
        };
        Thread thread = new Thread(userInput);
        thread.start();
    }
}

