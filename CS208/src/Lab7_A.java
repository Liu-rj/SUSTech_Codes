import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Lab7_A {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        Node[] nodes = new Node[n + 1];
        long score = 0;
        nodes[0] = new Node(0, 0, Integer.MAX_VALUE);
        for (int i = 1; i <= n; i++) {
            nodes[i] = new Node(in.nextInt(), in.nextInt(), in.nextInt());
        }

        int[] ts = new int[100000001];
        int[] ats = new int[n + 1];
        Arrays.sort(nodes, new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.s - o2.s;
            }
        });
        int x = 0;
        for (int i = 1; i <= n; i++) {
            x = Math.max(x + 1, nodes[i].s);
            ats[i] = x;
        }

        Arrays.sort(nodes, new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o2.w - o1.w;
            }
        });
        for (int i = 1; i <= n; i++) {
            if (linearMatch(nodes, ts, ats, nodes[i].s, i)) {
                score += nodes[i].w;
            }
        }
        System.out.println(score);
    }

    public static boolean linearMatch(Node[] nodes, int[] ts, int[] ats, int x, int index) {
        if (ts[x] == 0) {
            ts[x] = index;
            return true;
        }
        int atsIndex = x + 1;
        for (int i = 1; i < ats.length; i++) {
            if (ats[i] > x) {
                atsIndex = i;
                break;
            }
        }
        if (nodes[index].t > nodes[ts[x]].t) {
            return match(nodes, ts, ats, atsIndex, index);
        } else {
            if (match(nodes, ts, ats, atsIndex, ts[x])) {
                ts[x] = index;
                return true;
            }
        }
        return false;
    }

    public static boolean match(Node[] nodes, int[] ts, int[] ats, int atsIndex, int index) {
        Node node = nodes[index];
        int x = ats[atsIndex];
        if (x > node.t) {
            return false;
        }
        if (ts[x] == 0) {
            ts[x] = index;
            return true;
        }
        if (nodes[index].t > nodes[ts[x]].t) {
            return match(nodes, ts, ats, atsIndex + 1, index);
        } else {
            if (match(nodes, ts, ats, atsIndex + 1, ts[x])) {
                ts[x] = index;
                return true;
            }
        }
        return false;
    }

    static class Node {
        int s;
        int t;
        int w;

        Node(int s, int t, int w) {
            this.s = s;
            this.t = t;
            this.w = w;
        }
    }
}
