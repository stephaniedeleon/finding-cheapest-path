
//Date: 			May 15, 2020
//Description:	
//				Interface of ListWithIterator extends ListInterface and Iterable. 
//

import java.util.Iterator;

public interface ListWithIteratorInterface<T> extends ListInterface<T>, Iterable<T> {
	
	public Iterator<T> getIterator();

}
