#include <string>
#include <vector>

using namespace std;

using ll = long long;

ll nCr(int n, int r) {
    ll numerator = 1;
    for (int i = n; i > r; i--)
        numerator *= i;
    ll denominator = 1;
    for (int i = 1; i <= r; i++)
        denominator *= i;
    return numerator / denominator;
}

ll catalan(int n) {
    ll numerator = nCr(2*n, n);
    ll denominator = n + 1;
    return numerator / denominator;
}

int solution(int n) {
    int answer = catalan(n);
    return answer;
}