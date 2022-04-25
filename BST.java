/*
Auther: John Blue
Time: 2022/4
Platform: ATOM with atom-ide-ui, ide-java, and script
SDK: java SE 8 SDK
Object: Binary Search Tree
Reference: ...
*/

import java.util.Queue;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class BST<Key extends Comparable<Key>, Value> {
    //// variable
    private Node root;// root
    private int size; // number of nodes in subtree

    //// inner Node
    private class Node {
        private Key key;           // sorted by key
        private Value val;         // associated data
        private Node left, right;  // left and right subtrees

        public Node(Key key, Value val) {
            this.key = key;
            this.val = val;
	          this.left = null;
	          this.right = null;
        }
      	public Node(final Node copy) {
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
    public BST() {
      	this.root = null;
      	this.size = 0;
    }
    public BST(final BST copy) {
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
      System.out.print(" (" + x.key + ")" + x.val);
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



    //// insert and delete
    // insert new node
    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("calls put() with a null key");
        if (val == null) {
            delete(key);
            return;
        }
        root = put(root, key, val);
        size += 1;
    }
    private Node put(Node x, Key key, Value val) {
        if (x == null) {
      	    return new Node(key, val);
      	}
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
      	    x.left = put(x.left, key, val);
      	}
        else if (cmp > 0) {
      	    x.right = put(x.right, key, val);
      	}
        else {
      	    x.val = val;
      	}
      	return x;
    }

    // delete
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("calls delete() with a null key");

        root = delete(root, key);

        size -= 1;
    }
    private Node delete(Node x, Key key) {
        if (x == null) {
          return null;
        }

        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
      	    x.left  = delete(x.left,  key);
      	}
        else if (cmp > 0) {
      	    x.right = delete(x.right, key);
      	}
        else {
            if (x.left  == null && x.right == null) {
      	        x = null;
      	    }
            else if (x.left != null && x.right == null) {
              if (x.left.right == null) {
                x.key = x.left.key;
                x.val = x.left.val;

                x.left = delete(x.left, x.left.key);
              }
              else {
                Node current = x.left;
                Node p_current = current;
                while (current.right != null) {
                  p_current = current;
                  current = current.right;
                }

                x.key = current.key;
                x.val = current.val;

                p_current.right = delete(current, current.key);
              }
            }
            else {
              if (x.right.left == null) {
                x.key = x.right.key;
                x.val = x.right.val;

                x.right = delete(x.right, x.right.key);
              }
              else {
                Node current = x.right;
                Node p_current = current;
                while (current.left != null) {
                  p_current = current;
                  current = current.left;
                }

                x.key = current.key;
                x.val = current.val;

                p_current.left = delete(current, current.key);
              }
            }
        }

        return x;
    }


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
        BST<String, Integer> st = new BST<String, Integer>();
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
        BST<Integer, Integer> st = new BST<Integer, Integer>();
        for (int i = 0; i < 7; i++) {
            st.put(ist[i], i);// key, value
        }

        System.out.println("traversal ...");
        st.trav_pre_order();
        st.trav_in_order();
        st.trav_post_order();
        st.trav_level_order();
        //st.trav_level_order2();

        System.out.println("get[4] = " + st.get(4));

        System.out.println("copy ...");
        BST<Integer, Integer> cp = new BST<Integer, Integer>(st);
        cp.trav_pre_order();

        System.out.println("delete ...");
        cp.delete(4);
        cp.trav_pre_order();
        cp.delete(5);
        cp.trav_pre_order();
        //st.trav_pre_order();

        // queue stack oreder ???
        //for (String s : st.level_order()) {
        //  ???.println(s + " " + st.get(s));
        //}

        //for (String s : st.keys()) {
        //  ???.println(s + " " + st.get(s));
        //}
    }
}
