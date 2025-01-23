/*
37분 소요
#### 풀이과정
1. T입력받음
2. F입력받음. 다음 F줄 마다 HashMap<String,int> Name2Id 을 이용해 숫자로 변환. 변환된 숫자 두개를 배열에 저장.
3. 입력된 순서대로 다시 처리함. 유니온 파인드로 두 친구 네트워크를 합침. 합칠때마다 친구 수도 합쳐서 출력.
*/

import java.util.*;
import java.io.*;

class UnionFind {
    public int[] parent, rank, count;
    public UnionFind(int size) {
        parent = new int[size];
        Arrays.fill(parent, -1);
        rank = new int[size];
        count = new int[size];
        Arrays.fill(count, 1);
    }
    public int union(int p1, int p2) {
        if (p1 == p2)
            return p1;
        if (rank[p1] == rank[p2]) {
            parent[p1] = p2;
            rank[p2]++;
            count[p2] += count[p1];
            return p2;
        }
        else if (rank[p1] > rank[p2]) {
            parent[p2] = p1;
            count[p1] += count[p2];
            return p1;
        }
        else {
            parent[p1] = p2;
            count[p2] += count[p1];
            return p2;
        }
    }
    public int find(int a) {
        if (parent[a] == -1)
            return a;
        return parent[a] = find(parent[a]);
    }
}

public class Main{
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        var sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        while (T-- != 0) {
            int F = Integer.parseInt(br.readLine());
            int[][] input = new int[F][2];
            HashMap<String, Integer> Name2Id = new HashMap<>();
            for (int i = 0; i < F; i++) {
                var st = new StringTokenizer(br.readLine());
                String a = st.nextToken();
                String b = st.nextToken();
                Name2Id.putIfAbsent(a, Name2Id.size());
                Name2Id.putIfAbsent(b, Name2Id.size());
                input[i][0] = Name2Id.get(a);
                input[i][1] = Name2Id.get(b);
            }
            
            UnionFind uf = new UnionFind(Name2Id.size());
            for (int i = 0; i < F; i++) {
                int aRoot = uf.find(input[i][0]);
                int bRoot = uf.find(input[i][1]);
                int p = uf.union(aRoot, bRoot);
                sb.append(uf.count[p]).append("\n");
            }
        }
        System.out.print(sb.toString());
    }
}