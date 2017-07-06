// file FHsdTree.java

/* ----------------------------------------------------------------------------
 * Class FHsdTree: a generic general tree class that allows soft-deletion of
 * nodes (instances of FHsdTreeNode)
 *
 * Included public methods allow for adding nodes, soft-deleting nodes, checking
 * virtual or physical size, checking virtual or physical emptiness, virtual and
 * physical display, clone, garbage collection, and traverse (which requires
 * implementation of the TraverserG class by the client)
 */

class FHsdTree<E> implements Cloneable
{
   // physical size (number of nodes in tree), including soft-deleted nodes
   protected int mSize;

   // points to the root (level 0) for the tree. All nodes point to mRoot
   protected FHsdTreeNode<E> mRoot;

   // default constructor
   public FHsdTree() { clear(); }

   // simple hard-deletion (removal of all n odes) for entire tree
   public void clear() { mSize = 0; mRoot = null; }

   // returns true if tree has no nodes (soft-deleted or otherwise)
   public boolean emptyPhysical() { return (mSize == 0); }

   // returns true if tree has no nodes or root node has been soft-deleted
   public boolean empty() { return (mSize == 0 || mRoot.deleted); }

   // returns number of nodes in tree, including deleted nodes
   public int sizePhysical() { return mSize; }

   // following public methods call overloaded methods
   public FHsdTreeNode<E> find(E x) { return find(mRoot, x, 0); }
   public boolean remove(E x) { return remove(mRoot, x); }
   public void display() { display(mRoot, 0); }
   public void  displayPhysical() { displayPhysical(mRoot, 0, false); }
   public void garbageCollection() { garbageCollection(mRoot); }
   public int size() { return size(mRoot); }

   // returns num of nodes, not including soft-deleted nodes or their children
   public int size(FHsdTreeNode<E> root)
   {
      FHsdTreeNode<E> child;
      int nodeCount;

      if (root == null)
         return 0;

      if (root.deleted)
         return 0; // escape without counting this node or children

      nodeCount = 1;
      // recursive step done here: visit all children
      for (child = root.firstChild;
         child != null;
         child = child.sib)
         nodeCount += size(child);
      return nodeCount;
   }

   // adds data: E x as first child of treeNode, regardless of deletion status
   public FHsdTreeNode<E> addChild(FHsdTreeNode<E> treeNode,  E x)
   {
      // empty tree? - create a root node if user passes in null
      if (mSize == 0)
      {
         if (treeNode != null)
            return null; // error: can't be mSize == 0 AND treeNode != null
         mRoot = new FHsdTreeNode<E>(x, null, null, null, false);
         mRoot.myRoot = mRoot;
         mSize = 1;
         return mRoot;
      }
      if (treeNode == null)
         return null; // error inserting into non_null tree with a null parent
      if (treeNode.myRoot != mRoot)
         return null;  // silent error, node does not belong to this tree

      // push this node into the head of the sibling list; adjust prev pointers
      FHsdTreeNode<E> newNode = new FHsdTreeNode<E>(x, treeNode.firstChild,
         null, treeNode, mRoot, false);  // sb, chld, prv, rt, deleted
      treeNode.firstChild = newNode;
      if (newNode.sib != null)
         newNode.sib.prev = newNode;
      ++mSize;
      return newNode;
   }

   // returns the first (not soft-deleted) node matching data: E x
   public FHsdTreeNode<E> find(FHsdTreeNode<E> root, E x, int level)
   {
      FHsdTreeNode<E> retval;

      if (mSize == 0 || root == null)
         return null;
      if (root.data.equals(x) && !root.deleted)
         return root;

      // otherwise, recurse. don't process sibs if this was the original call
      if (level > 0 && (retval = find(root.sib, x, level)) != null)
         return retval;
      if (root.deleted) // don't check children of deleted nodes
         return null; // already checked sibs, so return null for this subtree
      return find(root.firstChild, x, ++level);
   }

   // set deleted = true for first (not soft-deleted) node matching data: E x
   public boolean remove(FHsdTreeNode<E> root, E x)
   {
      FHsdTreeNode<E> tn = null;

      if (mSize == 0 || root == null)
         return false;

      if ((tn = find(root, x, 0)) != null)
      {
         tn.deleted = true;
         return true;
      }
      return false;
   }

   // physical removal of nodeToDelete from the tree
   private void removeNode(FHsdTreeNode<E> nodeToDelete)
   {
      if (nodeToDelete == null || mRoot == null)
         return; // node and tree are null: nothing to remove
      if (nodeToDelete.myRoot != mRoot)
         return;  // silent error, node does not belong to this tree

      // remove all the children of this node
      // (but for decrementing mSize, this loop might be unnecessary)
      while (nodeToDelete.firstChild != null)
         removeNode(nodeToDelete.firstChild);

      if (nodeToDelete.prev == null)
         mRoot = null;  // last node in tree
      else if (nodeToDelete.prev.sib == nodeToDelete)
         nodeToDelete.prev.sib = nodeToDelete.sib; // adjust left sibling
      else
         nodeToDelete.prev.firstChild = nodeToDelete.sib;  // adjust parent

      // adjust the successor sib's prev pointer
      if (nodeToDelete.sib != null)
         nodeToDelete.sib.prev = nodeToDelete.prev;
      --mSize;
   }

   // hard-deletion of all soft-deleted nodes and their children
   public void garbageCollection(FHsdTreeNode<E> root)
   {
      // physically empty or null (sub)tree: done for (sub)tree
      if (mSize == 0 || root == null)
         return;

      if (root.deleted)
         removeNode(root);

      // otherwise, recurse
      garbageCollection(root.firstChild);
      garbageCollection(root.sib);
   }

   // instantiates a new duplicate copy of the tree
   public Object clone() throws CloneNotSupportedException
   {
      FHsdTree<E> newObject = (FHsdTree<E>)super.clone();
      newObject.clear();  // can't point to other's data

      newObject.mRoot = cloneSubtree(mRoot);
      newObject.mSize = mSize;
      newObject.setMyRoots(newObject.mRoot);

      return newObject;
   }

   // helper for clone()
   private FHsdTreeNode<E> cloneSubtree(FHsdTreeNode<E> root)
   {
      FHsdTreeNode<E> newNode;
      if (root == null)
         return null;

      // does not set myRoot which must be done by caller
      newNode = new FHsdTreeNode<E>(root.data, cloneSubtree(root.sib),
         cloneSubtree(root.firstChild), null, root.deleted);

      // the prev pointer is set by parent recursive call ... this is the code:
      if (newNode.sib != null)
         newNode.sib.prev = newNode;
      if (newNode.firstChild != null)
         newNode.firstChild.prev = newNode;
      return newNode;
   }

   // recursively sets all myRoots to mRoot. A helper for clone()
   private void setMyRoots(FHsdTreeNode<E> treeNode)
   {
      FHsdTreeNode<E> child;

      if (mRoot == null)
         return;

      treeNode.myRoot = mRoot;
      for (child = treeNode.firstChild; child != null; child = child.sib)
         setMyRoots(child);
   }

   // define as static member so recursive display() doesn't need local version
   final static String blankString = "                                    ";

   // displays the tree, omitting the soft-deleted nodes, and their children
   public void display(FHsdTreeNode<E> treeNode, int level)
   {
      FHsdTreeNode<E> child;
      String indent;

      if (treeNode == null)
         return;

      // stop runaway indentation/recursion
      if  (level > (int)blankString.length() - 1)
      {
         System.out.println( blankString + " ... " );
         return;
      }

      indent = blankString.substring(0, level);

      if (treeNode.deleted)
         return; // escape without displaying this node or children

      // pre-order processing done here ("visit")
      System.out.println(indent + treeNode.data);

      // recursive step done here
      for (child = treeNode.firstChild;
         child != null;
         child = child.sib)
         display(child, level+1);
   }

   // displays entire/sub tree, adding a "(D)" after deleted nodes and children
   public void displayPhysical(FHsdTreeNode<E> treeNode,
                               int level, boolean dltd)
   {
      FHsdTreeNode<E> child;
      String indent;

      if (treeNode == null)
         return;

      // stop runaway indentation/recursion
      if  (level > (int)blankString.length() - 1)
      {
         System.out.println( blankString + " ... " );
         return;
      }

      indent = blankString.substring(0, level);

      // pre-order processing done here ("visit")
      if (dltd || treeNode.deleted)
      {
         dltd = true; // set dltd flag to display "(D)" for this and children
         System.out.println(indent + treeNode.data + " (D)");
      }
      else System.out.println(indent + treeNode.data);

      // recursive step done here
      for (child = treeNode.firstChild;
         child != null;
         child = child.sib)
         displayPhysical(child, level+1, dltd);
   }

   public <F extends TraverserG<? super E>> void traverse(F func)
   {
      traverse(func, mRoot);
   }

   protected <F extends TraverserG<? super E>>
   //  public <F extends TraverserG<E>>  // also works but less flexibly
   void traverse(F func, FHsdTreeNode<E> treeNode)
   {
      if (treeNode == null || treeNode.deleted)
         return;

      FHsdTreeNode<E> child;
      func.visit(treeNode.data);

      for (child = treeNode.firstChild; child != null; child = child.sib)
         traverse(func, child);
   }
} // end class FHsdTree -------------------------------------------------------
