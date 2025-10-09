package br.uam.clientserverrsa;

import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.Scanner;

public class Server {
    private static final int PORT = 12345;

    public static void main(String[] args) {
        // Valores RSA fornecidos pelo usuário
        BigInteger p = new BigInteger("17");
        BigInteger q = new BigInteger("23");
        BigInteger n = new BigInteger("391"); // p * q
        BigInteger e = new BigInteger("3");   // Expoente público
        BigInteger d = new BigInteger("235");  // Expoente privado
        BigInteger phiN = new BigInteger("352"); // (p-1)*(q-1)

        RSA serverRSA = new RSA(n, e, d);

        System.out.println("Servidor iniciado na porta " + PORT);
        System.out.println("Chaves RSA do Servidor:");
        System.out.println("  n (Módulo): " + serverRSA.getN());
        System.out.println("  e (Expoente Público): " + serverRSA.getE());
        System.out.println("  d (Expoente Privado): " + serverRSA.getD());

        try (ConexaoTCP conexao = new ConexaoTCP(PORT)) {

            // 1. Troca de chaves públicas
            // Servidor envia sua chave pública (n, e) para o cliente
            conexao.enviar(serverRSA.getN().toString());
            conexao.enviar(serverRSA.getE().toString());
            System.out.println("Chave pública do servidor enviada ao cliente.");

            // Cliente envia sua chave pública (n_client, e_client) para o servidor
            BigInteger clientN = new BigInteger(conexao.receber());
            BigInteger clientE = new BigInteger(conexao.receber());
            RSA clientRSAForServer = new RSA(clientN, clientE, null); // Servidor só precisa da chave pública do cliente para criptografar para ele
            System.out.println("Chave pública do cliente recebida: n=" + clientN + ", e=" + clientE);

            // 2. Comunicação de dados criptografados
            Scanner scanner = new Scanner(System.in);
            String clientMessage;
            String serverResponse;

            while (true) {
                // Recebe mensagem criptografada do cliente
                clientMessage = conexao.receber();
                if (clientMessage == null || clientMessage.equalsIgnoreCase("exit")) {
                    System.out.println("Cliente desconectou.");
                    break;
                }
                System.out.println("Mensagem criptografada do cliente: " + clientMessage);
                String decryptedClientMessage = serverRSA.decrypt(clientMessage);
                System.out.println("Mensagem decifrada do cliente: " + decryptedClientMessage);

                // Envia resposta criptografada para o cliente
                serverResponse = "Mensagem recebida com sucesso!";
                String encryptedServerResponse = clientRSAForServer.encrypt(serverResponse);
                conexao.enviar(encryptedServerResponse);
                System.out.println("Resposta criptografada do servidor enviada: " + encryptedServerResponse);
            }

            scanner.close();
        } catch (IOException ex) {
            System.err.println("Erro no servidor: " + ex.getMessage());
        }
    }
}

