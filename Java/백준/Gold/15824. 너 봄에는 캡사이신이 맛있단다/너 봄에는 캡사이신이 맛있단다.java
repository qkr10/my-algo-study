import java.util.*;
import java.io.*;

/*
아이디어(틀림)
```
input[] = 스코빌 지수 순으로 정렬된 메뉴 목록
pow[i] = 2^i
result[i] = input[0-i] 까지의 정답 = result[i-1] + (선택한 메뉴중 가장 오른쪽 메뉴가 input[i]인 조합들의 주헌고통지수 합)

result[i] = result[i-1] + (input[i] - input[i-1]) + (input[i] - input[i-2])*2 + (input[i] - input[i-3])*2^2 + ...
result[i] = (input[i-1] - input[i-2]) + (input[i-1] - input[i-3])*2 + (input[i-1] - input[i-4])*2^2 + ... + (input[i] - input[i-1]) + (input[i] - input[i-2])*2 + (input[i] - input[i-3])*2^2 + ...

(input[a]+input[a-b])*2^x + y = (input[a+1]+input[a]+input[a-b])*2^x
y = input[a+1]*2^x

result[i] = result[i-1] + (input[i] - input[i-1]) + (input[i] - input[i-2])*2 + (input[i] - input[i-3])*2^2 + ...
result[i] = result[i-1] + result[i-1]*2 + (input[i] - input[i-1])*sigma{j=0,i-1}(2^j)

sigma{j=0,i}(2^j) = 2^(i+1) - 1 = pow[i+1] - 1;

result[i] = result[i-1]*3 + (input[i] - input[i-1])*(pow[i] - 1)
```

반례
```
4
0 1 4 5
answer = 41
output = 43

잘못된 알고리즘의 계산 과정
1
12 = 1 + 1 * 2 + 3 * 0b11
43 = 12 + 12 * 2 + 1 * 0b111
```

아이디어(현재)
```
input[] = 스코빌 지수 순으로 정렬된 메뉴 목록
pow[i] = 2^i
sum[i] = (선택한 메뉴중 가장 오른쪽 메뉴가 input[i]인 조합들의 주헌고통지수 합) = sum[i-1] * 2 + (input[i] - input[i-1]) * (pow[i] - 1)
result[i] = (input[0-i] 까지의 정답) = result[i-1] + sum[i]
```
*/

public class Main {
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        var st = new StringTokenizer(br.readLine());
        int[] input = new int[N];
        for (int i = 0; i < N; i++)
            input[i] = Integer.parseInt(st.nextToken());
        Arrays.sort(input);
        
        int mod = 1000000007;
        long pow = 2, result = 0, temp = 0;
        for (int i = 1; i < N; i++) {
            temp = (temp * 2 + (input[i] - input[i-1]) * (pow - 1)) % mod;
            result = (result + temp) % mod;
            pow = pow * 2 % mod;
        }
        System.out.println(result);
    }
}