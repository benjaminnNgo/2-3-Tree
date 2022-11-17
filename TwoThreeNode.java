
public class TwoThreeNode {
	private final int TWO_NODE_NUM_KEY = 1;
	private final int THREE_NODE_NUM_KEY = 2;
	
	private final int LEFT_CHILD = 0;
	private final int MID_CHILD = 1;
	private final int RIGHT_CHILD = 2;
	
	private final int FIRST_KEY = 0;
	private final int SECOND_KEY = 1;
	
	public TwoThreeNode parent;
	public int[]keys;
	public int numsKeys;
	public TwoThreeNode[]children;
	
	public TwoThreeNode(int dataItem, TwoThreeNode parent) {
		this.parent = parent;
		keys = new int[1];
		keys[0]= dataItem;
		this.numsKeys = 1;
		this.children = null;
	}
	
	public TwoThreeNode(int key, TwoThreeNode parent, TwoThreeNode left,TwoThreeNode right) {
		this.parent = parent;
		this.keys = new int[2];
		this.keys[0] = key;
		numsKeys = 1;
		children = new TwoThreeNode[3];
		children[0] = left;
		children[1] = right;
	}
	
	public TwoThreeNode moveToChild(int searchKey) {
		int index = this.numsKeys -1 ;
		while(index >= 0 && searchKey < keys[index]) {
			index--;
		}
		return children[index + 1];
	}
	
	public TwoThreeNode addThirdNode(TwoThreeNode newNode,int newKey,TwoThreeNode root) {
		if(newKey > this.keys[FIRST_KEY]) {
			
			this.keys[SECOND_KEY] = newKey;
			children[RIGHT_CHILD] = newNode;
		}else if(newKey > children[LEFT_CHILD].keys[FIRST_KEY]) {
			
			keys[SECOND_KEY] = keys[FIRST_KEY];
			keys[FIRST_KEY] = newKey;
			
			children[RIGHT_CHILD] = children[MID_CHILD];
			children[MID_CHILD] = newNode;
		}else {
			
			keys[SECOND_KEY] = keys[FIRST_KEY];
			
			children[RIGHT_CHILD] = children[MID_CHILD];
			children[MID_CHILD] = children[LEFT_CHILD];
			children[LEFT_CHILD] = newNode;
			
			keys[FIRST_KEY] = children[MID_CHILD].keys[FIRST_KEY];
		}
		this.numsKeys ++;
		newNode.parent = this;
		return root;
	}
	
	public TwoThreeNode addFourthNode(TwoThreeNode newNode, int newKey, TwoThreeNode root) {
		int pushUpKey = findMedianToPushUp(newKey);
		TwoThreeNode pushUpNode = splitNode(newNode, newKey, root);
		
		children[RIGHT_CHILD] = null;
		numsKeys--;
		if(this == root) {	
			root = new TwoThreeNode(pushUpKey, null, root, pushUpNode);
			pushUpNode.parent = root;
		} else if(this.parent.numsKeys == TWO_NODE_NUM_KEY ) {
			root = this.parent.addThirdNode(pushUpNode, pushUpKey,root);
		}else {
			root = this.parent.addFourthNode(pushUpNode, pushUpKey, root);
			
		}
		return root;
		
	}
	
	private int findMedianToPushUp(int newKey) {
		int pushUpKey;
		if(newKey > keys[SECOND_KEY]) {
			pushUpKey = keys[SECOND_KEY];
		}else if( newKey < keys[FIRST_KEY]) {
			pushUpKey = keys[FIRST_KEY];
		}else {
			pushUpKey = newKey;
		}
		return pushUpKey;
	}
	
	private TwoThreeNode splitNode(TwoThreeNode newNode, int newKey, TwoThreeNode root) {
		TwoThreeNode pushUpNode;
		int newNodeKey = newNode.keys[FIRST_KEY];
		int rightChildKey = children[RIGHT_CHILD].keys[FIRST_KEY];
		int midChildKey = children[MID_CHILD].keys[FIRST_KEY];
		int leftChildKey = children[LEFT_CHILD].keys[FIRST_KEY];
		
		if(newNodeKey > midChildKey) {
			if(newNodeKey > rightChildKey) {
				pushUpNode = new TwoThreeNode(newKey, null, children[RIGHT_CHILD], newNode);
			}else {
				pushUpNode = new TwoThreeNode(keys[SECOND_KEY], null, newNode, children[RIGHT_CHILD]);
			}
			children[RIGHT_CHILD].parent = pushUpNode;
			newNode.parent = pushUpNode;
		}else {
			pushUpNode = new TwoThreeNode(keys[SECOND_KEY], null, children[MID_CHILD], children[RIGHT_CHILD]);
			children[RIGHT_CHILD].parent = pushUpNode;
			children[MID_CHILD].parent = pushUpNode;
			newNode.parent = this;
			
			if(newNodeKey > leftChildKey) {
				children[MID_CHILD] = newNode;
				keys[FIRST_KEY] = newNodeKey;
			}else {
				children[MID_CHILD] = children[LEFT_CHILD];
				children[LEFT_CHILD] = newNode;
				keys[FIRST_KEY] = midChildKey;
			}
		}
		return pushUpNode;
	}
	
	public boolean hasTwoChildren() {
		return this.numsKeys == TWO_NODE_NUM_KEY;
	}
	
	public boolean hasThreeChildren() {
		return this.numsKeys == THREE_NODE_NUM_KEY;
	}
	
	public boolean hasFirstKeyEqual(int key) {
		return this.keys[FIRST_KEY] == key;
	}
	
	public int getLeafNodeKey() {
		return this.keys[FIRST_KEY];
	}

}
