#include <string>
#include <vector>

using namespace std;

using vi = vector<int>;
using vvi = vector<vi>;

int k, n, root, max_sum;
vi num, subtree_sum;
vvi links;

int get_sum(int cur) {
    if (cur == -1)
        return 0;
    if (subtree_sum[cur] != 0)
        return subtree_sum[cur];
    
    int left = get_sum(links[cur][0]);
    int right = get_sum(links[cur][1]);
    return subtree_sum[cur] = num[cur] + left + right;
}

int max_count, remain_k;
bool dfs_result;
int dfs(int cur) {
    //cur이 루트인 서브트리에서 잘라 낼 부분을 잘라낸 후, cur 을 포함한 그룹의 인원을 반환함
    if (cur == -1 || !dfs_result)
        return 0;
    
    int left = links[cur][0];
    int right = links[cur][1];
    int cur_sum = subtree_sum[cur];
    int left_sum = left == -1 ? 0 : subtree_sum[left];
    int right_sum = right == -1 ? 0 : subtree_sum[right];
    
    int left_remain = dfs(left);
    int right_remain = dfs(right);
    int cur_remain = num[cur] + left_remain + right_remain;
    
    if (cur_remain <= max_count)
        return cur_remain;
    
    int left_cut_remain = num[cur] + right_remain;
    int right_cut_remain = num[cur] + left_remain;
    int min_remain = min(left_cut_remain, right_cut_remain);
    
    if (min_remain <= max_count) {
        remain_k--;
        dfs_result &= remain_k > 0;
        return min_remain;
    }
    
    remain_k -= 2;
    dfs_result &= remain_k > 0 && num[cur] <= max_count;
    return num[cur];
}

bool available(int max_count) {
    ::max_count = max_count;
    ::remain_k = k;
    dfs_result = true;
    dfs(root);
    return dfs_result;
}

int binary_search() {
    int l = 0, r = max_sum;
    while (l < r) {
        int mid = (l + r) / 2;
        if (available(mid)) {
            r = mid;
        } else {
            l = mid + 1;
        }
    }
    return l;
}

int solution(int k, vector<int> num, vector<vector<int>> links) {
    ::k = k;
    ::num = num;
    ::links = links;
    n = num.size();
    
    subtree_sum = vi(n, 0);
    for (int i = 0; i < n; i++) {
	    get_sum(i);
        if (max_sum < subtree_sum[i]) {
            max_sum = subtree_sum[i];
            root = i;
        }
    }
    
    return binary_search();
}