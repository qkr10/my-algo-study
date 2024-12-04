#include <iostream>
#include <vector>

using namespace std;

using vi = vector<int>;

int main(void) {
    ios_base::sync_with_stdio(false);
    cin.tie(0);
    cout.tie(0);
    
    int N, M;
    cin >> N >> M;
    vi input(N, 0);
    vi prefixSum(N+1, 0);
    for (int i = 0; i < N; i++) {
        cin >> input[i];
        prefixSum[i+1] = prefixSum[i] + input[i];
    }
    for (int i = 0; i < M; i++) {
        int a, b;
        cin >> a >> b;
        cout << (prefixSum[b] - prefixSum[a-1]) << '\n';
    }
}