//div[i][j] = i 의 j 번째 약수

//dp[i][j] = 현재 수가 i, K - i 가 j 일때, 최선의 전략으로 상대를 이기면 0
//모든 i 에 대해 dp[i][0] = 1
//모든 i, j 에 대해 dp[i][div[i][j]] = 0
//dp[i+div[i][k]][j-div[i][k]] = 1 을 만족시키는 k 가 존재하면 dp[i][j] = 0
//dp[1][K-1] 이 0 이면 "Kali", 1 이면 "Ringo" 출력

//위 방법은 메모리 초과

//dp[i] = 내 모니터에 숫자 i 가 떠있을때, 내가 이길수 있으면 0
//모든 i 에 대해 dp[i] = 1 로 초기화
//i 를 K...1 반복하며, 어떤 j 에 대해 dp[i+div[i][j]] = 1 이면, dp[i] = 0
//dp[1] 가 0 이면 "Kali", 1 이면 "Ringo" 출력

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        int K = Integer.parseInt(br.readLine());

        List<List<Integer>> div = new ArrayList<>();
        for (int i = 0; i <= K; i++) {
            div.add(new ArrayList<>());
            div.get(i).add(1);
        }
        for (int i = 2; i <= K; i++)
            for (int j = 1; i * j <= K; j++)
                div.get(i * j).add(i);

        int[] dp = new int[K+1];
        for (int i = 1; i <= K; i++)
            dp[i] = 1;
        for (int i = K; i > 0; i--) {
            int result = 0;
            for (int d : div.get(i)) {
                if (i + d > K)
                    break;
                result |= dp[i+d];
            }
            dp[i] = 1 ^ result;
        }

        String[] answer = {
            "Kali",
            "Ringo"
        };
        System.out.println(answer[dp[1]]);
    }
}