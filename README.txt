project folder:
MarilizePi-cs1c-project06

Brief description of submitted files:

src/lazyTrees/Item.java
    - One object of Item class represents one item in the inventory, with two class members.

src/lazyTrees/LazySearchTree.java
    - LazySearchTree is a Binary Search Tree (BST) algorithm that implements lazy deletion to solve the assigned tasks.
     This is an optimized version including collectGarbage() method to clean up the tree with hard deletion.

src/lazyTrees/PrintObject.java
    - It traverses and prints out data.

src/lazyTrees/SuperMarket.java
    - Simulates the market scenario of buying and adding items to a store's inventory.
    - Implements BST with lazy deletion to keep track of total inventory ("deleted" + non deleted)
      and current inventory (non deleted only).
    - Contains main.

src/lazyTrees/Traverser.java
    - It provides interface used for all tree algorithms.

-----

resources/inventory_empty.txt
    - Tests removing an item from empty inventory.

resources/inventory_invalid_removal.txt
    - Tests removing an item that might not exist (previously lazily deleted).

resources/inventory_log.txt
    - Tests implementation of LazySearchTree by adding and removing items from inventory.

resources/inventory_no_item.txt
    - Tests removing item that does not exist (never in the inventory).

resources/short.txt
    - Tests removing an item from a tree that has right and left children.

resources/RUN.txt
    - Console outputs various tests/runs of SuperMarket.java class.

-----

.gitignore
    - .gitignore content tells git to ignore specified files or folder.

-----

README.txt
    - Description of submitted files.