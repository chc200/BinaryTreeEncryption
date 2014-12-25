import java.util.*;
import java.io.*;

public class TreeEncryption{
	public static void main (String [] args)throws java.io.IOException
	{
		Scanner word = new Scanner(System.in);
		Scanner num = new Scanner(System.in);
		String treeFile;
		String messageFile;
		String outputFile;
		String encryptedFile;
		int eOrD = 0;

		Node<Character> root = new Node<Character>(null,null,null);


		System.out.print("Enter 1 for encryption and 2 for decryption: ");
		eOrD= num.nextInt();

		if(eOrD ==1)
		{
			System.out.print("Please enter the tree file: ");
			treeFile=word.nextLine();
			root.data= buildTree(treeFile, root);

			System.out.print("Please enter the message file: ");
			messageFile=word.nextLine();
			System.out.print("Please enter the output file: ");
			outputFile=word.nextLine();

			encryptMessage(root, messageFile, outputFile);
		}

		if(eOrD ==2)
		{
			System.out.print("Please enter the tree file: ");
			treeFile=word.nextLine();
			root.data= buildTree(treeFile, root);

			System.out.print("Please enter the encrypted file: ");
			encryptedFile=word.nextLine();
			System.out.print("Please enter the output file: ");
			outputFile=word.nextLine();

			decryptMessage(root, encryptedFile, outputFile);

		}
	}
	
	public static char buildTree(String file, Node<Character> root) throws java.io.IOException
	{
		Scanner build = new Scanner(new File(file));
		char [] brokenUp = build.nextLine().toCharArray();
		
		
		root = recursion(build, brokenUp[0], brokenUp[1], root);
	
		return brokenUp[1];
		
	}

	public static void printTree(Node<Character> node){
		
		System.out.println(node.data);
		
		if(!(node.right == null || node.left == null)){
			printTree(node.right);
			printTree(node.left);
		}
	}
		
	public static Node<Character> recursion(Scanner s, char isleaf , char data, Node<Character> node)
	{

		if(isleaf=='L'){

			return new Node<Character>(data, null ,null);
		}
		else{
			char [] brokenUp = s.nextLine().toCharArray();

			node.left = recursion(s, brokenUp[0], brokenUp[1], new Node<Character>(brokenUp[1], null, null));

			brokenUp = s.nextLine().toCharArray();

			node.right = recursion(s, brokenUp[0], brokenUp[1], new Node<Character>(brokenUp[1], null, null));			

			node = new Node<Character>(data, node.right, node.left);
			
			return node;
		}
	}
	
	public static void encryptMessage(Node<Character> root, String message, String output) throws java.io.IOException
	{
		//while loop has next
		//find in tree and store path (1 or 0) to string concatonate 
		//print to file
		  
		 //  it just can return 1 or 0
		 // and add it to a string call it recursively until we r at the letter then return null 
		
		Scanner breakDown = new Scanner(new File(message));
		
			FileWriter fw = new FileWriter(output, true);
			PrintWriter out = new PrintWriter(fw);

		while(breakDown.hasNext())
		{
			char [] brokenUp= breakDown.nextLine().toCharArray();
			for(int i = 0 ; i<brokenUp.length; i++)
			{
				out.println(encryptRecursion(root,brokenUp[i]));
			}
		}
			out.close();
			fw.close();
		
	}
	
	public static String encryptRecursion(Node<Character> node, char val)
	{

		String thePattern ="";
        Stack pattern = new Stack();


        find(node, val, pattern);
		while(!pattern.empty())
		{
			thePattern = thePattern + pattern.pop();
		} 
		
		return thePattern;
	}
	
	
	public static boolean find(Node<Character> node, char val, Stack theStack)
	{

		if(node.data == val)
			return true;
		
		else
		{
			if(node.left != null) {
		       if(find(node.left, val, theStack))
		       {
		    	   theStack.push(0);
		    	   return true;
		       }
		    }
		    if(node.right != null) {
		        if(find(node.right, val, theStack))
		        {
		        	theStack.push(1);
		        	return true;
		        }	
		    }
		    if(node.left == null && node.right == null) {

		    }
		}
		return false;
	}
	
	
	public static void decryptMessage(Node<Character> root, String encrypted, String output) throws java.io.IOException
	{
		String decryptString= "";
		
		Scanner breakDown = new Scanner(new File(encrypted));
		
		while(breakDown.hasNext())
		{
			char [] brokenUp = breakDown.nextLine().toCharArray();
	
			int count = 0;
			
			Node<Character> holder = root;
		
	
			while(count<brokenUp.length)
			{
				int way = brokenUp[count]-48;
				holder = deRecursion(holder, way);
				count++;
			}
		
			decryptString = decryptString + holder.data;
		}

		FileWriter fw = new FileWriter(output, true);
		PrintWriter out = new PrintWriter(fw);
		
		out.println(decryptString);
		
		out.close();
		fw.close();
	}
	
	public static Node<Character> deRecursion(Node<Character> node, int whichWay)
	{
		if(whichWay == 1)
		{
			return node.right;
		}
		if (whichWay ==0) 
		{
			return node.left;
		}
		else
			return node;
	}
}