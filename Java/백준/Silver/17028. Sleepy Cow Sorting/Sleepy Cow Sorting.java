import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] arr = Arrays.stream(br.readLine().split(" "))
            .mapToInt(Integer::parseInt).toArray();
        int i;
        for (i = N - 1; i > 0; i--)
            if (arr[i] < arr[i-1]) {
                System.out.println(i);
                break;
            }
        if (i == 0)
            System.out.println(i);
    }
}