/*
2시간 31분 소요

#### 나의 문제점
1. bfs(g, r) 을 어떻게 구현할지 생각해 두지 않음. 이런걸 생각하려고 풀이과정을 쓰는건데 그러지 못함.
    1. 결국 큐에 어떤 값을 넣을지, 배열을 어떤 형태로 만들지를 코드를 짜놓은 상태에서 이리저리 수정하느라 늦어짐.
    2. 풀이과정을 다시한번 적어보고, 내가 처음에 어떤식으로 메모해야 했을지, 내가 왜 메모하는걸 회피했는지 느껴보자.
2. dfs(g, r) 의 구현에서 막힘. 특히 나와 같은 방식으로 구현하려면 r을 계산할때 g를 제외해야 하는데, 여기서 좀 막힘.
    1. next_permutation() 구현을 외워뒀다면 쉽지 않았을까. 다른 사람들 구현을 찾아보자.
    2. 대다수 사람들도 나와 비슷하게 구현함.
    3. next_permutation() 을 써도 되지만, 0, 1 로만 이루어진 수열이라 굳이 쓸 필요를 느끼지 못한듯 함.
3. Integer.bitCount(), Integer.numberOfTrailingZeros() 함수 이름을 검색해야 했음.
    1. ntz 를 계산하는 알고리즘을 찾아보다 de bruijn sequence 라는 신기한 이론을 찾음.
    2. bitCount, numberOf(Trailing/Leading)Zeros, (lowest/highest)OneBit 는 외워두자.
4. 코드를 다 짜고 나서야 찾아낸 문제점들 :
    1. Queue 는 인터페이스고 ArrayDeque 로 객체를 생성해야함.
    2. 한 칸에 대해 ret++; 이 두번 실행되어 정답이 두배로 출력되었음.
        1. bfs를 할때 큐에 {행,열,색} 배열을 넣었으니, 한 칸이 색의 갯수만큼 중복되어 처리될수 있었음.
    3. map[i][j] == 1 일때만 queue.add() 를 하여, map[i][j] == 2 인 경우는 queue.add() 되지 못했음.
        1. map[i][j] 의 값이 0,1,2 셋 뿐임. 하나씩 어떤 뜻인지를 다시 생각해 보면 문제 없었음.

#### 풀이과정
1. map[i][j] = i행 j열이 0=호수 1=땅 2=배양액 뿌릴수 있는 땅
2. N, M, G, R, B = map[i][j]==2 인 땅의 수, A[i] = i번째 배양액 뿌릴수 있는 땅의 좌표
3. bfs(g, r) : g[i] = A[i]에 초록 배양액 뿌려졌는지 진리값. r[i] = A[i]에 빨간 배양액 뿌려졌는지 진리값.
    1. g, r 은 비트마스크로 표현. 주어진 g, r 에 대해 피울수 있는 꽃의 최대 갯수를 반환함.
4. dfs(g, r) : g, r 의 모든 경우의 수 각각에 대해 bfs(g, r) 을 호출하고 최대값을 반환함.

#### 풀고나서 다시 쓴 풀이과정
1. map[i][j] = 0:호수 1:땅 2:배양액을 뿌릴수 있는 땅
2. N, M, G, R, B = map[i][j]==2 인 땅의 수, A[i] = i번째 배양액 뿌릴수 있는 땅의 좌표
3. bfs(g, r), dfs(g, r) 함수 구현하기. g[i] = A[i]에 초록색 배양액을 뿌린다면 1 아니면 0.
4. dfs(g, r) :
    1. for(i = min(ntz(g), B-1); i > G - count(g); i--) g[i]=1, dfs(g, r);
    2. if(G==count(g)) for(i=min(ntz(r),B-1);i>R-count(r);i--)
        1. if(g[i] == 0) r[i]=1, dfs(g, r);
    3. if(G==count(g)&&R==count(r)) bfs(g,r);
5. bfs(g, r) :
    1. visit[i][j][k] = i행 j열에 k색의 배양액이 도달한 시간.
    2. 큐에 g[i]==1 이거나 r[i]==1 인 i들에 대해 A[i] 좌표들 넣어, 좌표를 하나씩 뽑으며 하위 단계 진행.
        1. visit[i][j][0] == visit[i][j][1] 이면 꽃이 핌. 꽃이 핀다면 아래 단계를 건너뜀.
        2. k = visit[i][j][0]이 초기값이 아니면 0, 초기값이면 1
        3. visit[nexti][nextj][k] == 초기값 이면 아래 단계를 건너뜀.
        3. 큐에 (nexti, nextj) 넣고 visit[nexti][nextj][k] = visit[i][j][k]+1 대입
*/

import java.util.*;
import java.io.*;

public class Main {
    private static int N, M, G, R, B;
    private static int[][] map, A;
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        var st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        G = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 2)
                    B++;
            }
        }
        A = new int[B][2];
        int Ai = 0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++)
                if (map[i][j] == 2) {
                    A[Ai][0] = i;
                    A[Ai++][1] = j;
                }
        
        System.out.println(dfs(0, 0));
    }
    
    private static int dfs(int g, int r) {
        if (Integer.bitCount(g) != G) {
            int ret = 0;
            int gtz = Math.min(Integer.numberOfTrailingZeros(g), B);
            for (int i = gtz-1; i >= 0; i--)
                ret = Math.max(ret, dfs(g | (1 << i), r));
            return ret;
        }
        else if (Integer.bitCount(r) != R) {
            int ret = 0;
            int rtz = Math.min(Integer.numberOfTrailingZeros(r), B);
            for (int i = rtz-1; i >= 0; i--) {
                if (((g | r) & (1 << i)) != 0)
                    continue;
                ret = Math.max(ret, dfs(g, r | (1 << i)));
            }
            return ret;
        }
        return bfs(g, r);
    }
    private static final int[][] delta = {
        {1, 0},
        {-1, 0},
        {0, 1},
        {0, -1}
    };
    private static int bfs(int g, int r) {
        //colorMap[a][b][c] = a행 b열 c색 배양액이 뿌져진 시간
        int[][][] colorMap = new int[N][M][2];
        
        //0: 행, 1: 열, 2: 색, 3: time
        Queue<int[]> queue = new ArrayDeque<>();
        while (g != 0) {
            int[] temp = A[Integer.numberOfTrailingZeros(g)];
            queue.add(new int[]{temp[0], temp[1], 0});
            colorMap[temp[0]][temp[1]][0] = 1;
            g &= g - 1;
        }
        while (r != 0) {
            int[] temp = A[Integer.numberOfTrailingZeros(r)];
            queue.add(new int[]{temp[0], temp[1], 1});
            colorMap[temp[0]][temp[1]][1] = 1;
            r &= r - 1;
        }
        
        int ret = 0;
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int t = colorMap[cur[0]][cur[1]][cur[2]];
            if (colorMap[cur[0]][cur[1]][cur[2] ^ 1] == t) {
                if (cur[2] == 0)
                    ret++;
                continue;
            }
            for (int dir = 0; dir < 4; dir++) {
                int row = cur[0] + delta[dir][0];
                int col = cur[1] + delta[dir][1];
                if (Math.min(row, col) < 0 || row >= N || col >= M)
                    continue;
                if (map[row][col] != 0 && colorMap[row][col][cur[2]] == 0) {
                    queue.add(new int[]{row, col, cur[2]});
                    colorMap[row][col][cur[2]] = t+1;
                }
            }
        }
        return ret;
    }
}
