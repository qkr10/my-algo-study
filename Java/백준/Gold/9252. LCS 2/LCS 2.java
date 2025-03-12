import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        char[] A = br.readLine().toCharArray();
        char[] B = br.readLine().toCharArray();
        int a = A.length;
        int b = B.length;
        int[][] table = new int[a+1][b+1];
        for (int i = 1; i <= a; i++)
        for (int j = 1; j <= b; j++) {
            if (A[i-1] == B[j-1])
                table[i][j] = table[i-1][j-1] + 1;
            else
                table[i][j] = Math.max(table[i-1][j], table[i][j-1]);
        }
        
        int len = table[a][b];
        System.out.println(len);
        if (len == 0) return;
        
        char[] lcs = new char[len];
        int cur = len - 1;
        while (cur != -1) {
            if (A[a-1] == B[b-1]) {
                a--;
                b--;
                lcs[cur--] = A[a];
                continue;
            }
            if (table[a][b] == table[a][b-1]) b--;
            else if (table[a][b] == table[a-1][b]) a--;
        }
        for (int i = 0; i < len; i++)
            System.out.print(lcs[i]);
    }
}