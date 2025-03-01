import java.util.*;
import java.io.*;

class SegTree {
    int[] tree;
    int leafStart;
    public SegTree(int N) {
        tree = new int[N*4];
        leafStart = Integer.highestOneBit(N-1) << 1;
    }
    public void add(int p, int v) {
        p += leafStart;
        while (p != 0) {
            tree[p] += v;
            p >>= 1;
        }
    }
    public int get(int l, int r) {
        return get(l, r, 0, leafStart-1, 1);
    }
    public int get(int l, int r, int ln, int rn, int n) {
        if (l <= ln && rn <= r)
            return tree[n];
        if (rn < l || r < ln)
            return 0;
        int mid = (ln + rn) / 2;
        int a = get(l, r, ln, mid, n*2);
        int b = get(l, r, mid+1, rn, n*2+1);
        return a + b;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        var st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        SegTree seg = new SegTree(65536);
        int[] arr = new int[N];
        int count = 0;
        long sum = 0;
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(br.readLine());
            seg.add(arr[i], 1);
            count++;
            if (i >= K) {
                seg.add(arr[i-K], -1);
                count--;
            }
            
            if (count == K) {
                int target = (count+1)/2;
                int l = 0, r = 65536;
                while (l < r) {
                    int mid = (l+r)/2;
                    int current = seg.get(0, mid);
                    if (current < target) {
                        l = mid + 1;
                    } else {
                        r = mid;
                    }
                }
                sum += r;
            }
        }
        System.out.println(sum);
    }
}