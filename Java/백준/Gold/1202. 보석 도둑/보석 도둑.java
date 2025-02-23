import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        var st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int[] bags = new int[K];
        int[][] jewels = new int[N][2];
        
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            jewels[i][0] = Integer.parseInt(st.nextToken());
            jewels[i][1] = Integer.parseInt(st.nextToken());
        }
        for (int i = 0; i < K; i++) {
            bags[i] = Integer.parseInt(br.readLine());
        }
        Arrays.sort(jewels, (a, b)->a[0]-b[0]);
        Arrays.sort(bags);
        
        PriorityQueue<int[]> q = new PriorityQueue<>((a, b)->b[1]-a[1]);
        int j = 0;
        long ans = 0;
        for (int b = 0; b < K; b++) {
            while (j < N && jewels[j][0] <= bags[b])
                q.add(jewels[j++]);
            if (q.isEmpty())
                continue;
            ans += q.poll()[1];
        }
        System.out.println(ans);
    }
}