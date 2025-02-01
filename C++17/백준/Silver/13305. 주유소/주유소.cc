/*
20분 소요

#### 풀이과정
1. 손으로 풀어보기
2. len[i] = i에서 i+1로 가는 도로의 길이, cost[i] = i에서 기름가격
3. minCost[i] = min(cost[0...i]), result += minCost[i] * len[i]
4. 정답이 int 를 넘어섬에 주의
*/

#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;
using ll = long long int;

int main(void) {
    ios_base::sync_with_stdio(false);
    cin.tie(0);
    
    int N;
    cin >> N;
    int len[N] = {0,};
    for (int i = 0; i < N-1; i++)
        cin >> len[i];
    int minCost = 0x7FFFFFFF;
    ll result = 0;
    for (int i = 0; i < N; i++) {
        int temp;
        cin >> temp;
        minCost = min(minCost, temp);
        result += (ll)minCost * len[i];
    }
    cout << result;
    return 0;
}