/**
 * A linked list of Tune.
 * There are operations to add tune in alphabetical order
 * ordering them in like order after liked it,and getting the 
 * length of list .
 * @author Mingmin
 *
 */
public class TuneList {
Node list;
Node list2=new Node();
int length;
/**
 * Calculate the length of list.
 * @return get the value of length.
 */
private int length(){
	length = 0;
	Node n = list;
	//if list is not null the move the tail to top and increase length.
	while(n!=null){
		length++;
		n=n.nextAlph;
	}
	return length;
}
/**
 * Add the tune to the list.
 * If the list is empty, then it will be added
 * to list directly. If not, it will compare with
 * the elements in the list and order by alphabetic order.
 * @param a it reference to the art in Tune.
 * @param t it reference to the tit in Tune.
 */
public void addTune(String a,String t){
	Tune tune = new Tune(a,t);
	//check whether the list is null, if list is null the add the tune as the first element of list.
	if (list == null){
		list = new Node(tune);
		return;
	}else{
		//if the list is not null the compare with the artist and title of the tune that existed.
		int compare = (a+t).compareTo(list.head().art+list.head().tit);
		Node temp = list;
		if(compare>0){
			//if the tune is larger than the first element in the list go to the next.
			while(compare > 0){
				if(temp.nextAlph!=null){
					compare = (a+t).compareTo(temp.nextAlph.head().art+temp.nextAlph.head().tit);
		}else{
		//insert the tune into middle.
		Node node1 = new Node(tune);
		temp.nextAlph = node1;
		node1.prevAlph = temp;
		return;
			}
			temp = temp.nextAlph;
		}
		//if it is the same then do nothing.
		if(compare == 0){
			return;
		}
		//insert the tune into back.
		Node node1 = new Node(tune);
		temp.prevAlph.nextAlph = node1;
		node1.prevAlph = temp.prevAlph;
		temp.prevAlph = node1;
		node1.nextAlph = temp;
		}else if(compare == 0){
			return;
			//if the tune is smaller than the first element then insert it into top.
		}else if(compare < 0){
			Node node2 = new Node(tune);
			node2.nextAlph = temp;
			temp.prevAlph = node2;
			list = node2;
		}
	}
}
/**
 * It will increase the number of like of tune.
 * If the given tune is no existing in the list,
 * and then it will have no effect on it.
 * @param a it reference to the art in Tune.
 * @param t it reference to the tit in Tune.
 */
public void likeTune(String a,String t){
	Tune tune = new Tune(a,t);
		Node temp = list;
		//compare the tune with the element in the list.
		int result = (a+t).compareTo(list.head().art+list.head().tit);
		//if there is no the element matches the tune, and it will go next.
		if (result!=0){
			while (result!=0){
				if(temp==null){
					break;
				}
				//go through the list to find the matched one.
				else if(temp!=null){
				result = (a+t).compareTo(temp.head().art+temp.head().tit);
				if(result == 0){
					temp.head().like++;
					break;
				}
				temp = temp.nextAlph;
				}		
			}	
		}else {
			//if it match the first one it will increase the first element's like.
			list.head().like++;
		}	
}
/**
 * This method is used to display the result
 * of list after ordering by alphabetical order.
 * @return get a result of list in String type.
 */
public String listAlphabetically() {
	String result1 = "";
	Node temp = list;
	for(int i=0;i<length();i++){
		String x = temp.head().toString();
		temp = temp.nextAlph;
		result1 = result1 + x;
	}
	return result1;	
}
/**
 * reset the pointer in order to keep the
 * pointer pointing the first node.
 */
private void resetpointer(){
	if(list!=null){
	while(list.prevAlph!=null){
		list=list.prevAlph;
	}
	}
	if(list2!=null){
		list2= new Node();
	}
}
/**
 * Use this method to list the list by the
 * number of like and display the result.
 * @return get the result of list with like
 * order in String type.
 */
public String listByLikes(){
	resetpointer();
	Node node2=list2;
	Node node1=list;
	int max=0;
	//find the max like.
	while(node1!=null){
		if(node1.head().like>=max){
			max=node1.head().like;	
		}
		node1=node1.nextAlph;
	}
	node1=list;
	//list the elements according to the likes.
	for(int i=max;i>=0;i--){
		while(node1!=null){
			if(node1.head().like==i){
				if(node2==null)
					node2=node1;
				else{
					node2.nextPop=node1;
					node2=node2.nextPop;
				}
				}
			node1=node1.nextAlph;
		}
		node1=list;
	}
	//display the result.
	Node node3=list2;
	node3=node3.nextPop;
	String result="";
	if(result==""){
		result=node3.head().toString();
		node3=node3.nextPop;
	}
		while(node3!=null){
			result=result+node3.head().toString();
			node3=node3.nextPop;
		}
	return result;
	}
}
/**
 * The class create a Tune which has artist, title and like attributes.
 * @author Mingmin
 *
 */
class Tune {
	  /**
     *  artist of tune.
     */
final String art;
	/**
	 * title of tune.
	 */
final String tit;
	/**
	 * the number of like of tune.
	 */
int like = 0;
/**
 * A constructor with artist and title.
 * @param a reference to art as artist.
 * @param t reference to tit as title.
 */
Tune(String a,String t){
	art = a;
	tit = t;
}
/**
 * Display the result.
 */
public String toString(){
	String result;
	result= art + "\n" + tit + "\n" + like + "\n" ;
	return result;
}
}
/**
 *  Three Node fields indicate two pointers in alphabetic order
 *  list, and one in like order list.
 */
class Node {
	  /**
     *  The tune at the current node.
     */
    Tune theTune;

    /**
     *  Next tune in alphabetic order.
     */
    Node nextAlph;
    /**
     *  Previous tune in alphabetic order.
     */
    Node prevAlph;

    /**
     *  Next tune in popularity order.
     */
   Node nextPop;
   /**
    * A default constructor. 
    */
Node(){
	
}
   /**
    * A constructor which is a pointer pointed the first.
    * @param tune reference to theTune.
    */

Node (Tune tune){
	theTune = tune;
	nextAlph = null;
	nextPop = null;
}
	/**
	 * Get the head value of a node.
	 * @return return theTune as the head of node.
	 */
Tune head(){
	return theTune;
}
}
