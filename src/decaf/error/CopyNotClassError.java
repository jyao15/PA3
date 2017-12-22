package decaf.error;

import decaf.Location;

/**
 * example：incompatible operands: int + bool<br>
 * PA2
 */
public class CopyNotClassError extends DecafError {

	private String right;

	public CopyNotClassError(Location location, String right) {
		super(location);
		this.right = right;
	}

	@Override
	protected String getErrMsg() {
			return "expected class type for copy expr but " + right + " given";
	}

}
