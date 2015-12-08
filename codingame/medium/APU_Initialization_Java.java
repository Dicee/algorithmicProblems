package codingame.medium;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class APU_Initialization_Java {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int width  = in.nextInt();
        int height = in.nextInt();
        in.nextLine();
        
        List<Node> nodes = new LinkedList<>();
        Node[] lastNodePerColumn = new Node[width];
        for (int i = 0; i < height; i++) {            
            String line = in.nextLine();
            Node lastInLine = null;       
            for (int j = 0; j < width ; j++) {
                if (line.charAt(j) == '0') {
                    Node node = new Node(i, j);
                    
                    if (lastNodePerColumn[j] != null) lastNodePerColumn[j].bottom = node;
                    lastNodePerColumn[j] = node;
                    
                    if (lastInLine != null) lastInLine.right = node;
                    lastInLine = node;
                    
                    nodes.add(node);
                }
            }
        }
        in.close();
        System.out.println(nodes.stream().map(Node::coordWithNeighbours).collect(Collectors.joining("\n")));
    }   
    
    private static class Node {
        private static final Node NOWHERE_NODE = new Node(-1, -1);
        
        public final int i, j;
        public Node bottom, right;

        public Node(int i, int j) {
            this.i = i;
            this.j = j;
            this.bottom = this.right = NOWHERE_NODE;
        }
        
        public String coordWithNeighbours() {
            return this + " " + right + " " + bottom;
        }
        
        @Override
        public String toString() {
            return j + " " + i;  
        }
    }
}