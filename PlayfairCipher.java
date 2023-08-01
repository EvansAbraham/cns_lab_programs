import java.util.Scanner;

public class PlayfairCipher {
    private static final int MATRIX_SIZE = 5;

    public static String prepareKey(String key) {
        StringBuilder preparedKey = new StringBuilder();
        boolean[] visited = new boolean[26];

        key = key.toUpperCase().replaceAll("[^A-Z]", "");

        for (char ch : key.toCharArray()) {
            if (!visited[ch - 'A']) {
                preparedKey.append(ch);
                visited[ch - 'A'] = true;
            }
        }

        for (char ch = 'A'; ch <= 'Z'; ch++) {
            if (ch != 'J' && !visited[ch - 'A']) {
                preparedKey.append(ch);
            }
        }

        return preparedKey.toString();
    }

    public static char[][] generateMatrix(String key) {
        char[][] matrix = new char[MATRIX_SIZE][MATRIX_SIZE];
        String preparedKey = prepareKey(key);
        int k = 0;

        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                matrix[i][j] = preparedKey.charAt(k);
                k++;
            }
        }

        return matrix;
    }

    public static String encrypt(String plaintext, String key) {
        char[][] matrix = generateMatrix(key.toUpperCase());
        StringBuilder encryptedText = new StringBuilder();

        plaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", "");
        plaintext = plaintext.replace('J', 'I');

        for (int i = 0; i < plaintext.length(); i += 2) {
            char ch1 = plaintext.charAt(i);
            char ch2 = (i + 1 < plaintext.length()) ? plaintext.charAt(i + 1) : 'X';

            int[] pos1 = findPosition(matrix, ch1);
            int[] pos2 = findPosition(matrix, ch2);

            int row1 = pos1[0];
            int col1 = pos1[1];
            int row2 = pos2[0];
            int col2 = pos2[1];

            if (row1 == row2) {
                col1 = (col1 + 1) % MATRIX_SIZE;
                col2 = (col2 + 1) % MATRIX_SIZE;
            } else if (col1 == col2) {
                row1 = (row1 + 1) % MATRIX_SIZE;
                row2 = (row2 + 1) % MATRIX_SIZE;
            } else {
                int temp = col1;
                col1 = col2;
                col2 = temp;
            }

            encryptedText.append(matrix[row1][col1]);
            encryptedText.append(matrix[row2][col2]);
        }

        return encryptedText.toString();
    }

    public static String decrypt(String ciphertext, String key) {
        char[][] matrix = generateMatrix(key.toUpperCase());
        StringBuilder decryptedText = new StringBuilder();

        for (int i = 0; i < ciphertext.length(); i += 2) {
            char ch1 = ciphertext.charAt(i);
            char ch2 = (i + 1 < ciphertext.length()) ? ciphertext.charAt(i + 1) : 'X';

            int[] pos1 = findPosition(matrix, ch1);
            int[] pos2 = findPosition(matrix, ch2);

            int row1 = pos1[0];
            int col1 = pos1[1];
            int row2 = pos2[0];
            int col2 = pos2[1];

            if (row1 == row2) {
                col1 = (col1 - 1 + MATRIX_SIZE) % MATRIX_SIZE;
                col2 = (col2 - 1 + MATRIX_SIZE) % MATRIX_SIZE;
            } else if (col1 == col2) {
                row1 = (row1 - 1 + MATRIX_SIZE) % MATRIX_SIZE;
                row2 = (row2 - 1 + MATRIX_SIZE) % MATRIX_SIZE;
            } else {
                int temp = col1;
                col1 = col2;
                col2 = temp;
            }

            decryptedText.append(matrix[row1][col1]);
            decryptedText.append(matrix[row2][col2]);
        }

        return decryptedText.toString();
    }

    public static int[] findPosition(char[][] matrix, char ch) {
        int[] position = new int[2];
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                if (matrix[i][j] == ch) {
                    position[0] = i;
                    position[1] = j;
                    return position;
                }
            }
        }
        return position;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the plaintext: ");
        String plaintext = scanner.nextLine();

        System.out.print("Enter the key: ");
        String key = scanner.nextLine();

        String encryptedText = encrypt(plaintext, key);
        String decryptedText = decrypt(encryptedText, key);

        System.out.println("Encrypted Text: " + encryptedText);
        System.out.println("Decrypted Text: " + decryptedText);

        scanner.close();
    }
}
