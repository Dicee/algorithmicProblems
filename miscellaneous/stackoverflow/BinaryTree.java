package miscellaneous.stackoverflow;

import static com.dici.check.Check.notNull;
import static java.util.Collections.emptyList;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import com.dici.check.Check;

/**
 * See http://stackoverflow.com/questions/32917351/implementing-splay-method-for-binary-search-tree/32917891#32917891
 */
public class BinaryTree<T extends Comparable<T>> implements Iterable<T> {
    private static final double log2(double d) { return Math.log(d) / Math.log(2); }
    
    private Node<T> root;
    private int size = 0;
    private int depth = 0;
    
    public BinaryTree() { this(null, 0); }
    
    private BinaryTree(Node<T> root, int size) {
        this.root = root;
        this.size = size;
    }
    
    public int depth() { return root == null ? 0 : root.depth() - 1; }

    public boolean insert(T value) {
        boolean inserted = insertInternal(value);
        if (inserted) size++;
        if (depth > 2 * (log2(size + 1) - 1)) rebalance();
        return inserted;
    }
    
    private boolean insertInternal(T value) {
        Node<T> newNode = new Node<>(value);
        
        if (root == null) {
            root = newNode;
            return true;
        }
        
        int insertedDepth = 0; 
        
        Node<T> current = root;
        while (true) {
            int cmp = newNode.getValue().compareTo(current.getValue());
            if (cmp == 0) return false;
            
            insertedDepth++;
            
            Side side = cmp < 0 ? Side.LEFT : Side.RIGHT;
            if (current.getChild(side) == null) {
                current.setChild(newNode, side);
                depth = Math.max(depth, insertedDepth);
                return true;
            }
            current = current.getChild(side);
        }
    }
    
    private void rebalance() { 
        if (root != null) rebalance(median(), root.getParent());
        depth = (int) log2(size + 1) + 1;
    }

    private void rebalance(Node<T> node, Node<T> rootParent) {
        if (node == null) return;
        while (node.getParent() != rootParent) rotation(node.getParent(), node.getChildKind().opposite());
        rebalance(median(node.getLeft ()), node);
        rebalance(median(node.getRight()), node);
    }
    
    private Node<T> median(            ) { return median(root); }
    private Node<T> median(Node<T> node) { 
        List<Node<T>> nodesInOrder = nodesInOrder(node);
        return nodesInOrder.isEmpty() ? null : nodesInOrder.get(nodesInOrder.size() / 2); 
    }

    private void rotation(Node<T> node, Side side) {
        if (node.getChild(side.opposite()) == null) return;

        Node<T> sideChild = node.getChild(side.opposite());
        node.setChild(sideChild.getChild(side), side.opposite());

        if (node.getParent() == null) setRoot(sideChild);
        else                          node.getParent().setChild(sideChild, node.getChildKind());

        sideChild.setChild(node, side);
    }
    
    private void setRoot(Node<T> root) {
        this.root = root;
        if (root != null) root.setRoot();
    }
    
    @Override
    // TODO: optimize this
    public Iterator<T> iterator() { return inOrder().iterator(); }
    
    public List<T> inOrder() { return inOrder(root, Node::getValue); }

    private List<Node<T>> nodesInOrder(            ) { return inOrder(root, Function.identity()); }
    private List<Node<T>> nodesInOrder(Node<T> node) { return inOrder(node, Function.identity()); }
    
    private static <T extends Comparable<T>, OUTPUT> List<OUTPUT> inOrder(Node<T> node, Function<Node<T>, OUTPUT> get) {
        if (node == null) return emptyList();
        
        List<OUTPUT> elts = new LinkedList<>();
        elts.addAll(inOrder(node.getLeft(), get));
        elts.add(get.apply(node));
        elts.addAll(inOrder(node.getRight(), get));
        return elts;
    }
    
    @Override 
    public String toString() { return root == null ? "[]" : toString(root, "", new StringBuilder()).toString(); }
    
    private static <T extends Comparable<T>> StringBuilder toString(Node<T> node, String indent, StringBuilder sb) { 
        if (!indent.isEmpty()) sb.append("\n");
        
        if (node == null) {
            sb.append(indent).append(".");
            return sb;
        }
            
        sb.append(indent).append(node.getValue());
        indent += "  ";
        
        if (node.getLeft() != null || node.getRight() != null) {
            toString(node.getLeft (), indent, sb);
            toString(node.getRight(), indent, sb);
        }
        return sb;
    }
    
    private static enum Side { 
        LEFT, RIGHT;
        
        public Side opposite() { return this == LEFT ? RIGHT : LEFT; }
    }
    
    private static class Node<T extends Comparable<T>> {
        private T value;
        private Node<T> left, right, parent;

        public Node(T value) { this(value, null, null, null); } 


        public Node(T value, Node<T> left, Node<T> right, Node<T> parent) {
            setValue (value );
            setLeft  (left  );
            setRight (right );
            setParent(parent);
        }
        
        public int depth() {
            int depth = 0;
            if (getLeft() != null) depth = getLeft().depth();
            if (getRight() != null) depth = Math.max(depth, getRight().depth());
            return 1 + depth;
        }
        
        public Node<T> setLeft(Node<T> left) {
            this.left = left;
            if (left != null) left.parent = this;
            return this;
        }
        
        public Node<T> setRight(Node<T> right) {
            this.right = right;
            if (right != null) right.parent = this;
            return this;
        }
        
        public Node<T> setChild(Node<T> child, Side side) { return side == Side.LEFT ? setLeft(child) : setRight(child); }
        
        public Node<T> setRoot() { return setParent(null); }
        
        private Node<T> setParent(Node<T> parent) {
            this.parent = parent;
            return this;
        }
        
        public Node<T> setValue(T value) {
            this.value = notNull(value);
            return this;
        }
        
        public boolean isRoot() { return parent == null; }
        
        public boolean isLeftChild () { return isRoot() || getParent().getValue().compareTo(getValue()) > 0; }
        public boolean isRightChild() { return isRoot() || !isLeftChild()                                  ; }

        public Node<T> getChild(Side side) { return side == Side.LEFT ? getLeft() : getRight(); }
        
        public Side getChildKind() { 
            Check.isFalse(isRoot(), "This method is not defined on root nodes");
            return isLeftChild() ? Side.LEFT : Side.RIGHT; 
        }
        
        public T       getValue () { return value ; }
        public Node<T> getLeft  () { return left  ; }
        public Node<T> getRight () { return right ; }
        public Node<T> getParent() { return parent; }
    }
}
