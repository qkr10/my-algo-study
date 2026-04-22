#include <string>
#include <vector>
#include <utility>

using namespace std;

using vi = vector<int>;
using vvi = vector<vi>;
using ll = long long;
using vll = vector<ll>;
using vvll = vector<vll>;

int n;
vi sales;
vvi links, adj;
vvll dp;

// treedp 문제이다.
// dp[i][j] = i 번 노드의 참석 여부가 j 일때 서브트리의 최적해
// min(dp[0][0], dp[0][1]) 이 정답이다.

// 점화식 초기값
// dp[리프노드 i][0] = 0
// dp[리프노드 i][1] = sales[i]

// 점화식
// childsum = sum(min(dp[i의 자식노드][j = 0 or 1]))
// childmin = min(dp[i의 자식노드][1] - dp[i의 자식노드][0])
// dp[i][1] = childsum
// dp[i][0] = childsum 에 적어도 하나의 j 가 1 이다 ? childsum : childsum + chlidmin
// dp[i][0] = childsum + max(childmin, 0)

void dfs(int cur) {
    if (adj[cur].size() == 0) {
        dp[cur][0] = 0;
        dp[cur][1] = sales[cur];
        return;
    }
    
    for (auto member : adj[cur]) {
        dfs(member);
    }
    
    ll child_sum = 0;
    ll child_min = 0x7fffffff;
    for (auto member : adj[cur]) {
        child_sum += min(dp[member][0], dp[member][1]);
        child_min = min(child_min, dp[member][1] - dp[member][0]);
    }
    
    dp[cur][1] = child_sum + sales[cur];
    dp[cur][0] = child_sum + max(child_min, 0LL);
}

int solution(vector<int> sales, vector<vector<int>> links) {
    int answer = 0;
    
    ::sales = sales;
    ::links = links;
    n = sales.size();
    
    adj = vvi(n);
    for (auto link : links) {
        int u = link[0];
        int v = link[1];
        adj[u-1].push_back(v-1);
    }
    
    dp = vvll(n, vll(2));
    
    dfs(0);
    
    return (int) min(dp[0][0], dp[0][1]);
}