//1시간 43분 소요
//trie 첫 구현. 최대한 조금 검색하고 만들어봄. 이제 다른 사람들 코드 보기.

import java.util.*;
import java.io.*;

class Trie {
    boolean word = false;
    int boardNum = -1;
    Trie[] children = new Trie[26];
    String str;
    public Trie() {}
    public Trie(String[] words) {
        for (int i = 0; i < words.length; i++) {
            Trie cur = this;
            for (int j = 0; j < words[i].length(); j++) {
                if (cur.children[words[i].charAt(j) - 'A'] == null)
                    cur.children[words[i].charAt(j) - 'A'] = new Trie();
                cur = cur.children[words[i].charAt(j) - 'A'];
            }
            cur.word = true;
            cur.str = words[i];
        }
    }
    public Trie getSub(char ch) {
        return children[ch - 'A'];
    }
    public boolean isWord() {
        return word;
    }
    public int getBoardNum() {
        return boardNum;
    }
    public void setBoardNum(int n) {
        boardNum = n;
    }
    public String getStr() {
        return str;
    }
}

public class Main {
    static String res;
    static int score;
    static int count;
    static Trie trie;
    static char[][] board;
    static int boardNum;

    static int[][] delta = new int[][]{
            {0, 1},
            {0, -1},
            {1, 0},
            {-1, 0},
            {1, 1},
            {-1, -1},
            {1, -1},
            {-1, 1}
    };
    static int[] scores = new int[]{
            0, 0, 1, 1, 2, 3, 5, 11
    };

    static void dfs(int y, int x, int visit, Trie trie) {
        if (trie == null)
            return;
        if ((visit & 1<<(y*4+x)) != 0)
            return;

        if (trie.isWord() && trie.getBoardNum() != boardNum) {
            count++;
            String temp = trie.getStr();
            score += scores[temp.length()-1];
            if (temp.length() > res.length()
                    || (temp.length() == res.length() && temp.compareTo(res) < 0))
                res = temp;
            trie.setBoardNum(boardNum);
        }

        visit |= 1<<(y*4+x);
        if (Integer.bitCount(visit) == 8)
            return;
        for (int d = 0; d < 8; d++) {
            int yy = delta[d][0]+y;
            int xx = delta[d][1]+x;
            if (yy < 0 || 4 <= yy || xx < 0 || 4 <= xx)
                continue;
            dfs(yy, xx, visit, trie.getSub(board[yy][xx]));
        }
    }

    static void dfs() {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                dfs(i, j, 0, trie.getSub(board[i][j]));
    }

    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        var sb = new StringBuffer();

        int W = Integer.parseInt(br.readLine());
        String[] words = new String[W];
        for (int i = 0; i < W; i++)
            words[i] = br.readLine();
        trie = new Trie(words);

        br.readLine();

        int B = Integer.parseInt(br.readLine());
        board = new char[4][];
        for (boardNum = 0; boardNum < B; boardNum++) {
            for (int j = 0; j < 4; j++)
                board[j] = br.readLine().toCharArray();
            if (boardNum != B-1)
                br.readLine();

            count = 0;
            score = 0;
            res = "Z";

            dfs();

            sb.append(score).append(' ');
            sb.append(res).append(' ');
            sb.append(count).append('\n');
        }
        System.out.print(sb);
    }
}