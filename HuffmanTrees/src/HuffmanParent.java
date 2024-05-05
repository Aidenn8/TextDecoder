import java.util.Map;

/**
 * Parent node in Huffman tree. Node that valid Huffman Tree nodes always
 * have two children (parents) or none (leaves). Children are labeled
 * as zero and one although this is easily represented as left and right.
 */
class HuffmanParent extends HuffmanNode {
  HuffmanNode zeroChild;
  HuffmanNode oneChild;

  //=================================\\
  // CONSTRUCTOR & METHODS TO FINISH \\
  //=================================\\
  
  /**
   * Sole constructor. Constructs parent node for two children,
   * calculates the resulting total frequency, and sets childrens'
   * parent references.
   * 
   * @param zero the "left" child node. Must be non-null.
   * @param one  the "right" child node. Must be non-null.
   */
  HuffmanParent(HuffmanNode zero, HuffmanNode one) {
    zeroChild = zero;
    oneChild = one;
    
    zeroChild.parent = this;
    oneChild.parent = this;
    
    //This sets the parent frequency as the sum of the two children
    this.frequency = zeroChild.frequency + oneChild.frequency;
  }
  
  // Overridden functions can inherit Javadoc comments from superclass.
  // This one was abstract, so is required to be implemented here.
  @Override
  void setBitStrings(String prefix, Map<Character, String> bitMap) {
	  if(zeroChild != null) {
		  zeroChild.setBitStrings(prefix+"0", bitMap);
	  }
	  if(oneChild != null) {
		  oneChild.setBitStrings(prefix+"1", bitMap);
	  } 
  }
  
  @Override
  Character decode(CharArrayIterator bits) {
    // If bits run out partway down the tree, return null
    // instead of real char.
    if (!bits.hasNext()) {
      System.err.println("Warning: Ran out of bits during decode.");
      return null;
    }
    
    
    //go through iterator and if reached 
    char c = bits.next();
    // This function call is provided for you for two reasons:
    // 1. Demonstrates how to get next character from iterator.
    // 2. Prevents infinite loop due to iterator that never advances.
    //look at next bit
    //go left or right depending on bit
    if(c =='0') {
    	if(zeroChild!=null) {
    		return zeroChild.decode(bits);
    	}
    	
    }else if(c =='1') {
    	if(oneChild != null) {
    		return oneChild.decode(bits);
    	}
    	
    }
    
    return null;
  }
  
  @Override
  void buildBitRep(StringBuilder sb) {
    // Tree parent nodes are encoded in pre-order as 0s, followed by
    // the codes for each of their children, which may themselves be
    // sub-trees or leaves.
    
    // TODO (OPTIONAL): Complete to add the sub-tree rooted at this 
    //   node to the StringBuilder.
  }
  
  //==================\\
  // COMPLETED METHOD \\
  //==================\\
  
  @Override
  void display(StringBuilder prefix) {
    // For each child, set the prefix indentation correctly, display the
    // child, then "back up" the indentation so the next nodes can display.
    
    // 0 child
    System.out.print("0 ");
    // 0 has been printed on one line for this child, but further
    // descendants on different lines may or may not want the bits
    // to be displayed beforehand.
    if (Driver.DISP_ALL_BITS) {
      prefix.append("0 ");
    }
    else {
      prefix.append("  ");
    }
    // Recursively display 0 child.
    zeroChild.display(prefix);
    // Back up the indentation for the 0 child now that it's done.
    prefix.setLength(prefix.length()-2);

    // Whether it's bits or whitespace, indent the 1 child so it
    // is aligned with the 0 child.
    System.out.print(prefix);
    
    // 1 child
    System.out.print("1 ");
    if (Driver.DISP_ALL_BITS) {
      prefix.append("1 ");
    }
    else {
      prefix.append("  ");
    }
    oneChild.display(prefix);
    prefix.setLength(prefix.length()-2);
  }
}