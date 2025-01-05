import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        String[] line = br.readLine().split(" ");
        long[] nm = Arrays.stream(line).mapToLong(Long::parseLong).toArray();
        int root = (int) Math.sqrt(nm[1]) + 1;
        int rootroot = (int) Math.sqrt(root) + 1;
        int[] eratos = new int[root+1];
        for (int i = 2; i <= rootroot; i++)
            if (eratos[i] == 0)
                for (int j = i*2; j <= root; j += i)
                    eratos[j] = 1;
        
        int result = 0;
        for (int i = 2; i <= root; i++)
            if (eratos[i] == 0)
                for (double j = (long)i * i; j <= nm[1]; j *= i)
                    if (nm[0] <= j)
                        result++;
        System.out.println(result);
    }
}