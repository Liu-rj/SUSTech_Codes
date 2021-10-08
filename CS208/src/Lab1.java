import java.io.*;
import java.math.*;
import java.util.*;

public class Lab1 {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader scanner = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        Task solver = new Task();
        solver.solve(scanner, out);
        int n = scanner.nextInt();
        HashMap<String, Integer> boyName = new HashMap<>();
        HashMap<String, Integer> girlName = new HashMap<>();
        String[] bn = new String[n];
        String[] gn = new String[n];
        int[] boy = new int[n]; // boy index, store matched girl index
        int[] girl = new int[n]; // girl index, store matched boy index
        int[][] boysPrefer = new int[n][n];
        int[][] girlsPrefer = new int[n][n]; // reverse preference
        int[] count = new int[n]; // ith boy decide jth girl to choose
        int[] unmatched = new int[n * n]; // queue
        for (int i = 0; i < n; i++) {
            unmatched[i] = i;
            boy[i] = -1;
            girl[i] = -1;
        }
        int head = 0, rear = n;
        for (int i = 0; i < n; i++) {
            bn[i] = scanner.next();
            boyName.put(bn[i], i);
        }
        for (int i = 0; i < n; i++) {
            gn[i] = scanner.next();
            girlName.put(gn[i], i);
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                boysPrefer[i][j] = girlName.get(scanner.next());
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                girlsPrefer[i][boyName.get(scanner.next())] = j;
            }
        }
        int index, prefer;
        while (head < rear) {
            index = unmatched[head++]; // boy index
            while (true) {
                prefer = boysPrefer[index][count[index]]; // girl index
                if (girl[prefer] == -1) {
                    girl[prefer] = index;
                    boy[index] = prefer;
                    break;
                } else if (girlsPrefer[prefer][girl[prefer]] > girlsPrefer[prefer][index]) {
                    boy[girl[prefer]] = -1;
                    unmatched[rear++] = girl[prefer];
                    count[girl[prefer]]++;
                    boy[index] = prefer;
                    girl[prefer] = index;
                    break;
                } else {
                    count[index]++;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            out.println(bn[i] + " " + gn[boy[i]]);
        }
        out.close();
    }

    static class Task {
        public void solve(InputReader in, PrintWriter out) {


        }
    }


    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public char[] nextCharArray() {
            return next().toCharArray();
        }

        //         public boolean hasNext() {
//             try {
//                 return reader.ready();
//             } catch(IOException e) {
//                 throw new RuntimeException(e);
//             }
//         }
        public boolean hasNext() {
            try {
                String string = reader.readLine();
                if (string == null) {
                    return false;
                }
                tokenizer = new StringTokenizer(string);
                return tokenizer.hasMoreTokens();
            } catch (IOException e) {
                return false;
            }
        }

        public BigInteger nextBigInteger() {
            return new BigInteger(next());
        }

        public BigDecimal nextBigDecinal() {
            return new BigDecimal(next());
        }
    }
}
