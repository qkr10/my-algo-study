/*
40분 소요

#### 나의 문제점
1. 풀이과정에서 arr[i] 라는 불필요한 배열을 만들고, 이 배열이 너무 커서 메모리 초과가 발생함.
	1. 제곱 연산은 상수시간에 가능하므로 계산결과를 굳이 저장할 필요가 없음.
2. 풀이과정 2번을 쓸때 어떻게 풀어야 할지 감을 못잡아 헤맸음.
	1. 어떻게 써야 했을지 풀이과정을 다시 쓰면서 느껴보자.
 	2. 반복문을 쓰고 싶으면 새로운 단계를 만들고 2-1, 2-2 같은 하위 단계들을 반복하라고 작성하자.

#### 풀이과정
1. arr[i] = i * i, i = 2...floor(sqrt(max)+1)
2. check[i] = (min + i 가 제곱수로 나누어 떨어지면 1)
	1. start[j] = (min - 1) / arr[j] + 1
	2. end[j] = max / arr[j]
	3. i=start[j]...end[j], check[i * arr[j]] = 1
3. max - min + 1 - sum(check)

#### 풀고나서 다시 쓰는 풀이과정
1. check[i] = (min + i) % (j * j) == 0 을 만족하는 j가 존재하는지 여부
2. 가능한 모든 j에 대해 하위 단계들을 반복
	1. start = (min - 1) / (j * j) + 1
 	2. end = max / (j * j)
  	3. i=start...end, check[i * j * j] = true
3. count(check, false) 를 출력
*/

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        var st = new StringTokenizer(br.readLine());
        long min = Long.parseLong(st.nextToken());
        long max = Long.parseLong(st.nextToken());
        int diff = (int)(max - min + 1);
        
        boolean[] check = new boolean[diff];
        for (int i = 2; i < (int)Math.sqrt(max)+2; i++) {
            long ii = (long) i * i;
            long start = (min - 1) / ii + 1;
            long end = max / ii;
            while (start != end+1) {
                check[(int)(start * ii - min)] = true;
                start++;
            }
        }
        
        int result = 0;
        for (int i = 0; i < check.length; i++)
            if (!check[i])
                result++;
        System.out.println(result);
    }
}
