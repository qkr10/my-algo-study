//1시간 30분 소요

import java.util.*;
import java.io.*;

//1. n, arr[n][3] 을 각각 입력받기
//2. arr[][0] 기준으로 오름차순 정렬시킨것을 arr0, arr[0][2] 기준으로 정렬시킨것을 arr2 라고 정의
//3. arr1를 하나씩 treeset 에 추가하면서, arr2 순서대로 treeset 에서 제거함.
//4. 추가 또는 제거할때마다 treeset 의 최대값이 변경되는지 확인하고 변경되면 x좌표와 높이를 출력함.
//5. 좌표가 겹칠수 있으므로, 한번에 여러 건물을 건너뛸수 있어야 함.

public class Main {
    static void add(TreeMap<Integer, Integer> map, int key, int val) {
        map.compute(key, (a, b)->{
            if (b == null) return val;
            else if (b + val == 0) return null;
            else return b + val;
        });
    }
    
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[][] arr0 = new int[N][3], arr2 = new int[N][3];
        for (int i = 0; i < N; i++) {
            var st = new StringTokenizer(br.readLine());
            arr2[i][0] = arr0[i][0] = Integer.parseInt(st.nextToken());
            arr2[i][1] = arr0[i][1] = Integer.parseInt(st.nextToken());
            arr2[i][2] = arr0[i][2] = Integer.parseInt(st.nextToken());
        }
        
        Arrays.sort(arr0, (a, b)->a[0] - b[0]);
        Arrays.sort(arr2, (a, b)->a[2] - b[2]);
        
        var sb = new StringBuffer();
        int i = 0, j = 0;
        TreeMap<Integer, Integer> map = new TreeMap<>();
        //높이의 중복을 허용하기 위해 set 대신 map 을 사용.
        //값부분에 높이가 중복된 횟수를 저장함.
        map.put(0, 1);
        while (i != N) {
            int maxH = map.lastKey();
            while (j < N && arr2[j][2] < arr0[i][0]) {
                add(map, arr2[j][1], -1);
                while (j+1 < N && arr2[j][2] == arr2[j+1][2])
                    add(map, arr2[++j][1], -1);
                int curMaxH = map.lastKey();
                if (curMaxH < maxH) {
                    sb.append(arr2[j][2]).append(' ').append(curMaxH).append(' ');
                    maxH = curMaxH;
                }
                j++;
            }
            
            maxH = map.lastKey();
            while (j < N && arr2[j][2] == arr0[i][0])
                add(map, arr2[j++][1], -1);
            
            add(map, arr0[i][1], 1);
            while (i+1 < N && arr0[i][0] == arr0[i+1][0])
                add(map, arr0[++i][1], 1);
            if (maxH != map.lastKey())
                sb.append(arr0[i][0]).append(' ').append(map.lastKey()).append(' ');
            i++;
        }
        while (j != N) {
            int maxH = map.lastKey();
            add(map, arr2[j][1], -1);
            while (j+1 < N && arr2[j][2] == arr2[j+1][2])
                add(map, arr2[++j][1], -1);
            if (maxH != map.lastKey()) {
                sb.append(arr2[j][2]).append(' ').append(map.lastKey()).append(' ');
            }
            j++;
        }
        System.out.println(sb);
    }
}