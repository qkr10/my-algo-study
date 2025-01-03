#include <cstdio>
#include <vector>
#include <algorithm>

using namespace std;

using vi = vector<int>;

int func(vi &height, int &b, int h) {
    int time = 0;
    for (int i = 0; i < height.size(); i++) {
        time += h < i ? (i-h)*2*height[i] : (h-i)*height[i];
        b += (i-h)*height[i];
    }
    return time;
}

int bufferIndex;
constexpr int sz = 8 * 501 * 502;
char buffer[sz];

bool isNum() {
    char ch = buffer[bufferIndex];
    return '0' <= ch && ch <= '9';
}

int getNum() {
    while (!isNum())
        bufferIndex++;
    int ret = 0;
    while (isNum())
        ret = ret * 10 + buffer[bufferIndex++] - '0';
    return ret;
}

int main() {
    buffer[fread(buffer, 1, sz, stdin)] = 0;
    int N = getNum();
    int M = getNum();
    int B = getNum();
    
    vi height(257, 0);
    for (int i = 0; i < N; i++)
        for (int j = 0; j < M; j++)
            height[getNum()]++;
    
    int lo = 0, hi = 256;
    while (lo + 2 < hi) {
        int h1 = (lo * 2 + hi) / 3;
        int h2 = (lo + hi * 2) / 3;
        int b1 = B;
        int b2 = B;
        
        int time1 = func(height, b1, h1);
        int time2 = func(height, b2, h2);
        
        if (b2 < 0) {
            if (b1 < 0)
                hi = h1 - 1;
            else
                hi = h2 - 1;
            continue;
        }
        
        if (time1 >= time2) {
            lo = h1;
        } else {
            hi = h2;
        }
    }
    
    int answerTime = 501 * 501 * 257, answerHeight = 0;
    for (int h = lo; h <= hi; h++) {
        int b = B;
        int time = func(height, b, h);
        
        if (b < 0) break;
        
        if (answerTime >= time) {
            answerTime = time;
            answerHeight = h;
        }
    }
    
    printf("%d %d\n", answerTime, answerHeight);
}