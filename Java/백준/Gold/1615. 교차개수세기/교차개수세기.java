/*
1시간 30분 소요

#### 풀이과정
1. N, M, arr[i] = (왼쪽 i번 정점과 오른쪽 j 번 정점을 잇는 간선들에 대해, j 값들의 배열), min_heap, result = 0
2. i = 0...N-1 에 대해 하위 단계를 반복
	1. temp = 0, tempArr = []
	2. for j in arr[i]:
		1. j < i 이면 result += min_heap.size()
		2. j > i 이면 tempArr.push(j)
		3. j == i 이면 temp++
	3. while (min_heap.peek() == i) min_heap.pop()
	4. result += min_heap.size() * temp
	5. min_heap.pushAll(tempArr)
3. result 출력
*/

import java.util.*;
import java.io.*;

public class Main {
    public static int read() throws IOException {
        int ret = 0, i = 0;
        while ((i = System.in.read()) >= '0')
            ret = (ret << 3) + (ret << 1) + (i & 15);
        return ret;
    }
    
    public static void main(String[] args) throws IOException {
        int N = read();
        int M = read();
        List<Integer>[] arr = new List[N];
        for (int i = 0; i < N; i++)
            arr[i] = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            int a = read() - 1;
            int b = read() - 1;
            arr[a].add(b);
        }
        
        long result = 0;
        long[] count = new long[N];
        for (int i = 0; i < N; i++) {
            for (int j : arr[i])
                for (int k = j+1; k < N; k++)
                    result += count[k];
            for (int j : arr[i])
                count[j]++;
        }
        System.out.println(result);
    }
}