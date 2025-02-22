import java.util.*;
import java.io.*;

class Fenwick {
    int[] tree;
    int N;
    public Fenwick(int n) {
        tree = new int[n+1];
        N = n;
    }
    public void add(int pos, int val) {
        while (pos <= N) {
            tree[pos] += val;
            pos += pos & -pos;
        }
    }
    public int get(int pos) {
        int ret = 0;
        while (0 < pos) {
            ret += tree[pos];
            pos &= pos - 1;
        }
        return ret;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        
        int N = Integer.parseInt(br.readLine());
        
        Fenwick fenwick = new Fenwick(N+1);
        int[] group = new int[N];
        var st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            group[i] = Integer.parseInt(st.nextToken());
            fenwick.add(i+1, group[i]);
        }
        
        var sb = new StringBuilder();
        int Q = Integer.parseInt(br.readLine());
        for (int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine());
            if (Integer.parseInt(st.nextToken()) == 1) {
                int pos = Integer.parseInt(st.nextToken());
                int val = Integer.parseInt(st.nextToken());
                fenwick.add(pos, val);
            } else {
                int val = Integer.parseInt(st.nextToken());
                int l = 1, r = N;
                while (l < r) {
                    int mid = (l + r) / 2;
                    int midVal = fenwick.get(mid);
                    if (midVal < val) {
                        l = mid+1;
                    } else {
                        r = mid;
                    }
                }
                sb.append(r).append('\n');
            }
        }
        System.out.println(sb);
    }
}