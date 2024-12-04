package com.delsorbo;

import java.math.BigInteger;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Scanner;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger();
    
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

        System.out.print("Inserisci il messaggio che vuoi cryptare");
		String msg = scan.nextLine();
		RSA_KEYgen(msg);

        scan.close();
	}

	public static void RSA_KEYgen(String msg){
		BigInteger p, q, n, v, e, d;
        int bitLenght = 2048;
		Random ran = new Random();

		p = BigInteger.probablePrime(bitLenght, ran);
		q = BigInteger.probablePrime(bitLenght, ran);

		n = p.multiply(q);
		v = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

		do {
			e = BigInteger.probablePrime(bitLenght, ran);
		} while(!e.gcd(v).equals(BigInteger.ONE));

		d = e.modInverse(v);


		String encryptedMSG = Encrypt(n, e, msg);
		System.out.println("Messaggio criptato: " + encryptedMSG);

		String decryptedMsg = Decrypt(n, d, encryptedMSG);
        System.out.println("Messaggio decifrato: " + decryptedMsg);

	}

	public static String Encrypt(BigInteger n, BigInteger e, String msg) {
        StringBuilder encryptedMessage = new StringBuilder();

        for (int i = 0; i < msg.length(); i++) {
            int asciiLetter = (int) msg.charAt(i);
            BigInteger M = BigInteger.valueOf(asciiLetter);
            BigInteger encryptedChar = M.modPow(e, n);

            encryptedMessage.append(encryptedChar.toString()).append(" ");
        }

        return encryptedMessage.toString().trim();
    }

    public static String Decrypt(BigInteger n, BigInteger d, String encryptedMsg) {
        StringBuilder decryptedMessage = new StringBuilder();

        StringTokenizer tokenizer = new StringTokenizer(encryptedMsg, " ");

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();

            BigInteger C = new BigInteger(token); 
            BigInteger decryptedValue = C.modPow(d, n);

            char decryptedChar = (char) decryptedValue.intValue();
            decryptedMessage.append(decryptedChar);
        }

        return decryptedMessage.toString();
    }
}