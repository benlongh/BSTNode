import java.util.ArrayList;

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

	public String getKey() {

		return this.data;
	}

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

	public int getHeight() {

		return helper(this);
	}

	private int helper(BSTNode ref) {
		if (ref == null)
			return -1;
		else
			return 1 + Math.max(helper(ref.left), helper(ref.right));
	}

	public int getCount() {

		return helper2(this);
	}

	private int helper2(BSTNode ref) {
		if (ref == null)
			return 0;
		else
			return 1 + helper2(ref.left) + helper2(ref.right);
	}

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

	public String toDotFile(boolean b) {
		String represent = "vertex" + this.hashCode();
		result3 = "";
		result3 = result3 + "digraph" + "\n" + "{" + "\n";
		result3 = result3 + represent + " [label=\"" + this.getKey() + "\"]" + ";" + "\n";
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