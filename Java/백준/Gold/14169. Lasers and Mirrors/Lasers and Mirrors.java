import java.util.*;
import java.io.*;
import java.util.stream.Stream;

public class Main{
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        String[] nlb = br.readLine().split(" ");
        int N = Integer.parseInt(nlb[0]);
        int[] L = new int[]{
                Integer.parseInt(nlb[1]),
                Integer.parseInt(nlb[2])
        };
        int[] B = new int[]{
                Integer.parseInt(nlb[3]),
                Integer.parseInt(nlb[4])
        };
        List<int[]> fenceList = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            String[] line = br.readLine().split(" ");
            fenceList.add(new int[]{
                    Integer.parseInt(line[0]),
                    Integer.parseInt(line[1])
            });
        }

        HashMap<Integer, Integer> X2NewX = new HashMap<>();
        HashMap<Integer, Integer> Y2NewY = new HashMap<>();
        Stream.concat(fenceList.stream(), Stream.of(L, B))
                .sorted((a, b) -> a[0] - b[0])
                .forEachOrdered(a -> {
                    X2NewX.putIfAbsent(a[0], X2NewX.size());
                });
        Stream.concat(fenceList.stream(), Stream.of(L, B))
                .sorted((a, b) -> a[1] - b[1])
                .forEachOrdered(a -> {
                    Y2NewY.putIfAbsent(a[1], Y2NewY.size());
                });

        List<Set<Integer>> NewX2IndexList = new ArrayList<>();
        for (int i = 0; i < X2NewX.size(); i++)
            NewX2IndexList.add(new TreeSet<>());
        List<Set<Integer>> NewY2IndexList = new ArrayList<>();
        for (int i = 0; i < Y2NewY.size(); i++)
            NewY2IndexList.add(new TreeSet<>());
        for (int i = 0; i < N; i++) {
            int[] f = fenceList.get(i);
            f[0] = X2NewX.get(f[0]);
            f[1] = Y2NewY.get(f[1]);
            NewX2IndexList.get(f[0]).add(i);
            NewY2IndexList.get(f[1]).add(i);
        }
        L[0] = X2NewX.get(L[0]);
        L[1] = Y2NewY.get(L[1]);
        B[0] = X2NewX.get(B[0]);
        B[1] = Y2NewY.get(B[1]);

        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{L[0], L[1], 0});
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();

            if (B[0] == cur[0] || B[1] == cur[1]) {
                System.out.println(cur[2]);
                return;
            }
            for (int index : NewX2IndexList.get(cur[0])) {
                int[] f = fenceList.get(index);
                NewY2IndexList.get(f[1]).remove(index);
                queue.add(new int[]{f[0], f[1], cur[2]+1});
            }
            NewX2IndexList.get(cur[0]).clear();
            for (int index : NewY2IndexList.get(cur[1])) {
                int[] f = fenceList.get(index);
                NewX2IndexList.get(f[0]).remove(index);
                queue.add(new int[]{f[0], f[1], cur[2]+1});
            }
            NewY2IndexList.get(cur[1]).clear();
        }
        System.out.println(-1);
    }
}