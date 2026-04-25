#include <string>
#include <vector>
#include <set>

using namespace std;

using vi = vector<int>;
using vvi = vector<vi>;
using si = set<int>;

vi info, parent;
vvi adj;
int n, root;
si nexts;

int dfs(int cur, int sheep, int wolf) {
    wolf += info[cur];
    sheep += info[cur] ^ 1;
    if (sheep <= wolf)
        return 0;
    
    for (auto child : adj[cur])
    	nexts.insert(child);
    
    si temp = nexts;
    int ret = sheep;
    for (auto next : temp) {
        nexts.erase(next);
        ret = max(ret, dfs(next, sheep, wolf));
        nexts.insert(next);
    }
    
    for (auto child : adj[cur])
    	nexts.erase(child);
    
    return ret;
}

int solution(vector<int> info, vector<vector<int>> edges) {
    ::info = info;
    n = info.size();
    adj = vvi(n);
    parent = vi(n, -1);
    
    for (auto &edge : edges) {
        int a = edge[0];
        int b = edge[1];
        adj[a].push_back(b);
        parent[b] = a;
    }
    
    while (parent[root] != -1)
        root = parent[root];
    
    return dfs(root, 0, 0);
}