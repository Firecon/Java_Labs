package com.company;

import org.w3c.dom.Node;

import java.util.*;
import java.util.function.Consumer;

public class BinaryTree<T extends Comparable<T>>
{

    public BinaryTree(T elem)
    {
        root = new Node<T>(elem);
        root.value = elem;
        root.left = null;
        root.right = null;
    }

    public static class Node<T1 extends Comparable<T1>>
    {
    private T1 value;
    private Node<T1> left;
    private Node<T1> right;

    public Node(T1 value)
    {
        this.value = value;
        this.left = null;
        this.right = null;
    }

    public Node(T1 value, Node<T1> left, Node<T1> right)
    {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public final T1 getValue() {
                return value;
            }

    public final Node<T1> getLeft() {
                return left;
            }

    public final Node<T1> getRight() {
                return right;
            }

    public boolean hasLeft() {
                return left != null;
            }

    public boolean hasRight() {
                return right != null;
            }

    private boolean FindElem(T1 value)
    {
            boolean Flag = false;
            //boolean Flag2 = false;
            if (value.compareTo(this.value) < 0) {
            if (this.left == null) {
                 Flag = false;
            }
            else {
              return this.left.FindElem(value);
            }
            }
            else if(value.compareTo(this.value) > 0) {
            if(this.right == null) {
               Flag = false;
            }
            else {
                return this.right.FindElem(value);
            }
            }
            else if (value.compareTo(this.value) == 0) {
            Flag = true;
            }
                return Flag;
            }


            private void AddElem(T1 value)
            {
                Node<T1> new_node = new Node<T1>(value);
                if (value.compareTo(this.value) < 0)
                {
                    if (this.left == null) {
                        this.left = new_node;
                    }
                    else {
                        this.left.AddElem(value);
                    }
                }
                else if (value.compareTo(this.value) > 0)
                {
                    if (this.right == null) {
                        this.right = new_node;
                    }
                    else {
                        this.right.AddElem(value);
                    }
                }
            }

        public void preorderTraverse(Node<T1> node)
        {
            System.out.print(node.value + " ");
            if (hasLeft())
            {
                left.preorderTraverse(node.left); }
            if (hasRight()) {
                right.preorderTraverse(node.right);
            }
        }

        public void inorderTraverse(Node<T1> node) {
            if (hasLeft()) {
                left.inorderTraverse(node.left); }
            System.out.print(node.value + " ");
            if (hasRight()) {
                right.inorderTraverse(node.right); }
        }

        public void postorderTraverse(Node<T1> node) {
            if (hasLeft())
            {
                left.postorderTraverse(node.left); }
            if (hasRight())
            {
                right.postorderTraverse(node.right); }
            System.out.print(node.value + " ");
        }
    }


    public Node<T> root;


    //Binary Tree methods

    public final Node<T> getRoot() {
        return root;
    }

    public void AddNode(T value)
    {
        Node<T> new_node = new Node<T>(value);

        if (value.compareTo(root.value) < 0)
        {
            if (root.left == null)
            {
                root.left = new_node; }
            else
            {
                root.left.AddElem(value); }
        }
        else if (value.compareTo(root.value) > 0 )
        {
            if (root.right == null)
            {
                root.right = new_node; }
            else
            {
                root.right.AddElem(value); }
        }
    }

    public boolean delete (T t)
    {
        if (root != null) {
            Node current = root;
            Node parent = root;
            boolean isLeftChild = true;
            while (current.value.compareTo(t) != 0) {
                parent = current;
                if (current.value.compareTo(t) > 0) {
                    current = current.left;
                    isLeftChild = true;
                } else
                {
                    current = current.right;
                    isLeftChild = false;
                }
                if (current == null) {
                    return false;
                }
            }

            /*нет потомков*/

            if (current.right == null && current.left == null) {
                if (current == root) {
                    root = null;
                } else if (isLeftChild) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
                return true;
            }

            /*один потомок*/

            if (current.right == null) {
                if (current == root)
                {
                    root = current.left;
                } else if (isLeftChild)
                {
                    parent.left = current.left;
                } else
                {
                    parent.right = current.left;
                }
                return true;
            } else if (current.left == null) {
                if (current == root) {
                    root = current.right;
                } else if (isLeftChild) {
                    parent.left = current.right;
                } else {
                    parent.right = current.right;
                }
                return true;
            }

            /* Два потомка */

            if (current.left != null && current.right != null) {
                Node successor = findSuccessor(current);
                if (current == root) {
                    root = successor;
                } else if (isLeftChild) {
                    parent.left = successor;
                } else {
                    parent.right = successor;
                }
                successor.left = current.left;
                return true;
            }
        }
        return false;
    }


    private Node findSuccessor (Node delNode) //поиск преемника
    {
        Node parentSuccessor = delNode;
        Node successor = delNode;
        Node current = delNode.right;
        while (current != null)
        {
            parentSuccessor = successor;
            successor = current;
            current = current.left;
        }
        if (successor!=delNode.right)
        {
            parentSuccessor.left = successor.right;
            successor.right = delNode.right;
        }
        return successor;
    }

    public boolean FindNode(T value)
    {
        boolean flag = false;
        if(value.compareTo(root.value) < 0)
        {
            flag = root.left.FindElem(value);
        }
        else if(value.compareTo((root.value)) > 0)
        {
            flag = root.right.FindElem(value);
        }
        else if(value.compareTo(root.value) == 0)
        {
            flag = true;
        }
        return flag;
    }

    public void preorderTraverse(Node<T> root) {
        if (root != null)
        {
            root.preorderTraverse(root);
        }
    }

    public void inorderTraverse(Node<T> root) {
        if (root != null)
        {
            root.inorderTraverse(root);
        }
    }

    public void postorderTraverse(Node<T> root) {
        if (root != null)
        {
            root.postorderTraverse(root);
        }
    }
}