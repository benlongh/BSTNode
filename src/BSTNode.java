// BSTNode
// CSC 345 Spring 16 - Project 1
// Benlong Huang	
//
import java.util.ArrayList;

// First, construct the tree and the private that we will always be used in the code below.
public class BSTNode {
	private ArrayList<String> list = new ArrayList<String>();
	private BSTNode left;
	private BSTNode right;
	private String data;
	private String result3;

	public BSTNode(String dataRef) {
		data = dataRef;
		left = null;
		right = null;
	}

	// use the helper recursion to find whether it has next or not and the
	// condition that ref is greater, less and equal to dataString.
	public BSTNode insert(String val) {

		return insertHelper(val, this);

	}

	private BSTNode insertHelper(String dataString, BSTNode ref) {
		if (dataString.equals(ref.data))
			throw new IllegalArgumentException();
		else if (dataString.compareTo(ref.data) < 0 && ref.left == null) {

			ref.left = new BSTNode(dataString);
			return ref;
		}

		else if (dataString.compareTo(ref.data) > 0 && ref.right == null) {

			ref.right = new BSTNode(dataString);
			return ref;
		}

		else {

			if (dataString.compareTo(ref.data) < 0 && ref.left != null)
				return insertHelper(dataString, ref.left);
			if (dataString.compareTo(ref.data) > 0 && ref.right != null)
				return insertHelper(dataString, ref.right);
		}
		return ref;
	}

	// just get the content.
	public String getKey() {

		return this.data;
	}

	// left and right are the same meaning, need to check whether current
	// position has data or not.
	public BSTNode getLeft() {
		if (this.data == null)
			return null;
		else
			return this.left;
	}

	public BSTNode getRight() {
		if (this.data == null)
			return null;
		else
			return this.right;
	}

	// use helper recursion to calculate the height, compare left and right that
	// which side is larger, always go to left can find current maximum height.
	// The recursion goes one time then the height add 1.

	public int getHeight() {

		return helper(this);
	}

	private int helper(BSTNode ref) {
		if (ref == null)
			return -1;
		else
			return 1 + Math.max(helper(ref.left), helper(ref.right));
	}

	// use recursion to count every leaf. recursion goes one time, then like 1 +
	// left + right, if there is no leaf, then add 0.
	public int getCount() {

		return helper2(this);
	}

	private int helper2(BSTNode ref) {
		if (ref == null)
			return 0;
		else
			return 1 + helper2(ref.left) + helper2(ref.right);
	}

	// need to create an array list result, use the recursion to put all the
	// left first in the list, then
	// add right. then return the list. just like inorder.
	public String[] toArray() {
		list.clear();
		String[] result = new String[getCount()];
		ArrayHelper(this);
		for (int i = 0; i < getCount(); i++) {
			result[i] = list.get(i);
		}

		return result;
	}

	private void ArrayHelper(BSTNode ref) {
		if (ref != null) {
			ArrayHelper(ref.left);
			list.add(ref.data);
			ArrayHelper(ref.right);
		}
	}

	// same as last one, this one is pre-order, just need to change the position
	// of the instructions.
	public String[] toArray_preOrder() {
		list.clear();
		String[] result2 = new String[getCount()];
		OrderHelper(this);
		for (int i = 0; i < getCount(); i++) {
			result2[i] = list.get(i);
		}

		return result2;
	}

	private void OrderHelper(BSTNode ref) {
		if (ref != null) {
			list.add(ref.data);
			OrderHelper(ref.left);
			OrderHelper(ref.right);
		}
	}

	// use the recursion to use the vertax + random number instead of the
	// content of key, check each side has next or not. add the string, change
	// to another space and label that required. digraph just appear once, so
	// write it in the public not in recursion.

	public String toDotFile(boolean b) {
		String represent = "vertex" + this.hashCode();
		result3 = "";
		result3 = result3 + "digraph" + "\n" + "{" + "\n";
		result3 = result3 + represent + " [label=\"" + this.getKey() + "\"]"
				+ ";" + "\n";
		FileHelper(this, represent);
		result3 = result3 + "}";
		return result3;
	}

	private void FileHelper(BSTNode ref, String represent) {
		if (ref == null)
			return;
		String representLeft = "";
		String representRight = "";
		if (ref.left != null) {
			representLeft = representLeft + "vertex" + ref.left.hashCode();
			result3 = result3 + representLeft + " [label=\""
					+ ref.left.getKey() + "\"]" + ";" + "\n";
			result3 = result3 + represent + " -> " + representLeft
					+ "[label=\"left\"];" + "\n";
		}

		if (ref.right != null) {
			representRight = representRight + "vertex" + ref.right.hashCode();
			result3 = result3 + representRight + " [label=\""
					+ ref.right.getKey() + "\"]" + ";" + "\n";
			result3 = result3 + represent + " -> " + representRight
					+ "[label=\"right\"];" + "\n";
		}

		FileHelper(ref.left, representLeft);
		FileHelper(ref.right, representRight);
	}
}