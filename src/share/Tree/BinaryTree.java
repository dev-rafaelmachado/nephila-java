package share.Tree;

import java.util.ArrayList;
import java.util.List;

public class BinaryTree {
    private BinaryTreeNode head;

    public BinaryTree() {
        head = null;
    }

    public void insert(String data) {
        head = insertCall(head, data);
    }

    private BinaryTreeNode insertCall(BinaryTreeNode head, String data) {
        if (head == null) {
            head = new BinaryTreeNode(data);
            return head;
        }
        if (data.compareTo(head.getData()) < 0) {
            head.setLeft(insertCall(head.getLeft(), data));
        } else if (data.compareTo(head.getData()) > 0) {
            head.setRight(insertCall(head.getRight(), data));
        } else if (data.equals(head.getData())) {
            System.out.println("This value already exists in the tree");
        }
        return head;
    }

    public boolean hasNode(String label) {
        return hasNodeCall(head, label) != null;
    }

    public List<BinaryTreeNode> returnAllNodes() {
        List<BinaryTreeNode> nodes = new ArrayList<>();
        returnAllNodesCall(head, nodes);
        return nodes;
    }

    private void returnAllNodesCall(BinaryTreeNode head, List<BinaryTreeNode> nodes) {
        if (head != null) {
            returnAllNodesCall(head.getLeft(), nodes);
            nodes.add(head);
            returnAllNodesCall(head.getRight(), nodes);
        }
    }

    private BinaryTreeNode hasNodeCall(BinaryTreeNode head, String data) {
        if (head == null) {
            return null;
        }
        if (data.equals(head.getData())) {
            return head;
        }
        if (data.compareTo(head.getData()) < 0) {
            return hasNodeCall(head.getLeft(), data);
        }
        return hasNodeCall(head.getRight(), data);
    }

    public void printPreOrder() {
        printPreOrderCall(head);
        System.out.println();
    }

    private void printPreOrderCall(BinaryTreeNode head) {
        if (head != null) {
            printPreOrderCall(head.getLeft());
            System.out.print(head.getData() + " ");
            printPreOrderCall(head.getRight());
        }
    }

    public void printInOrder() {
        printInOrderCall(head);
        System.out.println();
    }

    private void printInOrderCall(BinaryTreeNode head) {
        if (head != null) {
            printInOrderCall(head.getLeft());
            System.out.print(head.getData() + " ");
            printInOrderCall(head.getRight());
        }
    }

    public void printPostOrder() {
        printPostOrderCall(head);
        System.out.println();
    }

    private void printPostOrderCall(BinaryTreeNode head) {
        if (head != null) {
            printPostOrderCall(head.getLeft());
            printPostOrderCall(head.getRight());
            System.out.print(head.getData() + " ");
        }
    }

    public void removeLargestNumberInTree() {
        head = removeLargestNumberInTreeCall(head);
    }

    private BinaryTreeNode removeLargestNumberInTreeCall(BinaryTreeNode head) {
        if (head == null) {
            return null;
        } else if (head.getRight() == null) {
            return head.getLeft();
        } else {
            head.setRight(removeLargestNumberInTreeCall(head.getRight()));
            return head;
        }
    }

    public void removeSmallestNumberInTree() {
        head = removeSmallestNumberInTreeCall(head);
    }

    private BinaryTreeNode removeSmallestNumberInTreeCall(BinaryTreeNode head) {
        if (head == null) {
            return null;
        } else if (head.getLeft() == null) {
            return head.getRight();
        } else {
            head.setLeft(removeSmallestNumberInTreeCall(head.getLeft()));
            return head;
        }
    }

    public String toString() {
        return toStringCall(head);
    }

    private String toStringCall(BinaryTreeNode head) {
        if (head == null) {
            return "";
        }
        return toStringCall(head.getLeft()) + head.getData() + " " + toStringCall(head.getRight());
    }
}
