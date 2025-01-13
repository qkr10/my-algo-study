import java.util.*;
import java.io.*;

/*
간략한 문제 풀이 과정
1. 그래프의 mst를 구하고, mst의 가중치의 합을 구함. <= 10000 * 10000 = 1억
2. (n-2) \* (n-1) / 2 \* t 를 구함 < 5억
3. 1과 2에서 구한 값을 더해서 출력 < 6억

------

구체적인 과정
1. n t m 입력 받기
2. m 개의 줄에 a b c 입력 받아서, Link[] 타입 배열에 간선 저장
	- Link 클래스는 a b c 를 입력받는 생성자 필요
3. arr 배열 정렬 (람다식 사용)
4. 크루스칼 알고리즘 사용하면서 mst의 가충치 합도 같이 구함(유니온 파인드 자료구조 사용)
	- 유니온 파인드 클래스를 만듬. 생성자는 원소의 갯수를 입력받고, union 연산은 rank 기반 최적화를, find 연산은 경로 압축 최적화를 사용.
5.  (n-2) \* (n-1) / 2 \* t 를 구함
6.  두 값을 더해서 출력.
*/

class Link {
    public int a, b, c;
    public Link(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
}

class UnionFind {
    private int[][] list; //list[i] = {i번째 원소의 부모, i번째 원소의 rank}
    public UnionFind(int n) {
        list = new int[n][2];
        for (int i = 0; i < n; i++)
            list[i][0] = i;
    }
    public void union(int a, int b) {
        int aRank = list[a][1];
        int bRank = list[b][1];
        if (bRank < aRank)
            list[b][0] = a;
        else if (aRank < bRank)
            list[a][0] = b;
        else {
            list[b][0] = a;
            list[a][1]++;
        }
    }
    public int find(int a) {
        if (a == list[a][0]) return a;
        return list[a][0] = find(list[a][0]);
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        String[] ntm = br.readLine().split(" ");
        int n = Integer.parseInt(ntm[0]);
        int m = Integer.parseInt(ntm[1]);
        int t = Integer.parseInt(ntm[2]);
        Link[] links = new Link[m];
        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken());
            links[i] = new Link(a, b, c);
        }
        
        //kruskal
        Arrays.sort(links, (a, b) -> a.c - b.c);
        UnionFind uf = new UnionFind(m);
        int result1 = 0;
        for (int i = 0; i < m; i++) {
            int aRoot = uf.find(links[i].a);
            int bRoot = uf.find(links[i].b);
            if (aRoot == bRoot)
                continue;
            uf.union(aRoot, bRoot);
            result1 += links[i].c;
        }
        
        int result2 = (n - 2) * (n - 1) / 2 * t;
        System.out.println(result1 + result2);
    }
}