#include <string>
#include <vector>
#include <cmath>

using namespace std;

using ll = long long;
using vi = vector<int>;

bool available(int a, int b, vi g, vi s, vi w, vi t, ll time) {
    int n = g.size();
    int oa = a, ob = b;
    ll maxW = 0;
    for (int i = 0; i < n; i++) {
        ll amount = (time + t[i]) / (2 * t[i]) * w[i];
        int am = (int)min(amount, (ll)g[i] + s[i]);
        
        a -= min(g[i], am);
        b -= min(s[i], am);
        maxW += am;
    }
    if (0 < a || 0 < b)
        return false;
    if (maxW < oa + ob)
        return false;
    return true;
}

ll solution(int a, int b, vector<int> g, vector<int> s, vector<int> w, vector<int> t) {
    ll answer = -1;
    
    // 시간(=answer)에 대해 0부터 10^15 + 20까지 이분탐색
    ll low = 0, high = 1000000000000020;
    while (low < high) {
        ll mid = (low + high) / 2;
        bool result = available(a, b, g, s, w, t, mid);
        if (result) {
            high = mid;
        } else {
            low = mid + 1;
        }
    }
    answer = low;
    
    return answer;
}