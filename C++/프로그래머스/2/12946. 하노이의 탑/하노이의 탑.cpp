#include <string>
#include <vector>
#include <utility>

using namespace std;

using vi = vector<int>;
using vvi = vector<vi>;
using ii = pair<int, int>;
using vii = vector<ii>;

int n;
vvi answer;
vii size_to_pos;
vvi pos_to_size;

void dfs(int size, int pos) {
    int prev_pos = size_to_pos[size].first;
    int prev_height = size_to_pos[size].second;
    
    if (prev_height + 1 < pos_to_size[prev_pos].size())
        dfs(pos_to_size[prev_pos][prev_height+1], pos^prev_pos);
    
    answer.push_back(vi{prev_pos, pos});
    pos_to_size[prev_pos].pop_back();
    size_to_pos[size] = ii(pos, pos_to_size[pos].size());
    pos_to_size[pos].push_back(size);
    
    if (size > 1)
        dfs(size-1, pos);
}

vector<vector<int>> solution(int n) {
    ::n = n;
    
    size_to_pos = vii(n+1, ii(1, 0));
    pos_to_size = vvi(4, vi());
    
    for (int i = 0; i < n; i++) {
        size_to_pos[i+1].second = n-i-1;
        pos_to_size[1].push_back(n-i);
    }
    
    dfs(n, 3);
    
    return answer;
}