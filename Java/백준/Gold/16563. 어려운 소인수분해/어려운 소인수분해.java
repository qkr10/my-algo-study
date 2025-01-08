import java.util.*;
import java.util.stream.*;
import java.io.*;

public class Main {
    private static int[] eratos;
    private static List<Integer>[] dp;
    
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        String[] line = br.readLine().split(" ");
        int[] input = Arrays.stream(line).mapToInt(Integer::parseInt).toArray();
        
        eratos = new int[5000001];
        int loopCount = (int) Math.sqrt(5000001);
        for (int i = 2; i < loopCount; i++) {
            if (eratos[i] != 0)
                continue;
            for (int j = i * 2; j < 5000001; j += i)
                if (eratos[j] == 0) eratos[j] = i;
        }
        
        dp = new List[5000001];
        StringBuilder sb = new StringBuilder();
        for (int i : input) {
            dfs(i);
            String result = dp[i].stream()
                .map(Object::toString)
                .collect(Collectors.joining(" "));
            sb.append(result);
            sb.append("\n");
        }
        System.out.print(sb.toString());
    }
    
    private static void dfs(int i) {
        if (dp[i] != null)
            return;
        if (eratos[i] == 0) {
            dp[i] = List.of(i);
            return;
        }
        dp[i] = new ArrayList<>();
        dp[i].add(eratos[i]);
        dfs(i / eratos[i]);
        dp[i].addAll(dp[i / eratos[i]]);
    }
}