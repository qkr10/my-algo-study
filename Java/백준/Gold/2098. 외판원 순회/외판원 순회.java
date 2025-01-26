/*
1시간 50분 소요
50분에 아래 풀이과정 4번까지 구현 했지만 시간초과 발생하여 인터넷 검색.
내 문제점1 : dfs 를 사용하면 일단 dp 부터 떠올리기.
내 문제점2 : dfs 반환값에 덧셈을 하는데도, minCost 변수 초기값을 int 최대값으로 줌.
#### 풀이과정
1. N, graph[N][N] 입력받음
2. visit[i] = (i번째 노드를 방문했다면 true), minCost = (현재까지 순회하는 비용의 최소값) 변수 초기화
3. dfs(n, cost) : dfs와 백트래킹으로 답을 찾아서 minCost 변수에 저장함.
3-1. 이미 minCost 보다 cost 가 크다면 현재 노드는 건너뜀.
4. visit 배열을 비트마스킹으로 최적화.
5. dfs를 dp로 최적화. dp[i][visit] = (현재 i노드 에서 상태가 visit일때 남은 노드를 순회하는 최소비용)
*/

import java.util.*;
import java.io.*;

public class Main {
    private static int N;
    private static int[][] graph, dp;
    private static int maxVisit;
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        graph = new int[N][N];
        for (int i = 0; i < N; i++) {
            var st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++)
                graph[i][j] = Integer.parseInt(st.nextToken());
        }
        
        dp = new int[N][1 << N];
        maxVisit = (1 << N) - 1;
        System.out.println(dfs(0, 0));
    }
    
    private static int dfs(int node, int visit) {
        if (dp[node][visit] != 0)
            return dp[node][visit];
        if (node == 0 && visit == maxVisit)
            return 0;
            
        int minCost = 1000000 * 20;
        for (int i = 0; i < N; i++) {
            if ((visit & (1 << i)) != 0 || graph[node][i] == 0)
                continue;
            int newVisit = visit | (1 << i);
            int newCost = graph[node][i] + dfs(i, newVisit);
            minCost = Math.min(minCost, newCost);
        }
        return dp[node][visit] = minCost;
    }
}