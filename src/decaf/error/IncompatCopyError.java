package decaf.error;

import decaf.Location;

/**
 * example：incompatible operands: int + bool<br>
 * PA2
 */
public class IncompatCopyError extends DecafError {

	private String left;

	private String right;

	public IncompatCopyError(Location location, String left,
			String right) {
		super(location);
		this.left = left;
		this.right = right;
	}

	@Override
	protected String getErrMsg() {
			return "For copy expr, the source " + right + " and the destination " + left + " are not same";
	}

}
