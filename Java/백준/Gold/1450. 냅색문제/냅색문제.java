//1시간 25분 소요
//nextPermutation 말고 재귀로도 구현하고 시간을 비교해 보고 싶다.
//mitm 알고리즘을 처음 접해서 신기함.
//어디서나 자주 등장하는 완탐시간을, 2^30 을 2^15+1 로 줄일수 있으므로 유용할것 같다.

//1시간 동안 구현은 끝냈지만 25분동안 디버깅했음
//1. try ~ catch ~ 를 잘 써먹으니 어느 줄에서 어떤 문제인지 잘 파악할수 있어서 좋았다.
//2. N==1 일때 예외처리를 했어야 함. 그렇지 않으면 크기가 0인 배열을 만들게 됨.
//3. nextPermutation() 에서 permu[l+1:l+1+n-r] 은 true 로 채우고, 그 뒤는 false로 채워야 한다고 생각했음.

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
    
    static boolean nextPermutation(boolean[] permu) {
        int n = permu.length;
        int l = n - 2;
        while (l >= 0 && !(!permu[l] && permu[l+1]))
            l--;
        if (l == -1)
            return false;
        
        int r = l+1;
        while (r+1 < n && !(permu[r] && !permu[r+1]))
            r++;
        
        permu[l] = true;
        
        //permu[l+1:] 에서 첫 n-r 개 원소는 false 여야 함. 나머지는 true
        Arrays.fill(permu, l+1, l+1 + n-r, false);
        Arrays.fill(permu, l+1 + n-r, n, true);
        return true;
    }
    
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        var st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());
        int[] arr = new int[N];
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
        
        int left = N/2, right = N/2 + N%2;
        
        boolean[] permu = new boolean[left];
        TreeMap<Long, Integer> map = new TreeMap<>();
        for (int i = 0; i <= left; i++) {
            Arrays.fill(permu, 0, left - i, false);
            Arrays.fill(permu, left - i, left, true);
            do {
                long s = sum(arr, 0, permu);
                var val = map.get(s);
                if (val == null)
                    val = 1;
                else
                    val++;
                map.put(s, val);
            } while (nextPermutation(permu));
        }
        
        int[] prefixSum = new int[map.size()+1];
        int idx = 1;
        for (var entry : map.entrySet()) {
            long key = entry.getKey();
            int val = entry.getValue();
            prefixSum[idx] = prefixSum[idx-1] + val;
            entry.setValue(idx);
            idx++;
        }
        
        permu = new boolean[right];
        int ans = 0;
        for (int i = 0; i <= right; i++) {
            Arrays.fill(permu, 0, right - i, false);
            Arrays.fill(permu, right - i, right, true);
            do {
                long s = sum(arr, left, permu);
                var entry = map.floorEntry(C - s);
                if (entry == null)
                    continue;
                else
                    ans += prefixSum[entry.getValue()];
            } while (nextPermutation(permu));
        }
        System.out.println(ans);
    }
}