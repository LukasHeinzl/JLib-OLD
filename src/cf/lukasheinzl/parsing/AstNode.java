package cf.lukasheinzl.parsing;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is the base of all AST-nodes.
 * 
 * @author Lukas Heinzl
 *
 */
public class AstNode{

	protected List<AstNode> children;

	/**
	 * Constructs a new AstNode without any children
	 */
	public AstNode(){
		this(new ArrayList<>());
	}

	/**
	 * Constructs a new AstNode with the given children
	 * 
	 * @param children
	 *            The children of this node
	 */
	public AstNode(List<AstNode> children){
		this.children = children;
	}

	/**
	 * Returns the children if any of this node.
	 * 
	 * @return The children if any
	 */
	public List<AstNode> getChildren(){
		return children;
	}

	/**
	 * Add an AstNode to this one
	 * 
	 * @param child
	 *            The AstNode to add
	 */
	public void addChild(AstNode child){
		children.add(child);
	}

}
