import java.util.Scanner;

public class CaesarCipher {
    public static String encrypt(String plainText, int shift) {
        StringBuilder encryptedText = new StringBuilder();
        for (int i = 0; i < plainText.length(); i++) {
            char ch = plainText.charAt(i);
            if (Character.isLetter(ch)) {
                char base = (Character.isUpperCase(ch)) ? 'A' : 'a';
                ch = (char) ((ch - base + shift) % 26 + base);
            }
            encryptedText.append(ch);
        }
        return encryptedText.toString();
    }

    public static String decrypt(String encryptedText, int shift) {
        StringBuilder decryptedText = new StringBuilder();
        for (int i = 0; i < encryptedText.length(); i++) {
            char ch = encryptedText.charAt(i);
            if (Character.isLetter(ch)) {
                char base = (Character.isUpperCase(ch)) ? 'A' : 'a';
                ch = (char) ((ch - base - shift + 26) % 26 + base);
            }
            decryptedText.append(ch);
        }
        return decryptedText.toString();
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the plain text: ");
            String plainText = scanner.nextLine();

            int shift = 3; // You can modify the shift value as needed.

            String encryptedText = encrypt(plainText, shift);
            String decryptedText = decrypt(encryptedText, shift);

            System.out.println("Encrypted Text: " + encryptedText);
            System.out.println("Decrypted Text: " + decryptedText);
        }
    }
}
