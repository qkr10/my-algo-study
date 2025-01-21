//1시간 소요

import java.util.*;
import java.io.*;

class SegTree {
    private int[] tree;
    private int leafStart;
    private int size;
    public SegTree(int size) {
        tree = new int[size * 4];
        leafStart = Integer.highestOneBit(size-1) << 1;
        this.size = size;
        Arrays.fill(tree, 1);
    }
    public void setAll(int[] arr) {
        System.arraycopy(arr, 0, tree, leafStart, size);
        for (int curStart = leafStart >> 1; curStart != 0; curStart >>= 1)
            for (int i = 0; i < curStart; i++) {
                int cur = curStart + i;
                tree[cur] = tree[cur * 2] * tree[cur * 2 + 1];
            }
    }
    public void set(int pos, int value) {
        int node = leafStart + pos;
        while (node != 0) {
            tree[node] = value;
            value *= tree[node ^ 1];
            node /= 2;
        }
    }
    public int get(int s, int e) {
        return get(0, leafStart-1, s, e, 1);
    }
    public int get(int ns, int ne, int s, int e, int n) {
        if (s <= ns && ne <= e)
            return tree[n];
        int nm = (ns + ne) / 2;
        int ret = 1;
        if (s <= nm)
            ret *= get(ns, nm, s, e, n*2);
        if (nm < e)
            ret *= get(nm+1, ne, s, e, n*2+1);
        return ret;
    }
}

public class Main{
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        var sb = new StringBuilder();
        String line;
        char[] resultChars = {'-', '0', '+'};
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            var st = new StringTokenizer(line);
            int N = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(br.readLine());
            int[] arr = new int[N];
            for (int i = 0; i < N; i++) {
                arr[i] = Integer.parseInt(st.nextToken());
                if (arr[i] < 0)
                    arr[i] = -1;
                else if (arr[i] > 0)
                    arr[i] = 1;
            }

            SegTree seg = new SegTree(N);
            seg.setAll(arr);
            for (int i = 0; i < K; i++) {
                st = new StringTokenizer(br.readLine());
                char command = st.nextToken().charAt(0);
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                if (command == 'C') {
                    if (b < 0) b = -1;
                    else if (b > 0) b = 1;
                    seg.set(a - 1, b);
                }
                else if (command == 'P') {
                    int res = seg.get(a-1, b-1);
                    sb.append(resultChars[res+1]);
                }
            }
            sb.append('\n');
        }
        System.out.print(sb.toString());
    }
}