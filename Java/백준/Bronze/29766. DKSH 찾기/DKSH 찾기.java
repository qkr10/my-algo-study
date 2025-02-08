import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        int result = 0;
        int currentIndex = line.indexOf("DKSH");
        while (currentIndex != -1) {
            result++;
            currentIndex = line.indexOf("DKSH", currentIndex+1);
        }
        System.out.println(result);
    }
}