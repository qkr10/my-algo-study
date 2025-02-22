#include <vector>
using namespace std;

int solution(vector<int> nums)
{
    int answer = 0;
    vector<int> count(200001);
    for (int n : nums) {
        if (count[n] == 0)
            answer++;
        count[n] = 1;
    }
    return answer > nums.size() / 2 ? nums.size() / 2 : answer;
}