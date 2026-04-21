#include <string>
#include <vector>

using namespace std;

bool isPerfect(string s) {
    int count = 0;
    for (int i = 0; i < s.size(); i++) {
        count += s[i] == '(' ? 1 : -1;
        if (count < 0)
            return false;
    }
    return true;
}

int splitAt(string s) {
    int count = 0;
    for (int i = 0; i < s.size(); i++) {
        count += s[i] == '(' ? 1 : -1;
        if (count == 0)
            return i;
    }
    return s.size() - 1;
}

string solution(string p) {
    string answer = "";
    if (p.size() == 0)
	    return answer;
    
    if (isPerfect(p))
        return p;
    
    int index = splitAt(p);
    
    string u = p.substr(0, index+1);
    string v = index == p.size() - 1 ? "" : p.substr(index+1, p.size()-index-1);
    
    if (isPerfect(u)) {
        return u + solution(v);
    }
    
    answer = "(" + solution(v) + ')';
    for (int i = 1; i < u.size()-1; i++) {
        answer.push_back(u[i] == '(' ? ')' : '(');
    }
    
    return answer;
}