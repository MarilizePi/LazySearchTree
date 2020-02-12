package lazyTrees;

import java.util.*;

/**
 * LazySearchTree is a Binary Search Tree (BST) algorithm that implements lazy deletion
 * to solve the assigned tasks. This is an optimized version including collectGarbage() method to clean up
 * the tree with hard deletion.
 *
 * @param <E> genetic type.
 * @author Pires, Marilize.
 */
public class LazySearchTree<E extends Comparable<? super E>>
        implements Cloneable {

    protected int mSize; // "soft" size of the tree. Does not include deleted nodes.
    protected LazySTNode mRoot; // "hard" size of the tree. Includes deleted and un-deleted nodes.
    protected int mSizeHard; // root of the tree.

    /**
     * Constructor that initializes  the empty tree.
     */
    public LazySearchTree() {
        clear();
    }

    /**
     * It checks to see if the tree is empty.
     *
     * @return true if empty, false otherwise.
     */
    public boolean empty() {
        return (mSize == 0);
    }

    /**
     * It returns the "soft" size of the tree.
     *
     * @return size of the tree.
     */
    public int size() {
        return mSize;
    }

    /**
     * It returns the "hard" size of the tree.
     *
     * @return size of the tree.
     */
    public int sizeHard() {
        return mSizeHard;
    }

    /**
     * It clears the tree by re-setting the default values to zero and null.
     */
    public void clear() {
        mSize = 0;
        mSizeHard = 0;
        mRoot = null;
    }

    /**
     * It shows the height of the tree.
     *
     * @return height of the tree.
     */
    public int showHeight() {
        return findHeight(mRoot, -1);
    }

    /**
     * It calls the private method find() to search for data in the tree.
     *
     * @param x genetic type.
     * @return the data of the node found.
     */
    public E find(E x) {
        LazySTNode result = find(mRoot, x);
        if (result == null) {
            throw new NoSuchElementException();
        }
        return result.data;
    }

    /**
     * It finds the minimum value in the tree.
     *
     * @return genetic type, min value.
     */
    public E findMin() {
        if (mRoot == null) {
            throw new NoSuchElementException();
        }
        return findMin(mRoot).data;
    }

    /**
     * It finds the maximum value in the tree.
     *
     * @return genetic type, max value.
     */
    public E findMax() {
        if (mRoot == null) {
            throw new NoSuchElementException();
        }
        return findMax(mRoot).data;
    }

    /**
     * It calls the private method find() to see if the tree contains specific data values.
     *
     * @param x genetic type.
     * @return true if the value found, false otherwise.
     */
    public boolean contains(E x) {
        return find(mRoot, x) != null;
    }

    /**
     * It inserts item into the tree.
     *
     * @param x genetic type, item to be inserted.
     * @return true if successful, false otherwise.
     */
    public boolean insert(E x) {
        int oldSize = mSize;
        mRoot = insert(mRoot, x);
        return (mSize != oldSize);
    }

    /**
     * It removes items from the tree.
     *
     * @param x genetic type, item to be deleted.
     * @return true if successful, false otherwise.
     */
    public boolean remove(E x) {
        int oldSize = mSize;
        remove(mRoot, x);
        return (mSize != oldSize);
    }

    /**
     * It removes items from the tree.
     *
     * @param x genetic type, item to be deleted.
     * @return true if successful, false otherwise.
     */
    public boolean removeHard(E x) {
        int oldSize = mSize;
        mRoot = removeHard(mRoot, x);
        return mSize != oldSize;
    }


    /**
     * Hard traverses through the tree by calling the protected method traverseHard()
     * and it displays all nodes.
     *
     * @param func functor to be applied.
     * @param <F>  genetic type.
     */
    public <F extends Traverser<? super E>>
    void traverseHard(F func) {
        traverseHard(func, mRoot);
    }

    /**
     * Soft traverses through the tree by calling the protected method traverseSoft()
     * and it displays on node lazily deleted.
     *
     * @param func functor to be applied.
     * @param <F>  genetic type.
     */
    public <F extends Traverser<? super E>>
    void traverseSoft(F func) {
        traverseSoft(func, mRoot);
    }

    /**
     * It calls the protected collectGarbage to remove nodes marked as deleted.
     *
     * @return true if successful.
     */
    public boolean collectGarbage() {
        int oldSize = mSizeHard;
        mRoot = collectGarbage(mRoot);
        return (mSizeHard != oldSize);
    }

    /**
     * It clones the tree.
     *
     * @return tree cloned.
     * @throws CloneNotSupportedException if clone fails.
     */
    public Object clone() throws CloneNotSupportedException {
        LazySearchTree<E> newObject = (LazySearchTree<E>) super.clone();
        newObject.clear();

        newObject.mRoot = cloneSubtree(mRoot);
        newObject.mSize = mSize;

        return newObject;
    }

    // private helper methods ----------------------------------------

    /**
     * It located item in a specific tree.
     *
     * @param root where x is to be found.
     * @param x    genetic type/item to be found.
     * @return located item.
     */
    protected LazySTNode find(LazySTNode root, E x) {

        if (root == null) {
            return null;
        }

        int compareResult = x.compareTo(root.data);
        if (compareResult < 0) {
            return find(root.lftChild, x);
        }
        if (compareResult > 0) {
            return find(root.rtChild, x);
        }
        if (!root.deleted) {
            return root;
        }
        return null;
    }

    /**
     * It finds the smallest item of a specific tree.
     *
     * @param root root of tree.
     * @return smallest item found.
     */
    protected LazySTNode findMin(LazySTNode root) {
        if (root == null) {
            return null;
        }
        LazySTNode minValueFound = findMin(root.lftChild);
        if (minValueFound != null) {
            return minValueFound;
        }
        if (!root.deleted) {
            return root;
        }

        return findMin(root.rtChild);
    }

    /**
     * It finds the true/precise minimum value.
     *
     * @param root it starts to recurse down the tree.
     * @return the minimum value.
     */
    protected LazySTNode findMinHard(LazySTNode root) {
        if (root == null) {
            return null;
        }
        if (root.lftChild == null) {
            return root;
        }
        return findMinHard(root.lftChild);
    }

    /**
     * It finds the largest item of a specific tree.
     *
     * @param root root of tree.
     * @return largest item found.
     */
    protected LazySTNode findMax(LazySTNode root) {
        if (root == null) {
            return null;
        }

        LazySTNode maxValueFound = findMax(root.rtChild);

        if (maxValueFound != null) {
            return maxValueFound;
        }
        if (!root.deleted) {
            return root;
        }
        return findMax(root.lftChild);
    }

    /**
     * It finds the largest/maximum value in the tree.
     *
     * @param root it starts to recurse down the tree.
     * @return the maximum value.
     */
    protected LazySTNode findMaxHard(LazySTNode root) {
        if (root == null) {
            return null;
        }
        if (root.rtChild == null) {
            return root;
        }
        return findMaxHard(root.rtChild);
    }

    /**
     * It inserts item at a specific tree.
     *
     * @param root root of the subtree.
     * @param x    item to be inserted.
     * @return item inserted.
     */
    protected LazySTNode insert(LazySTNode root, E x) {

        if (root == null) {
            mSize++;
            mSizeHard++;
            return new LazySTNode(x, null, null);
        }

        int compareResult = x.compareTo(root.data);
        if (compareResult < 0) {
            root.lftChild = insert(root.lftChild, x);
        } else if (compareResult > 0) {
            root.rtChild = insert(root.rtChild, x);
        } else {
            if (root.deleted) {
                root.deleted = false;
                mSize++;
            }
        }
        return root;
    }

    /**
     * It removes item from specific tree.
     *
     * @param root root of the tree.
     * @param x    item to be removed.
     */
    protected void remove(LazySTNode root, E x) {

        if (root == null) {
            return;
        }
        int compareResult = x.compareTo(root.data);

        if (compareResult < 0) {
            remove(root.lftChild, x);
        } else if (compareResult > 0) {
            remove(root.rtChild, x);
        } else {
            if (!root.deleted) {
                root.deleted = true;
                mSize--;
            }
        }
    }

    /**
     * It hard removes item from a specific tree.
     *
     * @param root root of the tree. Where we want to remove.
     * @param x    genetic type to remove.
     * @return object (LazySTNode) just removed.
     */
    protected LazySTNode removeHard(LazySTNode root, E x) {
        int compareResult;
        if (root == null) {
            return null;
        }
        compareResult = x.compareTo(root.data);

        if (compareResult < 0) {
            root.lftChild = removeHard(root.lftChild, x);
        } else if (compareResult > 0) {
            root.rtChild = removeHard(root.rtChild, x);
        } else if ((root.lftChild != null) && (root.rtChild != null)) {
            root.data = findMinHard(root.rtChild).data;
            root.deleted = false;
            root.rtChild = removeHard(root.rtChild, root.data);
        } else {
            root = (root.lftChild != null) ? root.lftChild : root.rtChild;
            mSizeHard--;
        }
        return root;
    }

    /**
     * Hard traverses through a specific tree.
     *
     * @param func     specified function.
     * @param treeNode node.
     * @param <F>      genetic type.
     */
    protected <F extends Traverser<? super E>>
    void traverseSoft(F func, LazySTNode treeNode) {
        if (treeNode == null) {
            return;
        }

        traverseSoft(func, treeNode.lftChild);

        if (!treeNode.deleted) {
            func.visit(treeNode.data);
        }

        traverseSoft(func, treeNode.rtChild);
    }

    /**
     * Soft traverses through a specific tree.
     *
     * @param func     specified function.
     * @param treeNode node.
     * @param <F>      genetic type.
     */
    protected <F extends Traverser<? super E>>
    void traverseHard(F func, LazySTNode treeNode) {
        if (treeNode == null) {
            return;
        }

        traverseHard(func, treeNode.lftChild);
        func.visit(treeNode.data);
        traverseHard(func, treeNode.rtChild);
    }

    /**
     * It removes precisely all deleted nodes from the tree.
     *
     * @param root LazySTNode of tree.
     * @return root node.
     */
    protected LazySTNode collectGarbage(LazySTNode root) {
        if (root == null) {
            return null;
        }
        if (root.lftChild != null) {
            root.lftChild = collectGarbage(root.lftChild);
        }
        if (root.rtChild != null) {
            root.rtChild = collectGarbage(root.rtChild);
        }
        if (root.deleted) {
            root = removeHard(root, root.data);
        }
        return root;
    }


    /**
     * It clones a specific tree.
     *
     * @param root root of the tree.
     * @return root of cloned tree.
     */
    protected LazySTNode cloneSubtree(LazySTNode root) {
        LazySTNode newNode;
        if (root == null) {
            return null;
        }

        // does not set myRoot which must be done by caller
        newNode = new LazySTNode
                (
                        root.data,
                        cloneSubtree(root.lftChild),
                        cloneSubtree(root.rtChild)
                );
        return newNode;
    }

    /**
     * It finds the height of the tree.
     *
     * @param treeNode to find the height.
     * @param height   height of the tree.
     * @return height of the tree.
     */
    protected int findHeight(LazySTNode treeNode, int height) {
        int leftHeight, rightHeight;
        if (treeNode == null) {
            return height;
        }
        height++;
        leftHeight = findHeight(treeNode.lftChild, height);
        rightHeight = findHeight(treeNode.rtChild, height);
        return (leftHeight > rightHeight) ? leftHeight : rightHeight;
    }

    // INNER CLASS----------------------------------

    private class LazySTNode {
        public LazySTNode lftChild, rtChild;
        public E data;
        public LazySTNode myRoot;  // needed to test for certain error
        public boolean deleted;

        /**
         * It initializes the node.
         *
         * @param data to be stored.
         * @param lft  left sibling.
         * @param rt   right sibling.
         */
        public LazySTNode(E data, LazySTNode lft, LazySTNode rt) {
            this.lftChild = lft;
            this.rtChild = rt;
            this.data = data;
            this.deleted = false;
        }

        /**
         * It initializes the empty node.
         */
        public LazySTNode() {
            this(null, null, null);
        }
    }
}