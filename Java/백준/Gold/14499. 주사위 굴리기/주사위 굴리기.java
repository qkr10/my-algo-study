import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        var st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int Y = Integer.parseInt(st.nextToken());
        int X = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int[][] map = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++)
                map[i][j] = Integer.parseInt(st.nextToken());
        }
        
        int[] dice = new int[6];
        int up = 0, right = 2, front = 4;
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < K; i++) {
            int command = Integer.parseInt(st.nextToken());
            //1부터 동,서,북,남
            if (command == 1) {
                if (X+1 >= M)
                    continue;
                X++;
                int left = 5 - right;
                right = up;
                up = left;
            } else if (command == 2) {
                if (X-1 < 0)
                    continue;
                X--;
                int down = 5 - up;
                up = right;
                right = down;
            } else if (command == 3) {
                if (Y-1 < 0)
                    continue;
                Y--;
                int down = 5 - up;
                up = front;
                front = down;
            } else if (command == 4) {
                if (Y+1 >= N)
                    continue;
                Y++;
                int back = 5 - front;
                front = up;
                up = back;
            }
            int down = 5 - up;
            if (map[Y][X] == 0) {
                map[Y][X] = dice[down];
            } else {
                dice[down] = map[Y][X];
                map[Y][X] = 0;
            }
            System.out.println(dice[up]);
        }
    }
}