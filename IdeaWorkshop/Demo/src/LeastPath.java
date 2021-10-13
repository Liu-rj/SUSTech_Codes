import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class LeastPath {
    static ArrayList<String> substr;
    static ArrayList<String> lines;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        String string = scanner.next();
        substr = new ArrayList<>(string.length() / 6);
        lines = new ArrayList<>(string.length() / 6);
        StringBuffer result = null;
        loop:
        for (int i = 0; i < string.length() / 6 - 1; i++) {
            lines.add(string.substring(6 * i, 6 * i + 12));
            if (i == 0){
                substr.add(string.substring(6 * i, 6 * i + 6));
            } else {
                for (int j = 0; j < substr.size(); j++) {
                    if (substr.get(j).contains(string.substring(6 * i, 6 * i + 6))) {
                        continue loop;
                    }
                }
                substr.add(string.substring(6 * i, 6 * i + 6));
            }
        }
        ArrayList<Integer> point = new ArrayList<>();
        for (String s : substr) {
            point.add(Integer.parseInt(s));
        }
        Collections.sort(point);
        out:
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (check(point.get(i), point.get(j)) != -1 && i != j) {
                    assert false;
                    if (i == 0) {
                        result.append(lines.get(check(point.get(i), point.get(j))));
                        for (int k = 0; k < result.length(); k++) {
                            if (result.charAt(k) != 0){
                                String mid = result.substring(k, result.length());
                                System.out.println(Integer.parseInt(mid) % 998244353);
                                continue out;
                            }
                        }
                    } else {
                        result.append(lines.get(check(point.get(i), point.get(j))), 6, 12);
                        for (int k = 0; k < result.length(); k++) {
                            if (result.charAt(k) != 0){
                                String mid = result.substring(k, result.length());
                                System.out.println(Integer.parseInt(mid) % 998244353);
                                continue out;
                            }
                        }
                    }
                } else {
                    System.out.println(-1);
                }
            }
        }
    }

    public static int check(int a, int b) {
        for (int i = 0; i < lines.size(); i++) {
            ArrayList<Integer> group = new ArrayList<>(Integer.parseInt(lines.get(i).substring(0, 6)));
            group.add(Integer.parseInt(lines.get(i).substring(6, 12)));
            if (group.contains(a) && group.contains(b)) {
                return i;
            }
        }
        return -1;
    }
}
