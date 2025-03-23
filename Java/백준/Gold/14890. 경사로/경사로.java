//28분 소요
//일단 코딩을 하면서 생각하는건 빨리 구현하는데 좋은것 같음
//하지만, +1, -1 등 자잘한 수식에서 실수가 있고, 디버깅하는게 어려움
//특히, 한칸 상승을 판단하는 조건문을 map[i][j - 1] == map[i][j] + 1 와 같이 잘못 작성해서 디버깅이 오래걸림
//각 블록의 역할을 주석으로 적어보는 것도 디버깅에 도움이 될것 같음. 위 문제도 주석을 작성하다 찾음.

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        var st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int L = Integer.parseInt(st.nextToken());
        int[][] map = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++)
                map[i][j] = Integer.parseInt(st.nextToken());
        }
        
        //조건문 배열 범위 벗어나는지 체크 필요
        int ans = 0;
        for (int i = 0; i < N; i++) {
            //i번째 줄의 길 여부 판단.
            boolean trig = true;
            int len = 1;
            for (int j = 1; j < N; j++) {
                if (map[i][j] != map[i][j-1]) {
                    if (Math.abs(map[i][j] - map[i][j-1]) > 1) {
                        trig = false;
                        break;
                    }
                    
                    if (map[i][j] == map[i][j-1] + 1) {
                        //한칸 상승
                        if (len < L) {
                            trig = false;
                            break;
                        }
                        len = 1;
                    } else {
                        //한칸 하강
                        len = 0;
                        while (j+len+1 < N && map[i][j] == map[i][j+len+1]) len++;
                        if (len + 1 < L) {
                            trig = false;
                            break;
                        }
                        j += len;
                        len -= L - 1;
                    }
                } else {
                    len++;
                }
            }
            if (trig) ans++;
            
            //i번째 열의 길 여부 판단.
            trig = true;
            len = 1;
            for (int j = 1; j < N; j++) {
                if (map[j][i] != map[j-1][i]) {
                    //현재 칸과 이전 칸의 높이가 다를때
                    if (Math.abs(map[j][i] - map[j-1][i]) > 1) {
                        //높이가 2이상 차이나면 길이 아님
                        trig = false;
                        break;
                    }
                    
                    if (map[j][i] == map[j-1][i] + 1) {
                        //한칸 상승
                        if (len < L) {
                            trig = false;
                            break;
                        }
                        len = 1;
                    } else {
                        //한칸 하강
                        len = 0;
                        while (j+len+1 < N && map[j][i] == map[j+len+1][i]) len++;
                        if (len + 1 < L) {
                            trig = false;
                            break;
                        }
                        j += len;
                        len -= L - 1;
                    }
                } else {
                    len++;
                }
            }
            if (trig) ans++;
        }
        System.out.println(ans);
    }
}