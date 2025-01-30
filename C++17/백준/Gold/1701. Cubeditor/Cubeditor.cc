/*
#### 풀이과정
1. input = 입력된 문자열
2. table[i][j] = input[i:j] 의 전치사와 접미사가 길이가 k인 동일한 문자열일때, k의 최댓값.
3. max(table) 을 반환.
*/

#include <iostream>
#include <string>
#include <algorithm>
#include <vector>

using namespace std;

using vi = vector<int>;
using vvi = vector<vi>;

int main(void) {
    ios_base::sync_with_stdio(false);
    cin.tie(0);
    
    string input;
    cin >> input;
    
    vvi table(input.size(), vi(input.size()));
    int result = 0;
    for (int i = 0; i < input.size(); i++) {
        int prefixRight = i;
        for (int j = i + 1; j < input.size(); j++) {
            while (prefixRight != i && input[prefixRight] != input[j])
                prefixRight = i + table[i][prefixRight-1];
            if (input[prefixRight] == input[j])
                prefixRight++;
            table[i][j] = prefixRight - i;
            result = max(result, table[i][j]);
        }
    }
    cout << result;
}