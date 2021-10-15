import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Lab8_A {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        String s;
        for (int i = 0; i < n; i++) {
            s = in.next();
            HashMap<Character, Integer> chars = new HashMap<>();
            PriorityQueue<Node> q = new PriorityQueue<>(new Comparator<Node>() {
                @Override
                public int compare(Node o1, Node o2) {
                    return o1.num - o2.num;
                }
            });
            Node u, v;
            int sum = 0;
            for (int j = 0; j < s.length(); j++) {
                if (!chars.containsKey(s.charAt(j))) {
                    chars.put(s.charAt(j), 1);
                } else {
                    chars.put(s.charAt(j), chars.get(s.charAt(j)) + 1);
                }
            }
            for (Integer value : chars.values()) {
                Node node = new Node(value);
                q.add(node);
            }
            Node root;
            if (q.size() == 1) {
                sum = q.poll().num;
            } else {
                while (q.size() > 1) {
                    u = q.poll();
                    v = q.poll();
                    Node father = new Node(u.num + v.num);
                    father.left = u;
                    father.right = v;
                    q.add(father);
                }
                root = q.poll();
                sum = DFS(root, 0);
            }
            System.out.println(sum);
        }
    }

    public static int DFS(Node node, int depth) {
        if (node.left == null && node.right == null) {
            return node.num * depth;
        }
        int sum = 0;
        sum += DFS(node.left, depth + 1);
        sum += DFS(node.right, depth + 1);
        return sum;
    }

    static class Node {
        int num;
        Node left;
        Node right;

        Node(int num) {
            this.num = num;
        }
    }
}
