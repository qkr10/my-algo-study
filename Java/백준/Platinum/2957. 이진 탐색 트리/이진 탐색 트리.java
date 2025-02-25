import java.util.*;
import java.io.*;

class Tree {
    int[] parent, left, right, count;
    TreeSet<Integer> set;
    public Tree(int N, int root) {
        parent = new int[N];
        left = new int[N];
        right = new int[N];
        count = new int[N];
        for (int i = 0; i < N; i++)
            left[i] = right[i] = parent[i] = i;
        set = new TreeSet<>();
        set.add(root);
    }
    public int push(int data) {
        var p = set.higher(data);
        boolean isLeftChild = true;
        if (p == null || left[p] != p) {
            p = set.lower(data);
            isLeftChild = false;
        }
        
        set.add(data);
        parent[data] = p;
        if (isLeftChild)
            left[p] = data;
        else
            right[p] = data;
        
        return count[data] = count[p] + 1;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        var sb = new StringBuilder();
        
        Tree tree = new Tree(N, Integer.parseInt(br.readLine())-1);
        long count = 0;
        sb.append(0).append('\n');
        for (int i = 1; i < N; i++) {
            int a = Integer.parseInt(br.readLine()) - 1;
            count += tree.push(a);
            sb.append(count).append('\n');
        }
        
        System.out.print(sb);
    }
}