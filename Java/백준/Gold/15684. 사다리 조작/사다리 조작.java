import java.util.*;
import java.io.*;

public class Main {
    static int N, M, H;
    static boolean[][] lines;
    
    static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
    
    static boolean calc() {
        int[] result = new int[N];
        for (int i = 0; i < N; i++)
            result[i] = i;
        
        for (int j = 0; j < H; j++)
            for (int i = 0; i < N; i++)
                if (lines[i][j])
                    swap(result, i, i+1);
        
        for (int i = 0; i < N; i++)
            if (result[i] != i)
                return false;
        return true;
    }
    
    static int dfs(int depth, int h) {
        if (calc())
            return depth;
        if (depth == 3)
            return -1;
        
        int ans = 4;
        for (int i = h; i < H; i++) {
            for (int j = 0; j < N-1; j++) {
                //lines 배열 조작
                boolean c1 = j-1 >= 0;
                if ((c1 && lines[j-1][i]) || lines[j][i] || lines[j+1][i])
                    continue;
                lines[j][i] = true;
                
                int ret = dfs(depth+1, i);
                
                //lines 배열 복구
                lines[j][i] = false;
                
                if (ret == -1)
                    continue;
                else
                    ans = Math.min(ans, ret);
            }
        }
        if (ans == 4) return -1;
        return ans;
    }
    
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        var st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());
        lines = new boolean[N][H];
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            lines[b][a] = true;
        }
        
        int ans = -1;
        if (calc())
            ans = 0;
        else
            ans = dfs(0, 0);
        System.out.println(ans);
    }
}