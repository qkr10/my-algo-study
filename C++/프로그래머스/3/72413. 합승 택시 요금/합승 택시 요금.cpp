#include <string>
#include <vector>
#include <utility>
#include <queue>
#include <cmath>

using namespace std;

using vi = vector<int>;
using vvi = vector<vi>;

using ii = pair<int, int>;
using vii = vector<ii>;
using vvii = vector<vii>;
using pq = priority_queue<ii>;

int n;
const int MAX = 0x0fffffff;

vi dijkstra(vvii graph, int s) {
    vi visit(n, MAX);
    pq q;
    q.push(ii(0, s));
    while (!q.empty()) {
        ii current = q.top();
        q.pop();
        int node = current.second;
        int dist = -current.first;
        
        if (visit[node] != MAX)
            continue;
        visit[node] = dist;
        
        for (ii next : graph[node]) {
            int next_node = next.first;
            int next_dist = dist + next.second;
            
            if (visit[next_node] != MAX)
                continue;
            q.push(ii(-next_dist, next_node));
        }
    }
    
    return visit;
}

int solution(int n, int s, int a, int b, vector<vector<int>> fares) {
    int answer = 0;
    
    ::n = n;
    s--;
    a--;
    b--;
    
    vvii graph(n, vii());
    for (vi edges : fares) {
        int x = edges[0] - 1;
        int y = edges[1] - 1;
        int d = edges[2];
        graph[x].push_back(ii(y, d));
        graph[y].push_back(ii(x, d));
    }
    
    vi result_s = dijkstra(graph, s);
    vi result_a = dijkstra(graph, a);
    vi result_b = dijkstra(graph, b);
    
    answer = MAX;
    for (int i = 0; i < n; i++) {
        answer = min(answer, result_s[i] + result_a[i] + result_b[i]);
    }
    
    return answer;
}