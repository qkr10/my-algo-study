/*
2시간 45분 소요

#### 내 문제점
1. 풀이과정 짜기전 손으로 한번 풀어본건 좋았으나, 잘못된 풀이를 떠올려서 결국 코드를 다 짜고나서야 잘못되었음을 알게됨.
    1. is-a, has-a 각각의 관계에 대해 명확하게 그래프 용어를 사용해서 재정의 해야 했음.
        1. 그래프 용어를 사용해서 각각의 관계를 정의하자면, 간선들에는 is와 has라는 종류가 있고, 단방향 간선임. 사이클은 없음.
        2. a is-a b 일때, is 간선을 따라 i노드에서 j노드로 가는 경로가 존재한다는 뜻임.
        3. a has-a b 일때, 간선의 종류에 관계없이 i->j 경로가 존재하고, 경로에 has 간선이 하나는 포함된다는 뜻임.
2. 자바 HashMap, Queue, HashSet, Iterator 각각에 대해 잊어버린 부분이 있었음.
    1. HashMap은 get()의 결과가 없을때 null을 반환함. values() 로 값을 순회가능.
    2. HashSet은 contains()로 값이 존재하는지를 조회할수 있음. iterator() 로 순회가능.
    3. Queue는 인터페이스이므로 ArrayDeque로 객체 생성해야 함. 여러 값을 담고 싶으면 Object[] 사용하기.
    4. Iterator의 메소드는 hasNext(), next().
    5. 검색하다가 알게 된 것.
        1. LinkedHashMap, LinkedHashSet 으로 더 빠른 순회 가능
3. 틀렸습니다를 받고 나서야 찾은 버그들.
    1. visit배열도 없이 M번의 쿼리마다 bfs 사용해서 시간초과됨.
        1. m=10000 * 관계수=10000 * 중복탐색횟수=10000^2
    2. 항상 !(a is-a b) == (a has-a b) 라고 가정함
        1. 문제 조건에 따르면 쿼리는 항상 관계를 추론할수 있게 주어진다고 했지만, a->b 또는 b->a 가 존재한다는 의미같음.
        2. a->b 가 항상 존재한다고 가정해야 !(a is-a b) == (a has-a b) 가 성립함.
    3. 어떤 a에 대해 a has-a b 가 성립하는 모든 b의 목록을 계산할때, dfs로 탐색하였음.
        1. dfs 자체는 문제가 없지만, has-a 라는 관계를 정확하게 정의하지 않아서, 목록에 빠진 것들이 있었음.
    4. bfs 의 visit 배열을 잘못 만듬. 큐에 has 간선 포함 여부를 넣으니, visit[has간선포함여부][노드] 형태여야 했음.

#### 풀이과정
1. 문자열 비교할때 String.equals() 사용해야 함에 주의.
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