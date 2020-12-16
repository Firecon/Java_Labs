package com.company;

public class Main
{

    public static void main(String[] args)
    {
        BinaryTree<Integer> tree = new BinaryTree<Integer>(55) ;


        tree.AddNode(25);      //        55
        tree.AddNode(64);      //       /   \
        tree.AddNode(65);      //      25     64
        tree.AddNode(14);      //     /  \    /  \
        tree.AddNode(29);      //    14   29 60  65
        tree.AddNode(60);      //   / \
        tree.AddNode(7);       //  7   15
        tree.AddNode(15);




        System.out.println(tree.FindNode(15) ? "Yes" : "No");

        tree.delete(25);

        System.out.println(tree.FindNode(15) ? "Yes" : "No");


        System.out.println(tree.FindNode(15) ? "Yes" : "No");

        System.out.println(tree.FindNode(16) ? "Yes" : "No");

        System.out.println(tree.FindNode(7) ? "Yes" : "No");

        System.out.println("\nTraverse\n");

        tree.preorderTraverse(tree.getRoot());
        System.out.println();

        tree.inorderTraverse(tree.getRoot());
        System.out.println();

        tree.postorderTraverse(tree.getRoot());


    }
}
