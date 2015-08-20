package miscellaneous.utils.collection.richIterator;

class ArrayRichIterator<T> extends RichIterator<T> {
	private T[] arr;
	private int i = 0;

	public ArrayRichIterator(T[] arr) { this.arr = arr; }
	
	@Override
	protected boolean hasNextInternal() throws Exception { return i < arr.length; }

	@Override
	protected T nextInternal() throws Exception { return arr[i++]; }
	
	@Override
	protected void closeInternal() { arr = null; }
}
