package decaf.error;

import decaf.Location;

/**
 * example：incompatible operands: int + bool<br>
 * PA2
 */
public class DifferentExprCaseError extends DecafError {

	private String left;

	private String right;

	public DifferentExprCaseError(Location location, String left,
			String right) {
		super(location);
		this.left = left;
		this.right = right;
	}

	@Override
	protected String getErrMsg() {
			return "type: " + right + " is different with other expr's type " + left;
	}

}
