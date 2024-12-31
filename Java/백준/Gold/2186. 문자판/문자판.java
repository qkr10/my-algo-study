import java.io.*;
import java.util.*;

class Problem {
    int k;
    String[] map;
    String word;
    int[][] deltaArr = {
        {1, 0},
        {-1, 0},
        {0, 1},
        {0, -1}
    };
    int[][][] memo;
    
    public Problem(int k, String[] map, String word) {
        this.k = k;
        this.map = map;
        this.word = word;
        this.memo = new int[map.length][map[0].length()][word.length() + 1];
        for (var a1 : memo)
            for (var a2 : a1)
                Arrays.fill(a2, -1);
    }
    public int solve() {
        int result = 0;
        for (int y = 0; y < map.length; y++)
            for (int x = 0; x < map[y].length(); x++)
                if (map[y].charAt(x) == word.charAt(0))
                    result += dfs(x, y, 0);
        return result;
    }
    public int dfs(int x, int y, int cur) {
        if (cur == word.length() - 1)
            return memo[y][x][cur] = 1;
        if (memo[y][x][cur] != -1)
            return memo[y][x][cur];
        
        int result = 0;
        for (int[] delta : deltaArr) {
            for (int i = 1; i <= k; i++) {
                int curY = y + delta[1] * i;
                int curX = x + delta[0] * i;
                if (curY < 0 || map.length <= curY)
                    break;
                if (curX < 0 || map[0].length() <= curX)
                    break;
                if (map[curY].charAt(curX) != word.charAt(cur + 1))
                    continue;
                result += dfs(curX, curY, cur + 1);
            }
        }
        return memo[y][x][cur] = result;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        String[] nmkStr = br.readLine().split(" ");
        int[] nmk = Arrays.stream(nmkStr).mapToInt(Integer::parseInt).toArray();
        String[] map = new String[nmk[0]];
        for (int i = 0; i < nmk[0]; i++)
            map[i] = br.readLine();
        String word = br.readLine();
        
        int result = new Problem(nmk[2], map, word).solve();
        System.out.println(result);
    }
}