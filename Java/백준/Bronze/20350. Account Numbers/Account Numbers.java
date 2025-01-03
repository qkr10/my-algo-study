import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();
        input = input.substring(4) + input.substring(0, 4);
        int d = 1, res = 0;
        for (int i = input.length() - 1; i >= 0; i--) {
            char c = input.charAt(i);
            int current = 0;
            if ('0' <= c && c <= '9') {
                current = c - '0';
                res = (res + current * d) % 97;
            }
            else if ('A' <= c && c <= 'Z'){
                current = c - 'A' + 10;
                res = (res + current * d) % 97;
                d *= 10;
            }
            d = (d * 10) % 97;
        }
        if (res == 1)
            System.out.println("correct");
        else
            System.out.println("incorrect");
    }
}