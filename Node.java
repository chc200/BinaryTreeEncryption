public class Node<T>{
	
	T data;
	Node<T> right;
	Node<T> left;

	public Node(T d, Node<T> r, Node<T> l){
		data = d;
		right = r;
		left = l;
	}

	public int countNodes(Node<T> root) 
	{
	   if ( root == null )
	      return 0;
	   else 
	   	{
	      int count = 1;
	      count += countNodes(root.left);
	      count += countNodes(root.right);
	      return count;
  		}
	} 
}