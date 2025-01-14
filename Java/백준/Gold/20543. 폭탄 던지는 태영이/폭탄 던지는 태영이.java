import java.util.*;
import java.io.*;

/*
### 간략한 풀이 과정
1. R[r][c] = r행 c열의 폭탄 갯수
2. 연산A(r,c) = R[r][c] 값만큼 주변 M\*M 칸의 H[][] 를 줄임. O(M\*M)
3. j = {0 -> N-1-M/2} 반복, i = {0 -> N-1-M/2} 반복 하며 다음을 실행 => -H[i][j] = R[i+M/2][j+M/2], 연산A(i+M/2,j+M/2) 실행. O(N\*N\*M\*M)
4. 2D 레이지 세그트리로 연산A(r,c) 를 O(log^2(M)) 로 최적화함. 결과적으로 3번이 O(N\*N\*log^2(M)) 으로 최적화됨.
5. 또는, 3번의 반복문 내부를 다음 연산으로 대체. 연산B(r,c) = -H[r][c] - (좌상단 방향 (M/2)\*(M/2) 칸의 R[][] 값을 2D prefixSum 으로 구간합을 구함)  = R[r+M/2][c+M/2] O(1)

### 구체적인 풀이 과정
1. n, m 입력
2. H[n][n] 배열 만들고 입력받음. 이때 양수로 입력값 전처리
3. R[n][n] 배열 만들기
4. prefixSum[n+1][n+1] 배열 생성. prefixSum[i+1][j+1] = 가능한 모든 a, b에 대해 (i-a,j-b) 위치의 폭탄 개수 합
5. getPrefixSum(sr,sc,er,ec) = prefixSum[er+1][ec] + prefixSum[er][ec+1] - prefixSum[er][ec] - prefixSum[sr][ec] - prefixSum[er][sc] + prefixSum[sr][sc]
5-1. prefixSum[er+1][ec+1] = 0 이므로, 일반적인 prefixSum 조회 방식을 쓰지 못함.
6. r = 0->N-1-M, c = 0->N-1-M, R[r+M/2][c+M/2] = H[r][c] - getPrefixSum(r,c,r+M/2,c+M/2), prefixSum[r+1+M/2][c+1+M/2] = getPrefixSum(0,0,r+M/2,c+M/2) + R[r+M/2][c+M/2]
7. R을 출력
*/

public class Main {
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        String[] nm = br.readLine().split(" ");
        int n = Integer.parseInt(nm[0]);
        int m = Integer.parseInt(nm[1]);
        long[][] h = new long[n][n];
        long[][] r = new long[n][n];
        long[][] prefixSum = new long[n+1][n+1];
        for (int i = 0; i < n; i++) {
            var st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++)
                h[i][j] = -Long.parseLong(st.nextToken());
        }
        
        for (int i = 0; i <= n - m; i++)
            for (int j = 0; j <= n - m; j++) {
                int ii = i + m/2, jj = j + m/2;
                int iii = Math.max(0, i - m/2), jjj = Math.max(0, j - m/2);
                r[ii][jj] = h[i][j] - getPrefixSum(prefixSum, iii, jjj, ii, jj);
                prefixSum[ii+1][jj+1] = r[ii][jj] + getPrefixSum(prefixSum, 0, 0, ii, jj);
            }
        
        var sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(r[i][j]);
                sb.append(" ");
            }
            sb.append("\n");
        }
        System.out.print(sb.toString());
    }
    static long getPrefixSum(long[][] p, int sr, int sc, int er, int ec) {
        return p[er+1][ec] + p[er][ec+1] - p[er][ec] - p[er+1][sc] - p[sr][ec+1] + p[sr][sc];
    }
}