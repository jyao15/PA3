package decaf.error;

import decaf.Location;

/**
 * example：function 'gotoMars' expects 1 argument(s) but 3 given<br>
 * PA2
 */
public class BadDoLabelError extends DecafError {

	private String method;

	public BadDoLabelError(Location location, String method) {
		super(location);
		this.method = method;
	}

	@Override
	protected String getErrMsg() {
		return "The condition of Do Stmt requestd type bool but " + method + " given";
	}
}
