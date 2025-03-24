//38분 소요
//디버깅에 시간이 오래 걸림
//1. 이진수로 입력받을때, Integer.parseInt(br.readLine()) 처럼 작성하면 안됨.
//2. 변수 d의 초기값이 d = dir 이면 안됨. 이웃한 톱니의 회전방향이기 때문에 반대가 되어야 함.
//3. (num & (1 << 8)) >> 8 보다 num >> 8 & 1 로 쓰는게 간단함.
//4. left, right 변수 초기값이 2, 6 이었음.

import java.util.*;
import java.io.*;

public class Main{
    static boolean getState(int gear, int digit) {
        return (gear & 1 << digit) != 0;
    }
    
    static int rotate(int gear, int dir) {
        if (dir == -1) {
            //반시계
            gear <<= 1;
            gear |= gear >> 8 & 1;
            gear &= (1 << 8) - 1;
        } else {
            int temp = gear & 1;
            gear >>= 1;
            gear |= temp << 7;
        }
        return gear;
    }
    
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        int[] gears = new int[4];
        for (int i = 0; i < 4; i++) {
            gears[i] = Integer.parseInt(br.readLine(), 2);
        }
        
        int N = Integer.parseInt(br.readLine());
        final int left = 1, right = 5;
        for (int i = 0; i < N; i++) {
            var st = new StringTokenizer(br.readLine());
            int num = Integer.parseInt(st.nextToken()) - 1;
            int dir = Integer.parseInt(st.nextToken());
            
            int n = num, d = -dir;
            boolean cur = getState(gears[n], right);
            while (n+1 < 4 && (cur ^ getState(gears[n+1], left))) {
                cur = getState(gears[n+1], right);
                gears[n+1] = rotate(gears[n+1], d);
                d *= -1;
                n++;
            }
            
            n = num; d = -dir;
            cur = getState(gears[n], left);
            while (n-1 >= 0 && (cur ^ getState(gears[n-1], right))) {
                cur = getState(gears[n-1], left);
                gears[n-1] = rotate(gears[n-1], d);
                d *= -1;
                n--;
            }
            
            gears[num] = rotate(gears[num], dir);
        }
        
        int ans = 0;
        for (int i = 0; i < 4; i++)
            ans |= (gears[i] >> 7 & 1) << i;
        System.out.println(ans);
    }
}