#include <string>
#include <vector>
#include <map>

using namespace std;

using vi = vector<int>;
using vvi = vector<vi>;
using mii = map<int, int>;
using vmii = vector<mii>;

vvi nodeinfo;
vi preorder, postorder;
vmii pos;
int n;

void dfs(int cur_y, int cur_x, int min_x, int max_x) {
    int cur_num = pos[cur_y][cur_x];
    preorder.push_back(cur_num);
    
    int next_y = cur_y - 1;
    while (next_y >= 0 && pos[next_y].size() == 0) next_y--;
    if (next_y >= 0) {
        int left_x = -1, right_x = -1;
        
        auto iter = pos[next_y].upper_bound(cur_x);
        if (iter != pos[next_y].end() && iter->first <= max_x)
            right_x = iter->first;
        
        if (iter != pos[next_y].begin()) {
            iter--;
            if (iter->first >= min_x)
                left_x = iter->first;
        }
        
        if (left_x != -1)
	        dfs(next_y, left_x, min_x, cur_x);
        if (right_x != -1)
	        dfs(next_y, right_x, cur_x, max_x);
    }
    
    postorder.push_back(cur_num);
}

vector<vector<int>> solution(vector<vector<int>> nodeinfo) {
    ::nodeinfo = nodeinfo;
    n = nodeinfo.size();
    
    int max_y = 0;
    for (int i = 0; i < n; i++)
        max_y = max(max_y, nodeinfo[i][1]);
    
    pos = vmii(max_y+1);
    for (int i = 0; i < n; i++) {
        int x = nodeinfo[i][0];
        int y = nodeinfo[i][1];
        pos[y][x] = i+1;
    }
    
    dfs(max_y, pos[max_y].begin()->first, 0, 100000);
    return {preorder, postorder};
}