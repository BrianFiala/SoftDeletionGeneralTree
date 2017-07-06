// file TraverserG.java

/* A generic interface useful for implementation as a function object for
 * traversing generic general trees
 */
interface TraverserG<E>
{
   public void visit(E x);
} // end interface TraverserG -------------------------------------------------