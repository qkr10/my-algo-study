#include <iostream>
#include <algorithm>
#include <vector>
#include <map>
#include <random>
#include <cmath>

using namespace std;

using vi = vector<int>;
using vvi = vector<vi>;
using mii = map<int, int>;
using ll = long long;

ll getInverse(ll x, int p) {
    int power = p - 2;
    ll ret = 1;
    while (power != 0) {
        if (power & 1) {
            ret = (ret * x) % p;
        }
        x = (x * x) % p;
        power >>= 1;
    }
    return ret;
}

int main(void) {
    ios_base::sync_with_stdio(false);
    cin.tie(0); cout.tie(0);
    
    int N, K;
    cin >> N >> K;
    
    int p = 1000000007;
    random_device rd;
    mt19937 gen(rd());
    uniform_int_distribution<int> dist(10101, (int)sqrt(p) - 1000);
    ll x = dist(gen);
    ll invX = getInverse(x, p);
    
    mii table;
    bool answer = false;
    for (int n = 0; n < N; n++) {
        int M;
        cin >> M;
        
        vi input(M);
        for (int i = 0; i < M; i++)
            cin >> input[i];
        
        if (K > M)
            continue;
        
        ll hash1 = 0, hash2 = 0, nextx = 1;

        for (int k = 0; k < K-1; k++) {
            hash1 = (hash1 + (input[k] * nextx) % p) % p;
            hash2 = (hash2 + (input[K - k - 2] * nextx) % p) % p;
            nextx = (nextx * x) % p;
        }

        for (int k = K-1; k < M; k++) {
            if (k != K-1) {
                hash1 = ((hash1 + p - input[k - K]) % p) * invX % p;
                hash2 = (hash2 + p - (input[k - K] * nextx) % p) % p;
            }
            hash1 = (hash1 + (input[k] * nextx) % p) % p;
            hash2 = ((hash2 * x) % p + input[k]) % p;
            
            int minh = min(hash1, hash2);
            int maxh = max(hash1, hash2);
            auto iter = table.find((int)maxh);
            if (table[(int)maxh] == n) {
                table[(int)maxh] = n + 1;
                if (n == N-1) {
                    answer = true;
                    break;
                }
            }
            if (minh != maxh)
                table[(int)minh] = -maxh;
        }
    }
    
    cout << (answer ? "YES" : "NO") << '\n';
}