/*
1시간 30분 소요

#### 내 문제점
1. 어설프게 풀이과정을 상상해서 쓰다가, 코드를 다 짜고 나서야 잘못되었다는 것을 깨달음.
    1. 풀이과정을 쓸때 예제 입력만 손으로 풀어봤더라도 시간을 절약할수 있었음.
2. 입력시 메모리 초과가 날 수 있다는 것을 몰랐음. 자바의 fast io 도 처음 알게됨.
3. 정답이 int 최대치를 넘을수 있는지 여부를 확인하지 않음. 최악의 경우 정답이 N^4임.
4. TreeSet 메소드에 subSet, tailSet, headSet 이 있다는 것을 몰랐음.
5. 이전 코드에서 모든 간선마다 tailSet().size() 를 시행했음. 아무리 O(1) 짜리 연산이어도 너무 비싼 연산임.

총평 : 예시를 손으로 풀고나서 풀이과정을 썼다면,
세그먼트 트리를 망설임 없이 썼다면, 훨씬 빠르면서 정답에 가깝게 풀었을 것임.

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
