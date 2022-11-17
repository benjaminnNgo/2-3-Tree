
public class TwoThreeTree {
	private TwoThreeNode root;

	public TwoThreeTree() {
		this.root = null;
	}

	public boolean search(int searchKey) {
		boolean found = false;
		TwoThreeNode curr = this.root;
		if (root != null) {
			curr = findClosestLeafNode(searchKey);
			found = curr.hasFirstKeyEqual(searchKey);
		}
		return found;
	}

	public void add(int key) {
		TwoThreeNode newNode = new TwoThreeNode(key, null);
		if (this.root == null) {
			this.root = new TwoThreeNode(key, root);
		} else if (this.root.children == null) {
			addSecondNode(key);
		} else {
			TwoThreeNode closestKeyNode = findClosestLeafNode(key);
			TwoThreeNode currParent = closestKeyNode.parent;

			if (closestKeyNode.hasFirstKeyEqual(key)) return;
			if (currParent != null) {
				if (currParent.hasTwoChildren()) {
					this.root = currParent.addThirdNode(newNode, key, this.root);
				} else if (currParent.hasThreeChildren()) {
					this.root = currParent.addFourthNode(newNode, key, this.root);
				}
			}
		}
	}
	
	private void addSecondNode(int key) {
		TwoThreeNode newNode = new TwoThreeNode(key, null);
		TwoThreeNode existNode = root;
		
		if (key > root.getLeafNodeKey()) {
			this.root = new TwoThreeNode(key, null, root, newNode);
		} else {
			this.root = new TwoThreeNode(root.getLeafNodeKey(), null, newNode, root);
		}
		existNode.parent = this.root;
		newNode.parent = this.root;
	}
	
	private TwoThreeNode findClosestLeafNode(int searchKey) {
		TwoThreeNode curr = this.root;
		if (root != null) {
			while (curr.children != null) {
				curr = curr.moveToChild(searchKey);
			}
		}
		return curr;
	}
	
	public void printTree() {
		printTree(this.root);
	}
	
	private void printTree(TwoThreeNode curr) {
		if(curr.children == null) {
			System.out.print(curr.keys[0] + " ");
		}else {
			printTree(curr.children[0]);
			printTree(curr.children[1]);
			if(curr.hasThreeChildren()) {
				printTree(curr.children[2]);
			}
		}
	}

}
