#include <iostream>
#include <vector>
#include <algorithm>
#include <queue>
#include <functional>
#include <sstream>

using namespace std;

using vi = vector<int>;
using vvi = vector<vi>;
using ii = pair<int, int>;
using iii = pair<int, ii>;
using pq = priority_queue<iii, vector<iii>, greater<iii> >;

void dijkstra(vvi &input, vvi &output, int *start) {
    int maxY = input.size();
    int maxX = input[0].size();
    vvi visit(maxY, vi(maxX, 0));
    pq q;
    q.push(iii(0, ii(start[0], start[1])));
    visit[start[1]][start[0]] = 1;
    ii delta[] = {
        ii(1, 0),
        ii(-1, 0),
        ii(0, 1),
        ii(0, -1)
    };
    while (!q.empty()) {
        iii cur = q.top();
        q.pop();
        ii curPos = cur.second;
        int dist = cur.first + 1;
        for (int i = 0; i < 4; i++) {
            int newX = curPos.first + delta[i].first;
            int newY = curPos.second + delta[i].second;
            if (newX < 0 || maxX - 1 < newX) continue;
            if (newY < 0 || maxY - 1 < newY) continue;
            if (visit[newY][newX] == 1) continue;
            if (input[newY][newX] != 1) continue;
            q.push(iii(dist, ii(newX, newY)));
            visit[newY][newX] = 1;
            output[newY][newX] = dist;
        }
    }
}

int main(void) {
    ios_base::sync_with_stdio(false);
    cin.tie(0);
    cout.tie(0);

    int h, w;
    cin >> h >> w;
    vvi input(h, vi(w, 0));
    int start[2];
    for (int y = 0; y < h; y++) {
        for (int x = 0; x < w; x++) {
            cin >> input[y][x];
            if (input[y][x] == 2) {
                start[0] = x;
                start[1] = y;
            }
        }
    }
    vvi output(h, vi(w, 0));
    dijkstra(input, output, start);
    for (int y = 0; y < h; y++) {
        for (int x = 0; x < w; x++) {
            if (output[y][x] == 0 && input[y][x] == 1)
                output[y][x] = -1;
            cout << output[y][x] << ' ';
        }
        cout << '\n';
    }
}