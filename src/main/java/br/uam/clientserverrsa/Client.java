package br.uam.clientserverrsa;

import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.Scanner;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 12345;

    public static void main(String[] args) {
        // Valores RSA do cliente (podem ser os mesmos do servidor para este exemplo)
        BigInteger p = new BigInteger("17");
        BigInteger q = new BigInteger("23");
        BigInteger n = new BigInteger("391"); // p * q
        BigInteger e = new BigInteger("3");   // Expoente público
        BigInteger d = new BigInteger("235");  // Expoente privado
        BigInteger phiN = new BigInteger("352"); // (p-1)*(q-1)

        RSA clientRSA = new RSA(n, e, d);

        System.out.println("Cliente iniciado.");
        System.out.println("Chaves RSA do Cliente:");
        System.out.println("  n (Módulo): " + clientRSA.getN());
        System.out.println("  e (Expoente Público): " + clientRSA.getE());
        System.out.println("  d (Expoente Privado): " + clientRSA.getD());

        try (Socket socket = new Socket(SERVER_ADDRESS, PORT)) {
            System.out.println("Conectado ao servidor em " + SERVER_ADDRESS + ":" + PORT);

            // Streams para comunicação
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // 1. Troca de chaves públicas
            // Cliente recebe a chave pública (n, e) do servidor
            BigInteger serverN = new BigInteger(in.readLine());
            BigInteger serverE = new BigInteger(in.readLine());
            RSA serverRSAForClient = new RSA(serverN, serverE, null); // Cliente só precisa da chave pública do servidor para criptografar para ele
            System.out.println("Chave pública do servidor recebida: n=" + serverN + ", e=" + serverE);

            // Cliente envia sua chave pública (n, e) para o servidor
            // out.println(clientRSA.getN().toString());
            // out.println(clientRSA.getE().toString());
            // System.out.println("Chave pública do cliente enviada ao servidor.");

            // 2. Comunicação de dados criptografados
            Scanner scanner = new Scanner(System.in);
            String clientMessage;
            String serverResponse;

            while (true) {
                System.out.print("Digite sua mensagem para o servidor (ou 'exit' para sair): ");
                clientMessage = scanner.nextLine();

                if (clientMessage.equalsIgnoreCase("exit")) {
                    out.println("exit");
                    break;
                }

                // Envia mensagem criptografada para o servidor
                String encryptedClientMessage = serverRSAForClient.encrypt(clientMessage);
                out.println(encryptedClientMessage);
                System.out.println("Mensagem criptografada do cliente enviada: " + encryptedClientMessage);

                // Recebe resposta criptografada do servidor
                serverResponse = in.readLine();
                System.out.println("Resposta criptografada do servidor: " + serverResponse);
                String decryptedServerResponse = clientRSA.decrypt(serverResponse);
                System.out.println("Resposta decifrada do servidor: " + decryptedServerResponse);
            }

            scanner.close();
        } catch (IOException ex) {
            System.err.println("Erro no cliente: " + ex.getMessage());
        }
    }
}

