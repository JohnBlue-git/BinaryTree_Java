/*
Auther: John Blue
Time: 2022/4
Platform: ATOM with atom-ide-ui, ide-java, and script
SDK: java SE 8 SDK
Object: Red Black Tree
Reference: ...
*/

import java.util.Queue;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Red_Black<Key extends Comparable<Key>, Value> {
    //// RED BLACK
    private static final boolean RED   = true;
    private static final boolean BLACK = false;

    //// variable
    private Node root;// root
    private int size; // number of nodes in subtree

    //// inner Node
    private class Node {
        private boolean color;     // color of parent link

        private Key key;           // sorted by key
        private Value val;         // associated data
        private Node left, right;  // left and right subtrees

        public Node(Key key, Value val, boolean cr) {
            this.color = cr;
            this.key = key;
            this.val = val;
	          this.left = null;
	          this.right = null;
        }
      	public Node(final Node copy) {
            this.color = copy.color;
            this.key = copy.key;
            this.val = copy.val;
      	    this.left = null;
      	    this.right = null;
      	    if (copy.left != null) {
      	        this.left = new Node(copy.left);
      	    }
      	    if (copy.right != null) {
      	        this.right = new Node(copy.right);
      	    }
      	}
    }

    //// constructor
    public Red_Black() {
      	this.root = null;
      	this.size = 0;
    }
    public Red_Black(final Red_Black copy) {
      	this.root = new Node(copy.root);
      	this.size = copy.size;
    }

    //// function
    // empty ?
    public boolean isEmpty() {
        return size == 0;
    }

    // size
    public int size() {
        return size;
    }

    // contain ?
    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    // Returns the value associated with the given key.
    public Value get(Key key) {
        return get(root, key);
    }
    private Value get(Node x, Key key) {
        if (key == null) throw new IllegalArgumentException("calls get() with a null key");
        if (x == null) {
	         return null;
	      }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
      	    return get(x.left, key);
      	}
        else if (cmp > 0) {
      	    return get(x.right, key);
      	}
        else {
      	    return x.val;
      	}
    }

    // min
    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("calls min() with empty symbol table");
        return min(root).key;
    }
    private Node min(Node x) {
        if (x.left == null) {
      	    return x;
      	}
        else {
      	    return min(x.left);
      	}
    }

    // max
    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("calls max() with empty symbol table");
        return max(root).key;
    }
    private Node max(Node x) {
        if (x.right == null) {
      	    return x;
      	}
        else {
      	    return max(x.right);
      	}
    }

    // depth (height) of the tree
    public int depth() {
        return depth(root);
    }
    // depth (height) of the Node
    public int depth(Node x) {
        if (x == null) {
      	    return 0;
      	}
        return Math.max(depth(x.left), depth(x.right)) + 1;
    }



    //// traversal
    // pre-, in-, post- is the sequence
    // assume:
    //    4
    //   / \
    //  2   6
    // / \ / \
    //1  3 5  7
    //preorder: mid -> left -> right : 4 2 1 3 6 5 7
    //inorder: left -> mid -> right : 1 2 3 4 5 6 7
    //postorder: left -> right -> mid: 1 3 2 5 7 6 4
    //
    // pre order
    public void trav_pre_order() {
      if (isEmpty()) {
        return;
      }
      System.out.print("pre order traversal:");
      trav_pre_order(root);
      System.out.println("");
    }
    private void trav_pre_order(Node x) {
      if (x == null) {
        return;
      }
      System.out.print(" (" + x.key  + ")" + x.val);
      trav_pre_order(x.left);
      trav_pre_order(x.right);
    }
    // in order
    public void trav_in_order() {
      if (isEmpty()) {
        return;
      }
      System.out.print("in order traversal:");
      trav_in_order(root);
      System.out.println("");
    }
    private void trav_in_order(Node x) {
      if (x == null) {
        return;
      }
      trav_in_order(x.left);
      System.out.print(" (" + x.key + ")" + x.val);
      trav_in_order(x.right);
    }
    // post order
    public void trav_post_order() {
      if (isEmpty()) {
        return;
      }
      System.out.print("post order traversal:");
      trav_post_order(root);
      System.out.println("");
    }
    private void trav_post_order(Node x) {
      if (x == null) {
        return;
      }
      trav_post_order(x.left);
      trav_post_order(x.right);
      System.out.print(" (" + x.key + ")" + x.val);
    }
    //
    // level order ...
    //    4
    //   / \
    //  2   6
    // / \ / \
    //1  3 5  7
    // method 1
    public void trav_level_order() {
      if (isEmpty()) {
        return;
      }
      System.out.print("level order traversal:");
      for (int i = 1; i <= depth(); i++) {
        trav_level_order(root, i);
      }
      System.out.println("");
    }
    private void trav_level_order(Node x, int level) {
      if (x == null) {
        return;
      }
      else if (level == 1) {
        System.out.print(" (" + x.key + ")" + x.val);
      }
      trav_level_order(x.left, level - 1);
      trav_level_order(x.right, level - 1);
    }

    // method 2
    // ??? not work
    /*
    public void trav_level_order2() {
        System.out.print("level order traversal(queue):");
        Queue<Node> queue = new LinkedList<Node>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node x = queue.dequeue();
            if (x == null) {
              continue;
            }
            queue.enqueue(x.left);
            queue.enqueue(x.right);
            System.out.print(" (" + x.key + ")" + x.val);
        }
    }
    */



    //// check

    // is node x red; false if x is null ?
    private boolean isRed(Node x) {
        if (x == null) {
          return false;
        }
        return x.color == RED;
    }

    // Note: this test also ensures that data structure is a binary tree since order is strict
    public boolean isBST() {
        return isBST(root, null, null);
    }
    private boolean isBST(Node x, Key min, Key max) {
        if (x == null) {
          return true;
        }
        if (min != null && x.key.compareTo(min) <= 0) {
          return false;
        }
        if (max != null && x.key.compareTo(max) >= 0) {
          return false;
        }
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    }

    // is 2-3 tree ? red black tree ?
    public boolean is23() {
      return is23(root);
    }
    private boolean is23(Node x) {
        if (x == null) {
          return true;
        }
        if (isRed(x.right)) {
          return false;
        }
        if (x != root && isRed(x) && isRed(x.left)) {
          return false;
        }
        return is23(x.left) && is23(x.right);
    }

    // do all paths from root to leaf have same number of black edges?
    public boolean isBalanced() {
        int black = 0;     // number of black links on path from root to min
        Node x = root;
        while (x != null) {
            if (!isRed(x)) {
              black++;
            }
            x = x.left;
        }
        return isBalanced(root, black);
    }
    // does every path from the root to a leaf have the given number of black links?
    private boolean isBalanced(Node x, int black) {
        if (x == null) {
          return black == 0 || black == 1;
        }
        if (!isRed(x)) {
          black--;
        }
        return isBalanced(x.left, black) && isBalanced(x.right, black);
    }



    //// rotate

    //               (h)                        (x:left)
    //      (left)        (...2)         (...1)           (h)
    // (...1)   (x.right)                         (x.right)  (...2)
    // make a left-leaning link lean to the right
    private Node rotateRight(Node h) {
        if (h.left == null) {
          return h;
        }
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;// default RED
        return x;
    }

    //              (h)                              (x:right)
    //     (...2)         (right)                (h)          (...1)
    //              (x.left)    (...1)     (...2)  (x.left)
    // make a right-leaning link lean to the left
    private Node rotateLeft(Node h) {
        if (h.right == null) {
          return h;
        }
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;// default RED
        return x;
    }

    // flip the colors of a node and its two children
    private void flipColors(Node h) {
        // h must have opposite color of its two children
        // assert (h != null) && (h.left != null) && (h.right != null);
        // assert (!isRed(h) &&  isRed(h.left) &&  isRed(h.right))
        //    || (isRed(h)  && !isRed(h.left) && !isRed(h.right));
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }



    //// insert
    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
        if (val == null) throw new IllegalArgumentException("second argument to put() is null");

        root = put(root, key, val);
        root.color = BLACK;

        size++;
    }
    private Node put(Node h, Key key, Value val) {
        if (h == null) {
          return new Node(key, val, RED);
        }

        int cmp = key.compareTo(h.key);
        if (cmp < 0) {
          h.left  = put(h.left, key, val);
        }
        else if (cmp > 0) {
          h.right = put(h.right, key, val);
        }
        else {
          h.val   = val;
        }

        // fix-up any right-leaning links
        if (!isRed(h.left) && isRed(h.right)) {// null red || black red // case (12) (32)
          h = rotateLeft(h);
        }
        if (isRed(h.left) && isRed(h.left.left)) {// case (11) from return of (12) (32)
          h = rotateRight(h);
        }
        if (isRed(h.left) && isRed(h.right)) {// case (21)
          flipColors(h);
        }
        // case (31)

        return h;
    }
    // LLRB should be:
    // has only left lean red link
    // has no side by side red link
    //
    // the following condition is in the end node
    // (1)
    //     Node(red)
    // Left(null) Right(null)
    //
    // (2)
    //     Node(black)
    // Node(red) Right(null)
    //
    // (3)
    //     Node(black)
    // Left(null) Right(null)
    //
    // (4) this one do not exist in the end node
    //     Node(black)
    // Left(black) Right(black)
    //
    // LLRB right after insert red link would have:
    // after insertion, it may violate the rule:
    // (11)
    //     Node(red)
    // Left(red) Right(null)
    //
    // (12)
    //     Node(red)
    // Left(null) Right(red)
    //
    // (21)
    //     Node(black)
    // Node(red) Right(red)
    //
    // (31)
    //     Node(black)
    // Node(red) Right(null)
    //
    // (32)
    //     Node(black)
    // Node(null) Right(red)



    //// delete

    public void delete(Key key) {
        if (isEmpty()) {
          return;
        }
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        if (!contains(key)) {
          return;
        }

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right)) {
          root.color = RED;
        }

        root = delete(root, key);

        if (!isEmpty()) {
          root.color = BLACK;
        }

        size--;

        // assert check();
    }
    private Node delete(Node h, Key key) {
      int cmp = key.compareTo(h.key);
      if (cmp < 0) {
          h.left  = delete(h.left,  key);

          if (h.left == null && h.right != null) {
            h = rotateLeft(h);
          }
      }
      else if (cmp > 0) {
          h.right = delete(h.right, key);

          if (h.left != null && h.right == null) {
            h.left.color = RED;
          }
      }
      else {
          if (h.left  == null && h.right == null) {
              return null;
          }
          else if (h.left != null && h.right == null) {
            if (h.left.right == null) {
              h.key = h.left.key;
              h.val = h.left.val;

              h.left = delete(h.left, h.left.key);
            }
            else {
              Node current = h.left;
              Node p_current = current;
              while (current.right != null) {
                p_current = current;
                current = current.right;
              }

              h.key = current.key;
              h.val = current.val;

              p_current.right = delete(current, current.key);
            }
          }
          else {
            if (h.right.left == null) {
              h.key = h.right.key;
              h.val = h.right.val;

              h.right = delete(h.right, h.right.key);
            }
            else {
              Node current = h.right;
              Node p_current = current;
              while (current.left != null) {
                p_current = current;
                current = current.left;
              }

              h.key = current.key;
              h.val = current.val;

              p_current.left = delete(current, current.key);
            }
          }
      }

      if (h.left != null && isRed(h.left)) {
        if (h.left.left != null && isRed(h.left.left)) {
          h = rotateRight(h.left);
          flipColors(h);
        }
      }

      return h;
    }
    // case of deletion:
    // (1) no kid
    // (2) one kid
    // (3) two kids
    //
    // LLRB should be:
    // has only left lean red link
    // has no side by side red link
    // (1)
    //     Node(black)
    // Left(red) Right(null)
    //
    // (2)
    //     Node(black)
    // Left(black) Right(black)
    //
    // (3)
    //      Node(red)
    // Left(black) Right(black)
    //
    // the following condition is the posibility through the whole tree
    // case of deletion with color
    // (11)
    //     Node(black)*
    // Left(red) Right(null)
    // then
    // up left to node
    //     Node(black) <- left
    // Left(null) Right(null)
    //
    // (12)
    //     Node(black)
    // Left(red)* Right(null)
    // then
    //     Node(black)
    // Left(null) Right(null)
    //
    // (21)
    //     Node(black)*
    // Left(black) Right(black)
    // then
    // up right to Node (23) or loop ...
    //
    // (22)
    //     Node(black)
    // Left(black)* Right(black)
    // then
    // rotate left(Node)
    //     Node(black) <- Right
    // Left(red) <- Node
    // if left is red
    // rotate right(Node)
    //
    // (23)
    //     Node(black)
    // Left(black) Right(black)*
    // then
    //     Node(black)
    // Left(red)
    // if left is red
    // rotate right(Node)
    //
    // (31)
    //      Node(red)*
    // Left(black) Right(black)
    // then
    // up right to Node (33) or loop ...
    //
    // (32)
    //      Node(red)
    // Left(black)* Right(black)
    // then
    // rotate left(Node)
    //      Node(red) <- Right
    // Left(red) <- Node
    // rotate right(Node)
    //
    // (33)
    //      Node(red)
    // Left(black) Right(black)*
    // then
    //      Node(red)
    // Left(black)



// not work @@
/*
    //// iteration
    //public Iterable<Key> keys() {
    public Queue<Key> keys() {
        if (isEmpty()) {
          return new Queue<Key>();??? not work
        }
        return keys(min(), max());
    }
    //public Iterable<Key> keys(Key lo, Key hi) {
    public Queue<Key> keys(Key lo, Key hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to keys() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to keys() is null");

        Queue<Key> queue = new Queue<Key>();
        keys(root, queue, lo, hi);
        return queue;
    }
    private void keys(Node x, Queue<Key> queue, Key lo, Key hi) {
        if (x == null) {
          return;
        }
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo < 0) {
      	    keys(x.left, queue, lo, hi);
      	}
        if (cmplo <= 0 && cmphi >= 0) {
      	    queue.enqueue(x.key);
      	}
        if (cmphi > 0) {
      	    keys(x.right, queue, lo, hi);
      	}
    }
*/



    // main
    public static void main(String[] args) {
      /*
        Red_Black<String, Integer> st = new Red_Black<String, Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }
      */

        // tree example
        //    4
        //   / \
        //  2   6
        // / \ / \
        //1  3 5  7
        int ist[] = {4, 2, 1, 3, 6, 5, 7};
        System.out.println("put ...");
        Red_Black<Integer, Integer> st = new Red_Black<Integer, Integer>();
        for (int i = 0; i < 7; i++) {
            st.put(ist[i], i);// key, value
        }
        if (st.isBalanced() && st.is23()) {
          System.out.println("a red black tree? yes");
        }
        else {
          System.out.println("a red black tree? no");
        }

        System.out.println("traversal ...");
        st.trav_pre_order();
        st.trav_in_order();
        st.trav_post_order();
        st.trav_level_order();
        //st.trav_level_order2();

        System.out.println("get[4] = " + st.get(4));

        System.out.println("copy ...");
        Red_Black<Integer, Integer> cp = new Red_Black<Integer, Integer>(st);
        cp.trav_pre_order();

        System.out.println("delete ...");
        //cp.delete(4);
        cp.delete(5);
        cp.trav_pre_order();
        cp.trav_in_order();
        cp.trav_post_order();
        cp.trav_level_order();
        if (cp.isBalanced() && cp.is23()) {
          System.out.println("a red black tree? yes");
        }
        else {
          System.out.println("a red black tree? no");
        }

        // queue stack oreder ???
        //for (String s : st.level_order()) {
        //  ???.println(s + " " + st.get(s));
        //}

        //for (String s : st.keys()) {
        //  ???.println(s + " " + st.get(s));
        //}
    }
}
