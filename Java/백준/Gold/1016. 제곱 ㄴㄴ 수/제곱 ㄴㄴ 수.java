/*
40분 소요

#### 풀이과정
1. arr[i] = i * i, i = 2...floor(sqrt(max)+1)
2. check[i] = (min + i 가 제곱수로 나누어 떨어지면 1)
	1. start[j] = (min - 1) / arr[j] + 1
	2. end[j] = max / arr[j]
	3. i=start[j]...end[j], check[i * arr[j]] = 1
3. max - min + 1 - sum(check)
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