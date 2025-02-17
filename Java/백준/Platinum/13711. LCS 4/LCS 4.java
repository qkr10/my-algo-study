//1시간 30분 소요

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[][][] arr = new int[2][2][N];
        for (int l = 0; l < 2; l++) {
            var st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                arr[l][0][i] = Integer.parseInt(st.nextToken()) - 1;
                arr[l][1][arr[l][0][i]] = i;
            }
        }
        
        int[] src = new int[N];
        for (int i = 0; i < N; i++) {
            int val = arr[0][0][i];
            src[i] = arr[1][1][val];
        }
        
        int[] lis = new int[N];
        int len = 0;
        for (int i = 0; i < N; i++) {
            if (len == 0 || lis[len-1] < src[i]) {
                lis[len++] = src[i];
                continue;
            }
            int l = 0, r = len-1;
            while (l < r) {
                int mid = (r + l) / 2;
                if (lis[mid] < src[i]) {
                    l = mid+1;
                } else {
                    r = mid;
                }
            }
            lis[r] = src[i];
        }
        
        System.out.println(len);
    }
}