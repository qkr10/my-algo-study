/*
### 문제 이해
1. 초당이동속도 v, 시간 m, 시작점 s, 목적지 t, 1000개 이하의 벙커의 위치 b[n] 이 주어짐.
2. 벙커의 갯수 n이 주어지지 않으므로 입력이 끝났는지 판단해야함.
2-2. BufferedReader 클래스의 lines() 가 반환하는 문자열 스트림으로 처리하면 해결됨.
3. b[n], s, t 점들을 그래프로 표현. 이때 거리가 v * 60 * m 이하인 노드만 연결되었다고 표시.
4. 3에서 만든 그래프상에서 s와 t노드 사이의 경로가 존재하는지 확인. (visit배열 + dfs)
*/

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        String[] vm = br.readLine().split(" ");
        int v = Integer.parseInt(vm[0]);
        int m = Integer.parseInt(vm[1]);
        String[] sStr = br.readLine().split(" ");
        double xs = Double.parseDouble(sStr[0]);
        double ys = Double.parseDouble(sStr[1]);
        double[] s = {xs, ys};
        String[] tStr = br.readLine().split(" ");
        double xt = Double.parseDouble(tStr[0]);
        double yt = Double.parseDouble(tStr[1]);
        double[] t = {xt, yt};

        String[] lines = br.lines().toArray(String[]::new);
        int n = lines.length+2;
        double[][] b = new double[n][2];
        for (int i = 0; i < n-2; i++) {
            StringTokenizer st = new StringTokenizer(lines[i]);
            b[i][0] = Double.parseDouble(st.nextToken());
            b[i][1] = Double.parseDouble(st.nextToken());
        }
        b[n-2] = s;
        b[n-1] = t;

        double maxDist = v * m * 60;
        graph = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(graph[i], -1);
            int index = 0;
            for (int j = 0; j < n; j++) {
                double dist = getDist(b[i], b[j]);
                if (dist > maxDist || i == j)
                    continue;
                graph[i][index++] = j;
            }
        }

        visit = new int[n];
        Arrays.fill(visit, -1);
        bfs(n-2, n-1);
        if (visit[n-1] == -1) {
            System.out.println("No.");
        }
        else {
            int count = 0;
            int cur = n-1;
            while (visit[cur] != n-2) {
                cur = visit[cur];
                count++;
            }
            System.out.printf("Yes, visiting %d other holes.", count);
        }
    }

    private static int[][] graph;
    private static int[] visit;
    private static void bfs(int s, int t) {
        Queue<Integer> q = new ArrayDeque<>();
        q.add(s);
        visit[s] = s;
        while (!q.isEmpty()) {
            int cur = q.poll();
            for (int next : graph[cur]) {
                if (next == -1)
                    break;
                
                if (visit[next] != -1)
                    continue;
                q.add(next);
                visit[next] = cur;
                if (next == t)
                    return;
            }
        }
    }

    private static double getDist(double[] p1, double[] p2) {
        double x = p1[0] - p2[0], y = p1[1] - p2[1];
        return Math.sqrt(x*x + y*y);
    }
}