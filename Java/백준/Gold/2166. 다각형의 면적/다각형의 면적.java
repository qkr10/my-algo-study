import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[][] points = new int[N][];
        for (int i = 0; i < N; i++) {
            String[] line = br.readLine().split(" ");
            points[i] = Arrays.stream(line)
                .mapToInt(Integer::parseInt)
                .toArray();
        }
        
        int[] origin = points[0];
        points[1][0] -= origin[0];
        points[1][1] -= origin[1];
        long result = 0;
        for (int i = 1; i < N-1; i++) {
            int[] p1 = points[i];
            int[] p2 = points[i+1];
            p2[0] -= origin[0];
            p2[1] -= origin[1];
            
            result += ((long)p1[0])*p2[1] - ((long)p1[1])*p2[0];
        }
        System.out.printf("%.1f", Math.abs(result / 2.));
    }
}