/* name: Brian Fiala
 * date: 6/10/14
 * Professor Jesse Cecil, CS1B, Foothill Community College
 * Assignment 9 Option A
 * File: Foothill.java
 * 
 * Class Foothill: instantiates an FHsdTree and adds 30 nodes to it to simulate
 * the contents of a room scene. Then uses console output and calls to the
 * public methods in the FHsdTreeNode class to comprehensively test this node
 * class and to display the tree at each stage.
 * 
 * Classes FHsdTree, FHsdTreeNode, and Foothill are modifications of classes
 * FHgTree, FHgTreeNode, and Foothill written by Professor Jesse Cecil, and 
 * used with explicit permission. 
 */

public class Foothill
{ 
   public static void main(String[] args) throws Exception
   {
      FHsdTree<String> sceneTree = new FHsdTree<String>();
      FHsdTreeNode<String> tn;
      
      // create a scene in a room
      tn = sceneTree.addChild(null, "room");
      
      // add three objects to the scene tree
      sceneTree.addChild(tn, "Lily the canine");
      sceneTree.addChild(tn, "Miguel the human");
      sceneTree.addChild(tn, "table");
      // add some parts to Miguel
      tn = sceneTree.find("Miguel the human");
      
      // Miguel's left arm
      tn = sceneTree.addChild(tn, "torso");
      tn = sceneTree.addChild(tn, "left arm");
      tn =  sceneTree.addChild(tn, "left hand");
      sceneTree.addChild(tn, "thumb");
      sceneTree.addChild(tn, "index finger");
      sceneTree.addChild(tn, "middle finger");
      sceneTree.addChild(tn, "ring finger");
      sceneTree.addChild(tn, "pinky");
      
      // Miguel's right arm
      tn = sceneTree.find("Miguel the human");
      tn = sceneTree.find(tn, "torso", 0);
      tn = sceneTree.addChild(tn, "right arm");
      tn =  sceneTree.addChild(tn, "right hand");
      sceneTree.addChild(tn, "thumb");
      sceneTree.addChild(tn, "index finger");
      sceneTree.addChild(tn, "middle finger");
      sceneTree.addChild(tn, "ring finger");
      sceneTree.addChild(tn, "pinky");
      
      // add some parts to Lily
      tn = sceneTree.find("Lily the canine");
      tn = sceneTree.addChild(tn, "torso");
      sceneTree.addChild(tn, "right front paw");
      sceneTree.addChild(tn, "left front paw");
      sceneTree.addChild(tn, "right rear paw");
      sceneTree.addChild(tn, "left rear paw");
      sceneTree.addChild(tn, "spare mutant paw");
      sceneTree.addChild(tn, "wagging tail");
      
      // add some parts to table
      tn = sceneTree.find("table");
      sceneTree.addChild(tn, "north east leg");
      sceneTree.addChild(tn, "north west leg");
      sceneTree.addChild(tn, "south east leg");
      sceneTree.addChild(tn, "south west leg");
      
      System.out.println("\nBeginning comprehensive test of class FHsdTree...");
      
      // display tree pre-deletion
      System.out.println("\n____Physical tree, pre-deletion____");
      sceneTree.displayPhysical();
      
      // display physical & virtual size of tree pre-deletion
      System.out.println("\nPhysical size of tree = "
         + sceneTree.sizePhysical());
      System.out.println("Virtual size of tree = " + sceneTree.size());
      
      // soft-delete some nodes
      System.out.println("\n_________________________________________________");
      System.out.println("Soft-deleting some nodes...\n");
      sceneTree.remove("spare mutant paw");
      sceneTree.remove("Miguel the human");
      sceneTree.remove("an imagined higgs boson"); // non-existent node
      
      // display physical and virtual tree post-deletion
      System.out.println("____Physical tree, post-deletion____");
      sceneTree.displayPhysical();
      System.out.println("\n____Virtual tree, post-deletion____");
      sceneTree.display();
      
      // display physical & virtual size of tree post-deletion
      System.out.println("\nPhysical size of tree = "
         + sceneTree.sizePhysical());
      System.out.println("Virtual size of tree = " + sceneTree.size());
      
      // garbage collect (remove) soft-deleted nodes
      System.out.println("\n_________________________________________________");
      System.out.println("Collecting garbage...\n");
      sceneTree.garbageCollection();
      
      // display physical & virtual tree post-garbage collection
      System.out.println("____Physical tree, post-garbage collection____");
      sceneTree.displayPhysical();
      System.out.println("\n____Virtual tree, post-garbage collection____");
      sceneTree.display();
      
      // display physical & virtual size of tree post-garbage collection
      System.out.println("\nPhysical size of tree = "
         + sceneTree.sizePhysical());
      System.out.println("Virtual size of tree = " + sceneTree.size());
      
      // soft-delete entire tree
      System.out.println("\n_________________________________________________");
      System.out.println("Soft-deleting entire tree...\n");
      sceneTree.remove("room");
      
      // display physical tree post-deletion
      System.out.println("____Physical tree, post-entire deletion____");
      sceneTree.displayPhysical();
      
      // clone the tree
      System.out.println("\n_________________________________________________");
      System.out.println("Cloning soft-deleted tree...\n");
      FHsdTree<String> newTree = (FHsdTree<String>)sceneTree.clone();
      
      // display physical tree clone
      System.out.println("____Physical tree clone____");
      newTree.displayPhysical();
      
      // display emptyPhysical and empty
      System.out.println("\nTree is physically empty: "
         + newTree.emptyPhysical());
      System.out.println("Tree is virtually empty: " + newTree.empty());
      
      // clear entire cloned tree
      System.out.println("\n_________________________________________________");
      System.out.println("Clearing entire cloned tree...\n");
      newTree.clear();
      
      // display physical and virtual tree clone post-clear
      System.out.println("____Physical tree clone, post-entire deletion____");
      newTree.displayPhysical();
      System.out.println("\n____Virtual tree clone, post-entire deletion____");
      newTree.display();
      
      // display emptyPhysical and empty
      System.out.println("\nTree is physically empty: "
         + newTree.emptyPhysical());
      System.out.println("Tree is virtually empty: " + newTree.empty() + "\n");
   } // end main
} // end class Foothill -------------------------------------------------------

/*-----------------------------Paste From Console------------------------------

Beginning comprehensive test of class FHsdTree...

____Physical tree, pre-deletion____
room
 table
  south west leg
  south east leg
  north west leg
  north east leg
 Miguel the human
  torso
   right arm
    right hand
     pinky
     ring finger
     middle finger
     index finger
     thumb
   left arm
    left hand
     pinky
     ring finger
     middle finger
     index finger
     thumb
 Lily the canine
  torso
   wagging tail
   spare mutant paw
   left rear paw
   right rear paw
   left front paw
   right front paw

Physical size of tree = 30
Virtual size of tree = 30

_________________________________________________
Soft-deleting some nodes...

____Physical tree, post-deletion____
room
 table
  south west leg
  south east leg
  north west leg
  north east leg
 Miguel the human (D)
  torso (D)
   right arm (D)
    right hand (D)
     pinky (D)
     ring finger (D)
     middle finger (D)
     index finger (D)
     thumb (D)
   left arm (D)
    left hand (D)
     pinky (D)
     ring finger (D)
     middle finger (D)
     index finger (D)
     thumb (D)
 Lily the canine
  torso
   wagging tail
   spare mutant paw (D)
   left rear paw
   right rear paw
   left front paw
   right front paw

____Virtual tree, post-deletion____
room
 table
  south west leg
  south east leg
  north west leg
  north east leg
 Lily the canine
  torso
   wagging tail
   left rear paw
   right rear paw
   left front paw
   right front paw

Physical size of tree = 30
Virtual size of tree = 13

_________________________________________________
Collecting garbage...

____Physical tree, post-garbage collection____
room
 table
  south west leg
  south east leg
  north west leg
  north east leg
 Lily the canine
  torso
   wagging tail
   left rear paw
   right rear paw
   left front paw
   right front paw

____Virtual tree, post-garbage collection____
room
 table
  south west leg
  south east leg
  north west leg
  north east leg
 Lily the canine
  torso
   wagging tail
   left rear paw
   right rear paw
   left front paw
   right front paw

Physical size of tree = 13
Virtual size of tree = 13

_________________________________________________
Soft-deleting entire tree...

____Physical tree, post-entire deletion____
room (D)
 table (D)
  south west leg (D)
  south east leg (D)
  north west leg (D)
  north east leg (D)
 Lily the canine (D)
  torso (D)
   wagging tail (D)
   left rear paw (D)
   right rear paw (D)
   left front paw (D)
   right front paw (D)

_________________________________________________
Cloning soft-deleted tree...

____Physical tree clone____
room (D)
 table (D)
  south west leg (D)
  south east leg (D)
  north west leg (D)
  north east leg (D)
 Lily the canine (D)
  torso (D)
   wagging tail (D)
   left rear paw (D)
   right rear paw (D)
   left front paw (D)
   right front paw (D)

Tree is physically empty: false
Tree is virtually empty: true

_________________________________________________
Clearing entire cloned tree...

____Physical tree clone, post-entire deletion____

____Virtual tree clone, post-entire deletion____

Tree is physically empty: true
Tree is virtually empty: true

----------------------------End Paste From Console---------------------------*/