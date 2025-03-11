import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        for (int i = 0; i <= 9; i++) {
            //i가 나오는 횟수 구해서 출력
            long cur = 1, next = 10;
            long count = 0;
            for (int j = 0; j <= 9; j++) {
                //10^j자릿수에 i가 나오는 횟수를 count에 더함.
                //j==0 이면 N/10 + N%10>=i ? 1 : 0
                //j==1 이면 N/100*10 + N/10%10>i ? 10 : N/10%10==i ? N%10 : 0
                //j==2 이면 N/1000*100 + N/100%10>i ? 100 : N/100%10==i ? N%100 : 0
                if (i != 0) {
                    count += N/next*cur;
                    count += N/cur%10>i ? cur : (N/cur%10==i ? N % cur + 1 : 0);
                } else if (j == 0)
                    count += N/10;
                else {
                    count += Math.max(N / next - 1, 0) * cur;
                    count += N/next == 0 ? 0 : N/cur%10 == 0 ? N%cur+1 : cur;
                }
                cur *= 10;
                next *= 10;
            }
            System.out.printf("%d ", count);
        }
    }
}