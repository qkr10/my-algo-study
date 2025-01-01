import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        for (int t = 0; t < T; t++) {
            int N = Integer.parseInt(br.readLine());
            double sum = 0;
            for (int n = 0; n < N; n++) {
                String[] line = br.readLine().split(" ");
                int[] arr = Arrays.stream(line).mapToInt(Integer::parseInt).toArray();
                double a = Math.atan2(arr[1], arr[0]);
                double b = Math.atan2(arr[3], arr[2]);
                double diff = Math.abs(a - b);
                if (diff > Math.PI)
                    diff = Math.PI * 2 - diff;
                sum += diff;
            }
            System.out.printf("%.5f\n", sum / Math.PI / 2);
        }
    }
}