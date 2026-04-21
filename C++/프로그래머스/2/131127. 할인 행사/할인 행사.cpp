#include <string>
#include <vector>
#include <map>

using namespace std;

int solution(vector<string> want, vector<int> number, vector<string> discount) {
    int answer = 0;
    
    int remain = 0;
    for (int i = 0; i < number.size(); i++)
        remain += number[i];
    
    map<string, int> product;
    for (int i = 0; i < number.size(); i++)
        product.insert({want[i], i});
    
    int e = -1;
    for (int s = 0; s < discount.size() - 9; s++) {
        while (s + 9 != e) {
            e++;
            auto iter = product.find(discount[e]);
            if (iter == product.end())
                continue;
            int index = iter->second;
            number[index]--;
            if (number[index] >= 0)
                remain--;
        }
        
        if (remain <= 0)
            answer++;
        
        auto iter = product.find(discount[s]);
        if (iter == product.end())
            continue;
        int index = iter->second;
        number[index]++;
        if (number[index] > 0)
            remain++;
    }
    
    return answer;
}