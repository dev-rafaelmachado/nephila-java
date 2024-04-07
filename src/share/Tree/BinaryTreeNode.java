package share.Tree;

public class BinaryTreeNode {
	private String data;
	private BinaryTreeNode left;
	private BinaryTreeNode right;

	public BinaryTreeNode(String data) {
		this.data = data;
		this.left = null;
		this.right = null;
	}

	public String getData() {
		return data;
	}

	public BinaryTreeNode getLeft() {
		return left;
	}

	public BinaryTreeNode getRight() {
		return right;
	}

	public void setLeft(BinaryTreeNode left) {
		this.left = left;
	}

	public void setRight(BinaryTreeNode right) {
		this.right = right;
	}
}