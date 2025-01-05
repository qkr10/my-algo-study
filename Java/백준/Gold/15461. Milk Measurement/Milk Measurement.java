import java.util.*;
import java.io.*;

public class Main {
    static int N, G;
    static Map<Integer, Integer> Id2NewId;
    static List<int[]> logs;
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        String[] ng = br.readLine().split(" ");
        N = Integer.parseInt(ng[0]);
        G = Integer.parseInt(ng[1]);
        logs = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            String[] line = br.readLine().split(" ");
            int day = Integer.parseInt(line[0]);
            int id = Integer.parseInt(line[1]);
            int diff = Integer.parseInt(line[2].substring(1));
            if (line[2].charAt(0) == '-')
                diff *= -1;
            logs.add(new int[]{day, id, diff});
        }

        Collections.sort(logs, (a, b) -> a[0] - b[0]);

        Id2NewId = new HashMap<>();
        for (int[] log : logs) {
            Id2NewId.putIfAbsent(log[1], Id2NewId.size());
            log[1] = Id2NewId.get(log[1]);
        }

        int[] states = new int[Id2NewId.size()];
        Arrays.fill(states, G);

        int topOutput = G;
        TreeSet<Integer> topCows = new TreeSet<>();
        for (int i = 0; i < Id2NewId.size() + 1; i++)
            topCows.add(i);

        TreeMap<Integer, TreeSet<Integer>> ranking = new TreeMap<>();
        ranking.put(G, (TreeSet) topCows.clone());

        int updateCount = 0;
        for (int i = 0; i < logs.size(); i++) {
            int[] log = logs.get(i);
            int id = log[1];

            ranking.get(states[id]).remove(id);
            ranking.compute(states[id], (k, v) -> v.isEmpty() ? null : v);
            states[id] += log[2];
            ranking.putIfAbsent(states[id], new TreeSet<>());
            ranking.get(states[id]).add(id);

            topOutput = ranking.lastKey();
            Iterator<Integer> nextDisplay = ranking.get(topOutput).iterator();
            Iterator<Integer> curDisplay = topCows.iterator();
            boolean isSameDisplay = ranking.get(topOutput).size() == topCows.size();
            while (isSameDisplay && nextDisplay.hasNext() && curDisplay.hasNext())
                isSameDisplay = Objects.equals(nextDisplay.next(), curDisplay.next());
            if (isSameDisplay)
                continue;

            updateCount++;
            topCows = (TreeSet) ranking.get(topOutput).clone();
        }
        System.out.println(updateCount);
    }
}