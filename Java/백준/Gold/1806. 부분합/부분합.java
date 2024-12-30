import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        String[] ns = br.readLine().split(" ");
        int N = Integer.parseInt(ns[0]);
        int S = Integer.parseInt(ns[1]);
        int[] arr = new int[N];
        String[] inputs = br.readLine().split(" ");
        for (int i = 0; i < N; i++)
            arr[i] = Integer.parseInt(inputs[i]);
        int[] prefixSum = new int[N+1];
        for (int i = 0; i < N; i++)
            prefixSum[i+1] = prefixSum[i] + arr[i];
        int start = 0, end = 1, result = Integer.MAX_VALUE;
        while (end <= N) {
            int sum = prefixSum[end] - prefixSum[start];
            int range = end - start;
            
            if (S <= sum) {
                result = Math.min(result, range);
                start++;
                continue;
            }
            end++;
        }
        
        if (result == Integer.MAX_VALUE)
            result = 0;
        
        System.out.printf("%d\n", result);
    }
}