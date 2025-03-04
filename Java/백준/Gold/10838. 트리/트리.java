//1시간 30분 소요

import java.util.*;
import java.io.*;

class Tree {
    int[][] tree; //부모, 색깔
    int[] visit;
    int lcaCount;
    public Tree(int N) {
        tree = new int[N][2];
        visit = new int[N];
    }
    private int lca(int a, int b) {
        lcaCount++;
        if (a == b)
            return a;
        for (int i = 0; i < 1001; i++) {
            visit[a] = lcaCount;
            if (a == tree[a][0])
                break;
            a = tree[a][0];
        }
        for (int i = 0; i < 1001; i++) {
            if (visit[b] == lcaCount)
                return b;
            b = tree[b][0];
        }
        return 0;
    }
    public void paint(int a, int b, int c) {
        int root = lca(a, b);
        while (a != root) {
            tree[a][1] = c;
            a = tree[a][0];
        }
        while (b != root) {
            tree[b][1] = c;
            b = tree[b][0];
        }
    }
    public int count(int a, int b) {
        HashSet<Integer> set = new HashSet<>();
        int root = lca(a, b);
        while (a != root) {
            set.add(tree[a][1]);
            a = tree[a][0];
        }
        while (b != root) {
            set.add(tree[b][1]);
            b = tree[b][0];
        }
        return set.size();
    }
    public void move(int a, int b) {
        tree[a][0] = b;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        var bw = new BufferedWriter(new OutputStreamWriter(System.out));
        var st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        Tree tree = new Tree(N);
        var sb = new StringBuilder();
        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            int R = Integer.parseInt(st.nextToken());
            int A = Integer.parseInt(st.nextToken());
            int B = Integer.parseInt(st.nextToken());
            if (R == 1) {
                int C = Integer.parseInt(st.nextToken());
                tree.paint(A, B, C);
            } else if (R == 2) {
                tree.move(A, B);
            } else if (R == 3) {
                bw.write(String.valueOf(tree.count(A, B)));
                bw.write("\n");
            }
        }
        bw.flush();
    }
}