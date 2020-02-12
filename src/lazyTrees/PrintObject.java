package lazyTrees;

/**
 * It traverses and prints out data.
 *
 * @param <E> genetic type.
 * @author Pires, Marilize.
 */
class PrintObject<E> implements Traverser<E> {

    @Override
    public void visit(E x) {
        System.out.println(x + " ");
    }
}