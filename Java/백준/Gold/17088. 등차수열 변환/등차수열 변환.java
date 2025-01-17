import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        var st = new StringTokenizer(br.readLine());
        int[] input = new int[N];
        int i = 0;
        while (st.hasMoreTokens())
            input[i++] = Integer.parseInt(st.nextToken());
            
        if (N <= 2) {
            System.out.println(0);
            return;
        }
        
        int s = input[0], e = input[N-1];
        int[] startDelta = {-1, 0, 1};
        List<int[]> pairs = new ArrayList<>();
        for (i = -2; i <= 2; i++) {
            if ((e-s+i) % (N-1) != 0)
                continue;
            int d = (e-s+i) / (N-1);
            int signI = i < 0 ? -1 : 1;
            for (int j = startDelta[Math.abs(i)]; j <= 1; j++)
                pairs.add(new int[]{d, -1 * signI * j});
        }
        
        int result = -1;
        for (int[] pair : pairs) {
            int d = pair[0], sDelta = pair[1];
            int newS = s + sDelta;
            int curResult = 0;
            if (sDelta != 0)
                curResult++;
            for (i = 1; i < N; i++) {
                int diff = Math.abs(input[i] - newS - d * i);
                if (diff == 1)
                    curResult++;
                else if (diff > 1) {
                    curResult = -1;
                    break;
                }
            }
            if (curResult == -1)
                continue;
            if (result == -1)
                result = Integer.MAX_VALUE;
            result = Math.min(result, curResult);
        }
        System.out.println(result);
    }
}