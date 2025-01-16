import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        long N = Long.parseLong(br.readLine());

        int sqrtN = (int) Math.sqrt(N) + 1;
        int[] eratos = new int[sqrtN];
        TreeSet<Integer> primes = new TreeSet<>();
        for (int i = 2; i < sqrtN; i++)
            if (eratos[i] == 0) {
                primes.add(i);
                for (long j = (long)i*i; j < sqrtN; j+=i)
                    eratos[(int)j] = 1;
            }

        Iterator<Integer> iter = primes.iterator();
        TreeMap<Long, Integer> factorization = new TreeMap<>();
        long target = N;
        int countSum = 0;
        while (iter.hasNext()) {
            int cur = iter.next();
            if (target % cur != 0)
                continue;

            target /= cur;
            int count = 1;
            while (target % cur == 0) {
                target /= cur;
                count++;
            }
            countSum += count;
            factorization.put((long)cur, count);
        }
        if (target > sqrtN - 1) {
            factorization.put(target, 1);
            countSum++;
        }

        long[] result = new long[(countSum+1)/2];
        Arrays.fill(result, 1);
        int index = 0;
        for (Map.Entry<Long, Integer> entry : factorization.entrySet())
            for (int i = 0; i < entry.getValue(); i++) {
                result[index / 2] *= entry.getKey();
                index++;
            }

        if (countSum == 1)
            System.out.println(-1);
        else {
            if (countSum % 2 == 1)
                result[countSum/2-1] *= result[countSum/2];
            for (int i = 0; i < countSum / 2; i++)
                System.out.printf("%d ", result[i]);
        }
    }
}