package lazyTrees;

/**
 * It provides interface used for all tree algorithms.
 * @param <E> genetic type.
 *
 * @author Pires, Marilize.
 */
public interface Traverser<E> {
    void visit(E x);
}