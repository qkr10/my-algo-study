import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        var in = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(in.readLine());
        
        int sharkX = 0, sharkY = 0;
        int sharkSize = 2, sharkCount = 0;
        int[][] map = new int[N][N];
        for (int i = 0; i < N; i++) {
            String[] line = in.readLine().split(" ");
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(line[j]);
                if (map[i][j] == 9) {
                    sharkX = j;
                    sharkY = i;
                    map[i][j] = 0;
                }
            }
        }
        
        int time = 0;
        int[][] delta = {{-1, 0},{0, -1},{0, 1},{1, 0}};
        while (true) {
            int foodX = -1, foodY = -1, foodDist = 500;
            
            int[][] floodFill = new int[N][N];
            int floodFillMaxValue = 0;
            Deque<Integer> Q = new ArrayDeque<Integer>();
            Q.add((sharkX << 8) + sharkY);
            while (Q.size() != 0) {
                int sXY = Q.remove();
                int sX = sXY >> 8, sY = sXY & 0b11111111;
                if (floodFillMaxValue < floodFill[sY][sX]) {
                    if (foodX != -1)
                        break;
                    floodFillMaxValue = floodFill[sY][sX];
                }
                for (int i = 0; i < 4; i++) {
                    int newX = sX + delta[i][1];
                    int newY = sY + delta[i][0];
                    int newXY = (newX << 8) + newY;
                    
                    if (newX < 0 || newX >= N)
                        continue;
                    if (newY < 0 || newY >= N)
                        continue;
                    if (map[newY][newX] > sharkSize)
                        continue;
                    if (map[newY][newX] == sharkSize || map[newY][newX] == 0) {
                        if (floodFill[newY][newX] != 0)
                            continue;
                        Q.add(newXY);
                        floodFill[newY][newX] = floodFill[sY][sX] + 1;
                    }
                    else if (map[newY][newX] < sharkSize) {
                        if (foodX != -1) {
                            if (foodY < newY)
                                continue;
                            if (foodY == newY && foodX < newX)
                                continue;
                        }
                        foodX = newX;
                        foodY = newY;
                        foodDist = floodFill[sY][sX] + 1;
                    }
                }
            }
            
            if (foodX == -1)
                break;
            
            time += foodDist;
            sharkX = foodX;
            sharkY = foodY;
            sharkCount++;
            if (sharkCount == sharkSize) {
                sharkSize++;
                sharkCount = 0;
            }
            map[foodY][foodX] = 0;
        }
        System.out.println(time);
    }
}