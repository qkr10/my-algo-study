/*
32분 소요
#### 풀이과정
1. N, p[N][4] 입력. (xbl, ybl, xtr, ytr)
2. ratio[N][2], tlRatioSort[N], brRatioSort[N] 계산. (ytl/xtl, ybr/xbr)
3. 투포인터로 tlRatioSort 한칸 앞으로 가서 result++ 하고,
   brRatioSort 값이 tlRatioSort 보다 커질때까지 brRatioSort 한칸 앞으로 가면서 result--
   위 두 과정이 끝나고 나서 result+1 값의 최대값이 정답.
*/

import java.util.*;
import java.io.*;

public class Main{
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[][] rects = new int[N][4];
        for (int i = 0; i < N; i++) {
            var st = new StringTokenizer(br.readLine());
            rects[i][0] = Integer.parseInt(st.nextToken());
            rects[i][1] = Integer.parseInt(st.nextToken());
            rects[i][2] = Integer.parseInt(st.nextToken());
            rects[i][3] = Integer.parseInt(st.nextToken());
        }
        
        double[] tlRatioSort = new double[N];
        double[] brRatioSort = new double[N];
        for (int i = 0; i < N; i++) {
            tlRatioSort[i] = (double)rects[i][0] / rects[i][3];
            brRatioSort[i] = (double)rects[i][2] / rects[i][1];
        }
        Arrays.sort(tlRatioSort);
        Arrays.sort(brRatioSort);
        
        int tlIdx = -1, brIdx = 0, count = 0, result = 0;
        while(++tlIdx < N) {
            count++;
            while (brIdx < N && brRatioSort[brIdx] < tlRatioSort[tlIdx]) {
                brIdx++;
                count--;
            }
            result = Math.max(count, result);
        }
        System.out.println(result);
    }
}