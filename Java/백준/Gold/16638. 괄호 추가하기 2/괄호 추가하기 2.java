import java.util.*;
import java.io.*;

class UnionFind {
    int[] parent, size;
    long[] value;
    public UnionFind(int N, char[] line) {
        parent = new int[N];
        size = new int[N];
        value = new long[N];
        for (int i = 0; i < N; i++) {
            parent[i] = i;
            size[i] = 1;
        }
        for (int i = 0; i < N; i+=2)
            value[i] = line[i] - '0';
    }
    public int find(int a) {
        if (parent[a] == a) return a;
        return parent[a] = find(parent[a]);
    }
    public void union(int a, int b) {
        a = find(a);
        b = find(b);
        parent[a] = b;
        size[b] += size[a];
    }
    public long getValue(int a) {
        return value[find(a)];
    }
    public void setValue(int a, long v) {
        value[find(a)] = v;
    }
    public int getSize(int a) {
        return size[find(a)];
    }
}

public class Main {
    static long calc(long l, char op, long r) {
        if (op == '+') return l + r;
        else if (op == '-') return l - r;
        else return l * r;
    }
    
    static long calc(UnionFind uf, int s, int e) {
        long ans = 0;
        for (int i = s+1; i < e; i+=2) {
            if (uf.getSize(i) != 1)
                continue;
            ans = calc(uf.getValue(i-1), line[i], uf.getValue(i+1));
            uf.union(uf.find(i-1), i);
            uf.union(uf.find(i+1), i);
            uf.setValue(i, ans);
        }
        return ans;
    }
    
    static long calc(int[] braket) {
        long ans = 0;
        UnionFind uf = new UnionFind(line.length, line);
        int curmul = mul;
        if (braket != null)
            for (int i = 0; i < braket.length; i++) {
                int s = braket[i]*2, e = braket[i]*2+2;
                ans = calc(uf, s, e);
                curmul &= ~(1<<(s+1));
            }
        while (curmul != 0) {
            int opIdx = Integer.numberOfTrailingZeros(curmul);
            ans = calc(uf, opIdx-1, opIdx+1);
            curmul &= curmul-1;
        }
        if (uf.getSize(0) != line.length)
            ans = calc(uf, 0, line.length-1);
        
        return ans;
    }
    
    static long dfs(int opCount, int depth, int[] braket) {
        if (depth == braket.length)
            return calc(braket);
        
        long ans = Integer.MIN_VALUE;
        int avilableIndex = 0;
        if (depth != 0)
            avilableIndex = braket[depth-1] + 2;
        for (int i = avilableIndex; i < opCount; i++) {
            braket[depth] = i;
            ans = Math.max(ans, dfs(opCount, depth+1, braket));
        }
        return ans;
    }
        
    static char[] line;
    static int mul;
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        line = br.readLine().toCharArray();
        
        if (N == 1) {
            System.out.println(line[0]);
            return;
        }
        
        int opCount = N/2;
        int numCount = N - opCount;
        int braketMax = (opCount + 1) / 2;
        
        mul = 0;
        for (int i = 0; i < opCount; i++)
            if (line[i*2+1] == '*')
                mul |= 1 << (i*2+1);
        
        long ans = calc(null);
        for (int braketCount = 1; braketCount <= braketMax; braketCount++) {
            int[] braket = new int[braketCount];
            ans = Math.max(ans, dfs(opCount, 0, braket));
        }
        System.out.println(ans);
    }
}