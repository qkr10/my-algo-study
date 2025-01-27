/*
35분 소요

#### 내 문제점
1. input[i], prev[i], count[i] 와 같이 수열로 생각을 표현하는 게 습관화 되지 않아서 풀이과정을 쓰는게 느림.
2. 첫 풀이과정을 쓸때 깊게 생각하지 않아서, 코드를 전부 짠다음 풀이과정을 고쳐야 했음.
    1. 풀이과정 3번에서 j의 최댓값을 구하는게 아니라, count[j]의 최댓값을 구해야 했음.
    2. 문제 예시처럼 3 7 5 2 6 1 4 과 같은 입력에서, prev[]가 -1 0 0 -1 3 3 5 처럼 내 의도와 다르게 계산됨.
    3. 즉, 한번만 prev[] 을 손으로 계산해 보았다면, 풀이 시간을 많이 줄일수 있었음.
3. std::max() 로 배열의 최댓값을 찾는 법을 잘 몰라서 해멤. 결국 std::max_element() 로 포인터를 구해서 사용함.
4. 이 문제는 lis의 길이를 구하는 문제임. O(n^2) 로 풀었지만, 이진 탐색 트리로 O(nlogn) 까지 줄일수 있음.
    1. 웰노운 문제도 풀이를 잘 몰라서 헤멨다는 점에서 반성해야함.

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
