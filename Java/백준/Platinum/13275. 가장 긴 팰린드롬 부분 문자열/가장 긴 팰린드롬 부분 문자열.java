//manacher's 알고리즘 검색해보고 푸느라 오래걸림.

import java.util.*;
import java.io.*;

public class Main {
    private static int palindrome(char[] input) {
        //input 내의 팰린드롬 중 최대 길이를 구하기
        char[] str = new char[input.length * 2 + 1];
        Arrays.fill(str, '#');
        for (int i = 0; i < input.length; i++)
            str[i * 2 + 1] = input[i];
            
        int res = 0, r = -1, k = 0, n = str.length;
        int[] p = new int[n];
        
        for (int i = 0; i < n; i++) {
            if (2 * k - i >= 0 && r != -1) p[i] = Math.min(p[2 * k - i], r - i);
            while (0 <= i-p[i]-1
                   && i+p[i]+1 < n
                   && str[i-p[i]-1] == str[i+p[i]+1])
                p[i]++;
            if (r < i + p[i]) {
                r = i + p[i];
                k = i;
            }
            res = Math.max(res, p[i]);
        }
        return res;
    }
    
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        char[] input = br.readLine().toCharArray();
        System.out.println(palindrome(input));
    }
}