import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        String[] nk = br.readLine().split(" ");
        int n = Integer.parseInt(nk[0]);
        int k = Integer.parseInt(nk[1]);
        List<Integer> arr = new ArrayList<>();
        var tokenizer = new StringTokenizer(br.readLine());
        while (tokenizer.hasMoreTokens()) {
            int val = Integer.parseInt(tokenizer.nextToken());
            if (val % 2 == 0)
                arr.add(0);
            else if (arr.size() > 0)
                arr.set(arr.size()-1, arr.get(arr.size()-1)+1);
        }
        
        if (arr.size() == 0) {
            System.out.println(0);
            return;
        }
        
        int start = 0;
        int end = 0;
        int result = 0;
        int sum = 0;
        while (sum + arr.get(end) <= k && end + 1 < arr.size()) {
            sum += arr.get(end);
            end++;
        }
        while (true) {
            result = Math.max(result, end - start + 1);
            
            sum += arr.get(end);
            end++;
            
            if (end == arr.size())
                break;
            
            while (sum > k) {
                sum -= arr.get(start);
                start++;
            }
        }
        
        System.out.println(result);
    }
}