//1시간 25분 소요
//nextPermutation 말고 재귀로도 구현하고 시간을 비교해 보고 싶다.
//mitm 알고리즘을 처음 접해서 신기함.
//어디서나 자주 등장하는 완탐시간을, 2^30 을 2^15+1 로 줄일수 있으므로 유용할것 같다.

//1시간 동안 구현은 끝냈지만 25분동안 디버깅했음
//1. try ~ catch ~ 를 잘 써먹으니 어느 줄에서 어떤 문제인지 잘 파악할수 있어서 좋았다.
//2. N==1 일때 예외처리를 했어야 함. 그렇지 않으면 크기가 0인 배열을 만들게 됨.
//3. nextPermutation() 에서 permu[l+1:l+1+n-r] 은 true 로 채우고, 그 뒤는 false로 채워야 한다고 생각했음.

//13분동안 nextPermutation() 말고 재귀적으로 구현함. 훨씬 직관적이고 편하다.
//이후 10분동안 또 디버깅함. 지역변수 C에만 값이 들어가고 필드 C에는 값이 설정되지 않은것이 원인이다.
//무작정 변수 출력부터 해보다가, 차분하게 다시 코드를 읽어보려 하자마자 문제점을 찾을수 있었다.
//결과론적으로 보면, 언제나 1이 나오던 이유가 무엇인지 이해하려 했어도 문제를 찾을수 있었을것 같다.

import java.util.*;
import java.io.*;

public class Main{
    static long sum(int[] arr, int start, boolean[] select) {
        long ret = 0;
        for (int i = 0; i < select.length; i++) {
            ret += select[i] ? arr[start + i] : 0;
        }
        return ret;
    }
    
    static int[] arr;
    static TreeMap<Long, Integer> map;
    static void makePermutation1(boolean[] permu, int current, int remain) {
        if (remain == 0) {
            long s = sum(arr, 0, permu);
            var val = map.get(s);
            if (val == null)
                val = 1;
            else
                val++;
            map.put(s, val);
            return;
        }
        
        remain--;
        for (int i = current; i < permu.length - remain; i++) {
            permu[i] = true;
            makePermutation1(permu, i+1, remain);
            permu[i] = false;
        }
    }
    
    static int left, right, C, ans;
    static int[] prefixSum;
    static void makePermutation2(boolean[] permu, int current, int remain) {
        if (remain == 0) {
            long s = sum(arr, left, permu);
            var entry = map.floorEntry(C - s);
            if (entry == null)
                return;
            else
                ans += prefixSum[entry.getValue()];
            return;
        }
        
        remain--;
        for (int i = current; i < permu.length - remain; i++) {
            permu[i] = true;
            makePermutation2(permu, i+1, remain);
            permu[i] = false;
        }
    }
    
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        var st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        arr = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        
        if (N == 1) {
            int ans = 0;
            if (C < arr[0])
                ans = 1;
            else
                ans = 2;
            System.out.println(ans);
            return;
        }
        
        left = N/2;
        right = N - left;
        
        boolean[] permu = new boolean[left];
        map = new TreeMap<>();
        for (int i = 0; i <= left; i++)
            makePermutation1(permu, 0, i);
        
        prefixSum = new int[map.size()+1];
        int idx = 1;
        for (var entry : map.entrySet()) {
            long key = entry.getKey();
            int val = entry.getValue();
            prefixSum[idx] = prefixSum[idx-1] + val;
            entry.setValue(idx);
            idx++;
        }
        
        permu = new boolean[right];
        for (int i = 0; i <= right; i++)
            makePermutation2(permu, 0, i);
        
        System.out.println(ans);
    }
}