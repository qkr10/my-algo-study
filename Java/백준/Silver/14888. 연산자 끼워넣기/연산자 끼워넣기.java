//40분 소요
//디버깅에 10분 정도 소요됨.
//1. nextPermutation() 첫번째 while 문에서 arr[l] > arr[l+1] 을 고쳐야 함. 같은 값이 있다고 생각해볼것.
//2. reverse(arr, l+1, r) 을 고쳐야 함. nextPermutation 의 원리를 생각해 보기.
//3. Math.abs(ans) * nums[i+1] * (ans < 0 ? -1 : 1); 을 고쳐야 함. 복붙시 무엇을 수정해야 하는지 생각하기.

//nextPermutation 의 원리.
//0. 첫 호출시 arr은 오름차순으로 정렬된 상태임.
//1. arr[l+1:] 이 내림차순이 되는 최소의 l값을 찾음.
//2. arr[l+1:] 에서 arr[l] < arr[r] 인 r의 최댓값을 찾음. arr[l+1:]이 정렬되어 있으므로 이분탐색 사용가능.
//3. swap(arr[l], arr[r]) 을 함.
//4. arr[l+1:] 을 뒤집음.
//5. 1~4 과정에 의해, arr[l+1:] 은 하나의 값이 교체되고 나서 오름차순으로 바뀌게 됨.

import java.util.*;
import java.io.*;

public class Main {
    static void swap(int[] arr, int a, int b) {
        int temp = arr[b];
        arr[b] = arr[a];
        arr[a] = temp;
    }
    
    static void reverse(int[] arr, int l, int r) {
        int mid = (r - l - 1) / 2;
        for (int i = 0; i <= mid; i++)
            swap(arr, l+i, r-i);
    }
    
    static boolean nextPermutation(int[] arr) {
        int l = arr.length - 2;
        while (l >= 0 && arr[l] >= arr[l+1]) l--;
        if (l == -1)
            return false;
        
        int r = l+1;
        while (r+1 < arr.length && arr[l] < arr[r+1]) r++;
        swap(arr, l, r);
        
        reverse(arr, l+1, arr.length-1);
        return true;
    }
    
    static int calc(int[] nums, int[] ops) {
        int ans = nums[0];
        for (int i = 0; i < ops.length; i++) {
            if (ops[i] == 0) {
                ans = ans + nums[i+1];
            } else if (ops[i] == 1) {
                ans = ans - nums[i+1];
            } else if (ops[i] == 2) {
                ans = ans * nums[i+1];
            } else if (ops[i] == 3) {
                ans = Math.abs(ans) / nums[i+1] * (ans < 0 ? -1 : 1);
            }
        }
        return ans;
    }
    
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        
        int[] nums = new int[N];
        var st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++)
            nums[i] = Integer.parseInt(st.nextToken());
        
        int[] opCounts = new int[4];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < 4; i++)
            opCounts[i] = Integer.parseInt(st.nextToken());
        
        int[] ops = new int[N-1];
        int k = 0;
        for (int i = 0; i < 4; i++)
            for (int j = opCounts[i]; j > 0; j--)
                ops[k++] = i;
        
        int minV = Integer.MAX_VALUE, maxV = Integer.MIN_VALUE;
        do {
            int res = calc(nums, ops);
            minV = Math.min(minV, res);
            maxV = Math.max(maxV, res);
        } while (nextPermutation(ops));
        System.out.println(maxV);
        System.out.println(minV);
    }
}
