import java.util.*;
import java.io.*;

class Seg2D {
    int[][] minTree;
    int[][] maxTree;
    int[][] input;
    int N, leafStart;
    public Seg2D(int[][] mat) {
        input = mat;
        N = mat.length;
        minTree = new int[N * 4][N * 4];
        maxTree = new int[N * 4][N * 4];
        int start = Integer.highestOneBit(N) << 1;
        leafStart = start;
        for (int i = 0; i < N; i++) {
            System.arraycopy(mat[i], 0, minTree[start + i], start, N);
            Arrays.fill(minTree[start + i], start + N, start * 2, Integer.MAX_VALUE);
            System.arraycopy(mat[i], 0, maxTree[start + i], start, N);
        }
        for (int i = N; i < start; i++) {
            Arrays.fill(minTree[start + i], start, start * 2, Integer.MAX_VALUE);
        }
        while (start != 1) {
            start /= 2;

            for (int y = start; y < start * 2; y++)
                for (int x = start; x < start * 2; x++) {
                    minTree[y][x] = Integer.MAX_VALUE;
                    for (int i = 0; i < 4; i++) {
                        int cy = y*2 + ((i & 2) >> 1);
                        int cx = x*2 + (i & 1);
                        minTree[y][x] = Math.min(minTree[y][x], minTree[cy][cx]);
                        maxTree[y][x] = Math.max(maxTree[y][x], maxTree[cy][cx]);
                    }
                }
        }
    }
    public int[] get(int[] s, int b) {
        int[] e = new int[]{s[0] + b, s[1] + b};
        int[] n = new int[]{1, 1};
        int[] ns = new int[]{0, 0};
        int[] ne = new int[]{leafStart, leafStart};
        int[] result = new int[]{Integer.MAX_VALUE, 0};
        get(s, e, n, ns, ne, result);
        return result;
    }
    private void get(int[] s, int[] e, int[] n, int[] ns, int[] ne, int[] res) {
        int y = n[0], x = n[1];
        int nsy = ns[0], nsx = ns[1];
        int ney = ne[0], nex = ne[1];

        if (!isRangeOverlap(nsy, ney, s[0], e[0])
                || !isRangeOverlap(nsx, nex, s[1], e[1]))
            return;

        if (isRangeBInA(s[0], e[0], nsy, ney)
                && isRangeBInA(s[1], e[1], nsx, nex)) {
            res[0] = Math.min(res[0], minTree[y][x]);
            res[1] = Math.max(res[1], maxTree[y][x]);
            return;
        }

        int midy = (nsy + ney) / 2, midx = (nsx + nex) / 2;
        for (int i = 0; i < 4; i++) {
            int isUpChild = (i & 2) >> 1;
            int isDownChild = isUpChild ^ 1;
            int isRightChild = i & 1;
            int isLeftChild = isRightChild ^ 1;
            n[0] = y*2 + isUpChild;
            n[1] = x*2 + isRightChild;
            ns[0] = nsy * isDownChild + midy * isUpChild;
            ns[1] = nsx * isLeftChild + midx * isRightChild;
            ne[0] = ney * isUpChild + midy * isDownChild;
            ne[1] = nex * isRightChild + midx * isLeftChild;

            get(s, e, n, ns, ne, res);
        }
        n[0] = y;
        n[1] = x;
        ns[0] = nsy;
        ns[1] = nsx;
        ne[0] = ney;
        ne[1] = nex;
    }
    private boolean isRangeOverlap(int as, int ae, int bs, int be) {
        return isBetween(as, ae, bs) || isBetween(as, ae, be-1) || isRangeBInA(bs, be, as, ae);
    }
    private boolean isRangeBInA(int as, int ae, int bs, int be) {
        return isBetween(as, ae, bs) && isBetween(as, ae, be-1);
    }
    private boolean isBetween(int s, int e, int b) {
        return s <= b && b < e;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        int[] nbk = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
        int[][] mat = new int[nbk[0]][];
        for (int i = 0; i < nbk[0]; i++) {
            mat[i] = Arrays.stream(br.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }
        Seg2D seg = new Seg2D(mat);
        for (int i = 0; i < nbk[2]; i++) {
            int[] q = Arrays.stream(br.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .map(a -> a - 1)
                    .toArray();
            int[] res = seg.get(q, nbk[1]);
            System.out.println(res[1]-res[0]);
        }
    }
//    public static void main1(String[] args) throws IOException { //test code
//        for (int t = 0; t < 20; t++) {
//            var rand = new Random();
//            int[] nbk = {rand.nextInt(3, 6), 0, 1};
//            nbk[1] = rand.nextInt(1, nbk[0]);
//            int[][] mat = new int[nbk[0]][];
//            for (int i = 0; i < nbk[0]; i++) {
//                mat[i] = rand.ints(nbk[0], 0, 10).toArray();
//            }
//            int y = rand.nextInt(nbk[0] - nbk[1]);
//            int x = rand.nextInt(nbk[0] - nbk[1]);
//
//            System.out.println(String.join(" ", Arrays.stream(nbk).mapToObj(Integer::toString).toArray(String[]::new)));
//            for (int[] m : mat)
//                System.out.println(String.join(" ", Arrays.stream(m).mapToObj(Integer::toString).toArray(String[]::new)));
//            System.out.printf("%d %d\n", y+1, x+1);
//
//            Seg2D seg = new Seg2D(mat);
//            int[] res1 = seg.get(new int[]{y, x}, nbk[1]);
//            int[] res2 = {Integer.MAX_VALUE, 0};
//            for (int yy = 0; yy < nbk[1]; yy++) {
//                for (int xx = 0; xx < nbk[1]; xx++) {
//                    res2[0] = Math.min(res2[0], mat[y + yy][x + xx]);
//                    res2[1] = Math.max(res2[1], mat[y + yy][x + xx]);
//                }
//            }
//            if (res1[0] != res2[0] || res1[1] != res2[1]) {
//                System.out.println("fail");
//                System.out.println(Arrays.toString(res1));
//                System.out.println(Arrays.toString(res2));
//                break;
//            } else {
//                System.out.println("success");
//            }
//        }
//    }
}