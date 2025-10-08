package br.uam.clientserverrsa;

import java.io.*;
import java.net.*;

public class ConexaoTCP implements AutoCloseable {
    private Socket socket;
    private ServerSocket serverSocket;
    private BufferedReader in;
    private PrintWriter out;

    // Constructor for client
    public ConexaoTCP(String host, int port) throws IOException {
        socket = new Socket(host, port);
        System.out.println("Conectado ao servidor em " + host + ":" + port);
        setupStreams();
    }

    // Constructor for server
    public ConexaoTCP(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Servidor iniciado na porta " + port);
        System.out.println("Aguardando conexão do cliente...");
        socket = serverSocket.accept();
        System.out.println("Cliente conectado: " + socket.getInetAddress().getHostAddress());
        setupStreams();
    }

    private void setupStreams() throws IOException {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void enviar(String msg) {
        out.println(msg);
        System.out.println("Mensagem enviada: " + msg);
    }

    public String receber() throws IOException {
        String msg = in.readLine();
        System.out.println("Mensagem recebida: " + msg);
        return msg;
    }

    public void fechar() throws IOException {
        if (in != null) in.close();
        if (out != null) out.close();
        if (socket != null) socket.close();
        if (serverSocket != null) serverSocket.close();
        System.out.println("Conexão fechada.");
    }

    @Override
    public void close() throws IOException {
        fechar();
    }
}