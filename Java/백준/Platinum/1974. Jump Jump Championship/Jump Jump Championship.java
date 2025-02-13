//1시간 9분 소요

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        var sb = new StringBuffer();
        while (T-- != 0) {
            int N = Integer.parseInt(br.readLine());
            
            int[] list = new int[N];
            int[] lis = new int[N];
            int[] lisIdx = new int[N];
            int lisSize = 0;
            var st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                list[i] = Integer.parseInt(st.nextToken());
                
                if (i == 0 || lis[lisSize-1] < list[i]) {
                    lisIdx[i] = lisSize;
                    lis[lisSize++] = list[i];
                } else if (lis[lisSize-1] > list[i]) {
                    int left = 0, right = lisSize-1;
                    while (left < right) {
                        int mid = (left + right) / 2;
                        if (lis[mid] < list[i])
                            left = mid + 1;
                        else
                            right = mid;
                    }
                    lisIdx[i] = left;
                    lis[left] = list[i];
                } else
                    lisIdx[i] = lisSize-1;
            }
            
            int[] answer = new int[lisSize];
            int cur = lisSize-1;
            for (int i = N-1; i >= 0; i--) {
                if (cur == lisIdx[i]) {
                    answer[cur--] = i;
                }
            }
            sb.append(lisSize).append('\n');
            for (int i = 0; i < lisSize; i++)
                sb.append(answer[i] + 1).append(' ');
            sb.append('\n');
        }
        System.out.print(sb);
    }
}