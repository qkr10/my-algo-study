/*
35분 소요

#### 풀이과정1
1. 이 문제는 어떤 수열에서 최소한의 원소만 제거하여 가장 긴 증가수열을 만드는 문제로 볼수 있음.
2. 예를들어 문제의 예시는 3,5,6을 선택하면 가장 긴 증가수열이 되고, 이때 제거된 원소의 숫자인 4가 답이됨.
3. N^2 시간복잡도로 prev[] 를 구함. prev[i] = j < i 이고, i번 원소보다 j번 원소가 작을때 j값의 최댓값. 없으면 -1.
4. 이후, N 시간복잡도로 count[] 를 구함. count[i] = count[prev[i]] + 1. prev[i] 가 -1 일때는 1.
5. 정답은 N - max(count) 가 됨.

#### 풀이과정2
1. 이 문제는 어떤 수열에서 최소한의 원소만 제거하여 가장 긴 증가수열을 만드는 문제로 볼수 있음.
2. 예를들어 문제의 예시는 3,5,6을 선택하면 가장 긴 증가수열이 되고, 이때 제거된 원소의 숫자인 4가 답이됨.
3. prev[] 를 구함. prev[i] = j < i 이면서, input[i] > input[j] 일때 count[j] 가 최대가 되는 j 값. 없으면 -1.
4. prev[i] 를 구하자 마자 count[i] 를 구함. count[i] = count[prev[i]] + 1. prev[i] 가 -1 일때는 1.
5. 정답은 N - max(count) 가 됨.
*/

#include <iostream>
#include <algorithm>

using namespace std;

int main(void) {
    ios_base::sync_with_stdio(false);
    cin.tie(0);
    int N;
    cin >> N;
    int input[N];
    for (int i = 0; i < N; i++)
        cin >> input[i];
    int prev[N], count[N];
    for (int i = 0; i < N; i++) {
        // prev[i] 계산
        prev[i] = -1;
        int maxCountJ = 0;
        for (int j = i - 1; j >= 0; j--) {
            if (input[i] > input[j] && maxCountJ < count[j]) {
                prev[i] = j;
                maxCountJ = count[j];
                continue;
            }
        }
        
        // count[i] 계산
        if (prev[i] == -1) {
            count[i] = 1;
            continue;
        }
        count[i] = count[prev[i]] + 1;
    }
    cout << (N - *max_element(count, count+N));
    return 0;
}