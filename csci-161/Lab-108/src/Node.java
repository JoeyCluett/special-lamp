/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author joey
 */
public class Node<E> implements Position<E> {
    private E element;
    private Node<E> parent;
    private Node<E> left;
    private Node<E> right;
    
    @Override
    public E getElement() throws IllegalStateException {
        return element;
    }
    
    public Node(E element, Node<E> above, Node<E> leftChild, Node<E> rightChild) {
        this.element = element;
        parent = above;
        left   = leftChild;
        right  = rightChild;
    }
    
    public Node<E> getParent() { return parent; }
    public Node<E> getLeft()   { return left; }
    public Node<E> getRight()  { return right; }

    public void setElement(E element)     { this.element = element; }
    public void setParent(Node<E> parent) { this.parent = parent; }
    public void setLeft(Node<E> left)     { this.left = left; }
    public void setRight(Node<E> right)   { this.right = right; }
}
