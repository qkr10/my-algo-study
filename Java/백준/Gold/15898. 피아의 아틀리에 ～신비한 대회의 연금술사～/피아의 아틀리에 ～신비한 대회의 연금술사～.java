import java.util.*;
import java.io.*;

public class Main{
    static String color = "WRBGY";
    static int[] scoreTable = new int[] {0, 7, 5, 3, 2};
    
    static int[] rotate(int[][][] ingredient, int rot, int y, int x) {
        if (rot == 0) return ingredient[y][x];
        else if (rot == 1) return ingredient[3-x][y];
        else if (rot == 2) return ingredient[x][3-y];
        else return ingredient[3-y][3-x];
    }
    
    static void update(int[][][] furnace, int[][][] ingredient, int pos, int rot) {
        int sy = pos >> 1, sx = pos & 1;
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++) {
                int[] in = rotate(ingredient, rot, i, j);
                
                int effect = furnace[sy+i][sx+j][0];
                furnace[sy+i][sx+j][0] = Math.min(9, Math.max(0, effect + in[0]));
                
                if (in[1] != 0)
                    furnace[sy+i][sx+j][1] = in[1];
            }
    }
    
    static int[][][] furnace = new int[5][5][2];
    static int simulate(int[][][][] ingredients, int[] permutation) {
        for (int i = 0; i < 3; i++)
            update(furnace, ingredients[permutation[6+i]], permutation[3+i], permutation[i]);
        int res = 0;
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++) {
                res += scoreTable[furnace[i][j][1]] * furnace[i][j][0];
                furnace[i][j][1] = furnace[i][j][0] = 0;
            }
        return res;
    }
    
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[][][][] ingredients = new int[N][4][4][2];
        for (int[][][] ingredient : ingredients) {
            for (int i = 0; i < 4; i++) {
                var st = new StringTokenizer(br.readLine());
                for (int j = 0; j < 4; j++)
                    ingredient[i][j][0] = Integer.parseInt(st.nextToken());
            }
            for (int i = 0; i < 4; i++) {
                var st = new StringTokenizer(br.readLine());
                for (int j = 0; j < 4; j++)
                    ingredient[i][j][1] = color.indexOf(st.nextToken().charAt(0));
            }
        }
        
        //N개중 3개를 순서대로 사용하는 모든 경우의 수를 시뮬레이션 한다.
        int ans = 0;
        int[] permutation = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        while (true) {
            permutation[8]++;
            int cur = 8;
            while (cur > 5 && permutation[cur] == N) {
                permutation[cur] = 0;
                permutation[cur-1]++;
                cur--;
            }
            while (cur <= 5 && cur > 0 && permutation[cur] == 4) {
                permutation[cur] = 0;
                permutation[cur-1]++;
                cur--;
            }
            if (permutation[0] == 4)
                break;
            
            if (permutation[8] == permutation[7]
                || permutation[7] == permutation[6]
                || permutation[6] == permutation[8])
                continue;
            
            ans = Math.max(ans, simulate(ingredients, permutation));
        }
        System.out.println(ans);
    }
}