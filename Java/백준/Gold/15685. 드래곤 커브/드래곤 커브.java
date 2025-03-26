import java.util.*;
import java.io.*;

public class Main {
    static List<List<Integer>> makeDragonCurve() {
        List<List<Integer>> ans = new ArrayList<>();
        ans.add(new ArrayList<>());
        ans.get(0).add(0);
        for (int i = 1; i <= 10; i++) {
            var prev = ans.get(i-1);
            var cur = new ArrayList<>(prev);
            for (int j = prev.size()-1; j >= 0; j--)
                cur.add((prev.get(j) + 1) % 4);
            ans.add(cur);
        }
        return ans;
    }
    
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        List<List<Integer>> dragonCurve = makeDragonCurve();
        boolean[][] map = new boolean[101][101];
        for (int i = 0; i < N; i++) {
            var st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            int g = Integer.parseInt(st.nextToken());
            List<Integer> curve = dragonCurve.get(g);
            map[y][x] = true;
            for (var dir : curve) {
                switch ((d + dir) % 4) {
                    case 0:
                        x++;
                        break;
                    case 1:
                        y--;
                        break;
                    case 2:
                        x--;
                        break;
                    case 3:
                        y++;
                        break;
                }
                map[y][x] = true;
            }
        }
        
        int ans = 0;
        for (int i = 0; i < 100; i++)
            for (int j = 0; j < 100; j++)
                if (map[i][j] && map[i][j+1] && map[i+1][j] && map[i+1][j+1])
                    ans++;
        System.out.println(ans);
    }
}