/*
2시간 45분 소요

#### 풀이과정
1. 문자열 비교할때 String.equals() 사용해야함에 주의.
2. Node 클래스에 has[], is[], name 세가지 필드 추가.
3. 관계를 N번 입력받으며, 모든 노드에 대해 nodeHashMap.add(node.name, node) 함.
    1. 이때, x is-a x 같은 입력은 무시함.
4. 관계를 M번 입력받으며 관계 종류별로 탐색을 진행
	1. a is-a b 이면, queue.add(a), while (cur = queue.poll() != null)
		1. if (cur == b) true, queue.addAll(cur.is)
	2. a has-a b 이면, queue.addAll(a.has), whlie(cur = queue.poll() != null)
		1. if (cur == b) true, queue.addAll(cur.is, cur.has)
*/

import java.util.*;
import java.io.*;

class Node {
    public String name;
    public List<Node> is;
    public List<Node> has;
    public HashSet<Node> isSet;
    public HashSet<Node> hasSet;
    public Node(String name) {
        this.name = name;
        this.is = new ArrayList<>();
        this.has = new ArrayList<>();
    }
}

public class Main {
    static void dfs(Node cur) {
        if (cur.isSet != null)
            return;
        cur.isSet = new HashSet<>();

        cur.isSet.add(cur);
        for (Node child : cur.is) {
            dfs(child);
            cur.isSet.addAll(child.isSet);
        }
    }
    static void bfs(Node cur) {
        if (cur.hasSet != null)
            return;
        cur.hasSet = new HashSet<>();
        
        HashSet<Node>[] visit = new HashSet[]{
            new HashSet<Node>(), new HashSet<Node>()
        };
        Queue<Object[]> queue = new ArrayDeque<>();
        queue.add(new Object[]{false, cur});
        Object[] obj;
        while ((obj = queue.poll()) != null) {
            boolean count = (Boolean)obj[0];
            Node node = (Node)obj[1];
            if (count)
                cur.hasSet.add(node);
            
            var iter = node.isSet.iterator();
            Node iterNext;
            while (iter.hasNext())
                if (!visit[count?1:0].contains(iterNext = iter.next())) {
                    queue.add(new Object[]{count, iterNext});
                    visit[count?1:0].add(iterNext);
                }
            for (Node next : node.has)
                if (!visit[1].contains(next)) {
                    queue.add(new Object[]{true, next});
                    visit[1].add(next);
                }
        }
    }

    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        var st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        HashMap<String, Node> nodeHashMap = new HashMap<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            String a = st.nextToken(), rel = st.nextToken(), b = st.nextToken();
            Node na = nodeHashMap.get(a), nb = nodeHashMap.get(b);
            if (na == null)
                nodeHashMap.put(a, na = new Node(a));
            if (a.equals(b))
                continue;
            if (nb == null)
                nodeHashMap.put(b, nb = new Node(b));
            if (rel.equals("is-a"))
                na.is.add(nb);
            else
                na.has.add(nb);
        }

        for (Node value : nodeHashMap.values())
            dfs(value);
        for (Node value : nodeHashMap.values())
            bfs(value);

        var sb = new StringBuilder();
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            String a = st.nextToken(), rel = st.nextToken(), b = st.nextToken();
            Node na = nodeHashMap.get(a), nb = nodeHashMap.get(b);

            boolean result = false;
            if (rel.equals("is-a")) result = na.isSet.contains(nb);
            else result = na.hasSet.contains(nb);

            sb.append("Query ").append(i+1).append(": ");
            if (result)
                sb.append("true\n");
            else
                sb.append("false\n");
        }
        System.out.print(sb.toString());
    }
}