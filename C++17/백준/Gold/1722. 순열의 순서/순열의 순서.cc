/*
#### 풀이과정
1. findPermutation(N, K), findPermutationN(permutation), factorialDecomposition(n) 함수를 구현함
2. findPermutation(N, K) = K+1번째 순열 배열
    1. ret = (1 ... N)
    2. decK = factorialDecomposition(K)
    3. for (f, m) : decK
        1. tempIdx = N-f-1 + m
        3. swap(ret[N-f-1], ret[tempIdx])
        4. sort(ret, N-f, tempIdx)
	4. ret 반환
3. findPermutationN(permutation) = (permutation == findPermutation(N, K)) 이 되게하는 K 값을 구함.
	1. N = len(permutation)
	2. origin = (1, 2, ...N)
	3. ret = 0
	4. left = origin[i] != permutation[i] 인 i의 최소값. 두번째 반복부터는 left < i
	5. right = find(origin, permutation[left)
	5. ret += (N-left-1)! * (right - left)
    6. swap(origin[left], origin[right])
	7. sort(origin, left, N-1)
	8. is_not_sort(permutation, left, N-1) 인 동안 3-4부터 반복
	9. ret 반환
4. factorialDecomposition(n) = 다음과 같은 2차원 배열 ret. n = ret[i][0]!*ret[i][1]
    1. f = max_i(n > i!), m = n / f!
    2. ret.push_back((f, m))
    3. n -= f!*m 하고, 4-1부터 n != 0 동안 반복
    4. ret 반환
*/

#include <iostream>
#include <vector>
#include <array>
#include <algorithm>

using namespace std;

using vi = vector<int>;
using ai2 = array<int, 2>;
using vai2 = vector<ai2>;
using ll = long long;

ll factorial(int n) {
    ll ret = 1;
    for (int i = 1; i <= n; i++)
        ret *= i;
    return ret;
}

vai2 factorialDecomposition(ll n) {
    vai2 ret;
    int f = 20;
    while (n != 0) {
        while (n < factorial(f)) f--;
        int m = n / factorial(f);
        ai2 temp{f, m};
        ret.push_back(temp);
        n -= factorial(f) * m;
    }
    return ret;
}

vi findPermutation(int N, ll K) {
    vi ret(N);
    for (int i = 0; i < N; i++)
        ret[i] = i+1;

    vai2 decN = factorialDecomposition(K);
    for (auto [f, m] : decN) {
        int left = N - f - 1;
        int tempIdx = left + m;
        swap(ret[left], ret[tempIdx]);
        sort(ret.begin()+left+1, ret.end());
    }
    return ret;
}

ll findPermutationN(vi permutation) {
    int N = permutation.size();
    ll ret = 0;
    vi origin(N);
    for (int i = 0; i < N; i++)
        origin[i] = i+1;

    for (int i = 0; i < N; i++) {
        if (origin[i] == permutation[i])
            continue;

        int right = distance(origin.begin(), find(origin.begin(), origin.end(), permutation[i]));
        ret += factorial(N-i-1) * (right - i);
        swap(origin[i], origin[right]);
        sort(origin.begin() + i + 1, origin.end());
    }
    return ret;
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(0);
    cout.tie(0);

    int N, type;
    cin >> N >> type;
    if (type == 1) {
        ll a;
        cin >> a;
        for (int v : findPermutation(N, a-1))
            cout << v << ' ';
    }
    else {
        vi per(N);
        for (int& v : per)
            cin >> v;
        cout << findPermutationN(per) + 1;
    }
    return 0;
}