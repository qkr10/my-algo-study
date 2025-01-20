import java.util.*;
import java.io.*;

public class Main{
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        var sb = new StringBuilder();
        while (true) {
            var st = new StringTokenizer(br.readLine());
            int R = Integer.parseInt(st.nextToken());
            int N = Integer.parseInt(st.nextToken());
            if (R == -1)
                break;
            
            int[] pos = new int[N];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++)
                pos[i] = Integer.parseInt(st.nextToken());
            Arrays.sort(pos);
            
            int cur = 0, p1 = 0, result = 0;
            while (p1 != N) {
                while (cur+1 < N && pos[cur+1] - pos[p1] <= R)
                    cur++;
                p1 = cur+1;
                while (p1 < N && pos[p1] - pos[cur] <= R)
                    p1++;
                result++;
            }
            sb.append(result).append("\n");
        }
        System.out.print(sb.toString());
    }
}