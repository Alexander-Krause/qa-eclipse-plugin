package qa.eclipse.plugin.bundles.checkstyle;

public final class StringUtils {

	/**
	 * The empty String {@code ""}.
	 * 
	 * @since 2.0
	 */
	public static final String EMPTY = "";

	private StringUtils() {
		// utility class
	}

	/**
	 * <p>
	 * Joins the elements of the provided array into a single String containing the
	 * provided list of elements.
	 * </p>
	 *
	 * <p>
	 * No delimiter is added before or after the list. Null objects or empty strings
	 * within the array are represented by empty strings.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.join(null, *)               = null
	 * StringUtils.join([], *)                 = ""
	 * StringUtils.join([null], *)             = ""
	 * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
	 * StringUtils.join([1, 2, 3], null) = "123"
	 * </pre>
	 *
	 * @param array
	 *            the array of values to join together, may be null
	 * @param separator
	 *            the separator character to use
	 * @return the joined String, {@code null} if null array input
	 * @since 3.2
	 */
	public static String join(int[] numbers, char separator) {
		return join(numbers, separator, 0, numbers.length);
	}

	/**
	 * <p>
	 * Joins the elements of the provided array into a single String containing the
	 * provided list of elements.
	 * </p>
	 *
	 * <p>
	 * No delimiter is added before or after the list. Null objects or empty strings
	 * within the array are represented by empty strings.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.join(null, *)               = null
	 * StringUtils.join([], *)                 = ""
	 * StringUtils.join([null], *)             = ""
	 * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
	 * StringUtils.join([1, 2, 3], null) = "123"
	 * </pre>
	 *
	 * @param array
	 *            the array of values to join together, may be null
	 * @param separator
	 *            the separator character to use
	 * @param startIndex
	 *            the first index to start joining from. It is an error to pass in
	 *            an end index past the end of the array
	 * @param endIndex
	 *            the index to stop joining from (exclusive). It is an error to pass
	 *            in an end index past the end of the array
	 * @return the joined String, {@code null} if null array input
	 * @since 3.2
	 */
	public static String join(final int[] array, final char separator, final int startIndex, final int endIndex) {
		if (array == null) {
			return null;
		}
		final int noOfItems = endIndex - startIndex;
		if (noOfItems <= 0) {
			return EMPTY;
		}
		final StringBuilder buf = new StringBuilder(noOfItems * 16);
		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			buf.append(array[i]);
		}
		return buf.toString();
	}

}