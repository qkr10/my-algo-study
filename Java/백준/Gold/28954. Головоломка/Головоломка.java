import java.util.*;
import java.io.*;

public class Main{
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        String[] nm1 = br.readLine().split(" ");
        int N1 = Integer.parseInt(nm1[0]);
        int M1 = Integer.parseInt(nm1[1]);
        String[] pic1 = new String[N1];
        int[] height1 = new int[M1];
        for (int n = 0; n < N1; n++) {
            pic1[n] = br.readLine();
        }
        for (int m = 0; m < M1; m++) {
            height1[m] = 100000;
            for (int n = N1-1; n >= 0; n--)
                if (pic1[n].charAt(m) == '*') {
                    height1[m] = N1-n-1;
                    break;
                }
        }
        
        String[] nm2 = br.readLine().split(" ");
        int N2 = Integer.parseInt(nm2[0]);
        int M2 = Integer.parseInt(nm2[1]);
        String[] pic2 = new String[N2];
        int[] height2 = new int[M2];
        for (int n = 0; n < N2; n++) {
            pic2[n] = br.readLine();
        }
        for (int m = 0; m < M2; m++) {
            height2[m] = 100000;
            for (int n = 0; n < N2; n++)
                if (pic2[n].charAt(m) == '#') {
                    height2[m] = n;
                    break;
                }
        }
        
        //height1[i] = i번째 열을 아래서부터 보았을때, 처음으로 * 문자가 나올때까지 . 문자의 갯수
        //height2[i] = i번째 열을 위에서부터 보았을때, 처음으로 # 문자가 나올때까지 . 문자의 갯수
        
        int result = 0;
        for (int i = -M1+1; i < M2; i++) {
            int drop = 1000000;
            //몇칸 떨어졌는지 drop 변수에 저장
            for (int j = 0; j < M2; j++) {
                if (j-i < 0 || M1 <= j-i)
                    continue;
                drop = Math.min(drop, height1[j-i] + height2[j]);
            }
            if (drop >= 100000)
                continue;
            
            int count = 0;
            for (int n = 0; n < N2; n++) {
                for (int m = 0; m < M2; m++) {
                    if (pic2[n].charAt(m) != '#')
                        continue;
                    if (m != M2-1 && pic2[n].charAt(m+1) != '.')
                        continue;
                    int pic1n = N1-drop+n;
                    int pic1m = m-i+1;
                    boolean isInRange = 0 <= pic1n && pic1n < N1 && 0 <= pic1m && pic1m < M1;
                    boolean isBlock = isInRange && pic1[pic1n].charAt(pic1m) == '*';
                    if (isBlock)
                        count++;
                }
            }
            result = Math.max(result, count);
        }
        System.out.printf("%d\n", result);
    }
}