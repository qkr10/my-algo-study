#include <vector>
#include <map>
#include <cmath>

using namespace std;

using ll = long long;

int reverse_count(int n, int remain_star, int count_plus) {
    if (n == 1) {
        if (count_plus > 0)
            return 0;
        return 1;
    }
    
    int ret = 0;
    if (remain_star > 0 && count_plus >= 2 && n % 3 == 0)
    	ret += reverse_count(n / 3, remain_star - 1, count_plus - 2);
    if (remain_star * 2 > count_plus)
	    ret += reverse_count(n - 1, remain_star, count_plus + 1);
    return ret;
}

int solution(int n) {
    int star = 1;
    for (; star < 30; star++) {
        ll min_count = pow(3, star) + 2 * star;
        ll max_count = 2 * pow(3, star) - 1;
        if (n <= max_count) {
            if (n < min_count)
                return 0;
            break;
        }
    }
    
    return reverse_count(n, star, 0);
}