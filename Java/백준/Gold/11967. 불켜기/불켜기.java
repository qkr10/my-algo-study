import java.util.*;
import java.io.*;

class Room {
    public List<int[]> switches;
    public Room() {
        switches = new ArrayList<>();
    }
}

public class Main {
    public static int[][] delta = {
        {0, 1},
        {1, 0},
        {0, -1},
        {-1, 0}
    };
    
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        String[] nm = br.readLine().split(" ");
        int N = Integer.parseInt(nm[0]);
        int M = Integer.parseInt(nm[1]);
        Room[][] map = new Room[N][N];
        for (int i = 0; i < M; i++) {
            String[] line = br.readLine().split(" ");
            int[] sw = Arrays.stream(line)
                .mapToInt(Integer::parseInt).map(a -> a - 1).toArray();
            if (map[sw[0]][sw[1]] == null)
                map[sw[0]][sw[1]] = new Room();
            map[sw[0]][sw[1]].switches.add(new int[]{sw[2], sw[3]});
        }
        
        TreeSet<int[]> roomToVisit = new TreeSet<>((a, b) -> {
            if (b[0] != a[0]) return b[0] - a[0];
            if (b[1] != a[1]) return b[1] - a[1];
            return b[2] - a[2];
        });
        roomToVisit.add(new int[]{1, 0, 0}); //0: light, 1~2: coordinate
        
        boolean[][] visit = new boolean[N][N];
        boolean[][] light = new boolean[N][N];
        visit[0][0] = light[0][0] = true;
        
        int result = 1;
        while (!roomToVisit.isEmpty()) {
            int[] cur = roomToVisit.pollFirst();
            if (cur[0] == 0)
                break;
            
            if (map[cur[1]][cur[2]] != null) {
                for (int[] sw : map[cur[1]][cur[2]].switches) {
                    if (light[sw[0]][sw[1]])
                        continue;
                    light[sw[0]][sw[1]] = true;
                    result++;
                    if (visit[sw[0]][sw[1]]) {
                        roomToVisit.remove(new int[]{0, sw[0], sw[1]});
                        roomToVisit.add(new int[]{1, sw[0], sw[1]});
                    }
                }
            }
            
            for (int[] d : delta) {
                int[] next = new int[]{0, cur[1] + d[0], cur[2] + d[1]};
                if (next[1] < 0 || N <= next[1] || next[2] < 0 || N <= next[2])
                    continue;
                if (visit[next[1]][next[2]])
                    continue;
                visit[next[1]][next[2]] = true;
                next[0] = light[next[1]][next[2]] ? 1 : 0;
                roomToVisit.add(next);
            }
        }
        System.out.println(result);
    }
}