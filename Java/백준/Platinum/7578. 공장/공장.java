//50분 소요

import java.io.*;
import java.util.*;

class SegTree {
    int[] tree;
    int leafStart;
    public SegTree(int n) {
        tree = new int[n * 4];
        leafStart = Integer.highestOneBit(n - 1) << 1;
    }
    public void set(int pos, int val) {
        pos += leafStart;
        while (pos != 0) {
            tree[pos] += val;
            pos >>= 1;
        }
    }
    public int get(int l, int r) {
        return get(l, r, 0, leafStart-1, 1);
    }
    public int get(int l, int r, int nl, int nr, int n) {
        if (r < nl || nr < l)
            return 0;
        if (l <= nl && nr <= r)
            return tree[n];
        int mid = (nl + nr) / 2;
        int a = get(l, r, nl, mid, n * 2);
        int b = get(l, r, mid+1, nr, n * 2 + 1);
        return a + b;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        
        int[] arr1idx = new int[1000001];
        var st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            int val = Integer.parseInt(st.nextToken());
            arr1idx[val] = i;
        }
        
        st = new StringTokenizer(br.readLine());
        SegTree seg = new SegTree(N);
        long sum = 0;
        for (int i = 0; i < N; i++) {
            int val = arr1idx[Integer.parseInt(st.nextToken())];
            seg.set(val, 1);
            sum += seg.get(val + 1, N);
        }
        System.out.println(sum);
    }
}