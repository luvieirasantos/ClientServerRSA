package br.uam.clientserverrsa;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RSA {
    private BigInteger n; // Módulo
    private BigInteger e; // Expoente público
    private BigInteger d; // Expoente privado

    public RSA(BigInteger n, BigInteger e, BigInteger d) {
        this.n = n;
        this.e = e;
        this.d = d;
    }

    public String encrypt(String message) {
        byte[] msgBytes = message.getBytes(StandardCharsets.UTF_8);
        StringBuilder encryptedStringBuilder = new StringBuilder();
        for (byte b : msgBytes) {
            // Ensure byte is treated as unsigned for BigInteger conversion
            BigInteger msgBigInt = BigInteger.valueOf(b & 0xFF);
            BigInteger encrypted = msgBigInt.modPow(e, n);
            encryptedStringBuilder.append(encrypted).append(" ");
        }
        return encryptedStringBuilder.toString().trim();
    }

    public String decrypt(String encryptedMessage) {
        String[] encryptedParts = encryptedMessage.split(" ");
        List<Byte> decryptedBytes = new ArrayList<>();
        for (String part : encryptedParts) {
            BigInteger encryptedBigInt = new BigInteger(part);
            BigInteger decrypted = encryptedBigInt.modPow(d, n);
            decryptedBytes.add(decrypted.byteValue());
        }
        byte[] resultBytes = new byte[decryptedBytes.size()];
        for (int i = 0; i < decryptedBytes.size(); i++) {
            resultBytes[i] = decryptedBytes.get(i);
        }
        return new String(resultBytes, StandardCharsets.UTF_8);
    }

    public BigInteger getN() {
        return n;
    }

    public BigInteger getE() {
        return e;
    }

    public BigInteger getD() {
        return d;
    }
}
