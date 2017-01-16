package exceptions;

/**
 * This is exception class that uses for create new exceptions
 * @author  Andrey Orlov
 * @version 1.0
 * 
 */
public class ProjectException extends Exception {

	private String message;

	/**
	 * Create exception object
	 * 
	 * @param massage - Exception description
	 */
	public ProjectException(String massage) {
		this.message = massage;
	}

	/**
	 * (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return message;
	}
}
