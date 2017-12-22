package decaf.error;

import decaf.Location;

/**
 * example：incompatible operands: int + bool<br>
 * PA2
 */
public class DifferentConstantCaseError extends DecafError {

	public DifferentConstantCaseError(Location location) {
		super(location);
	}

	@Override
	protected String getErrMsg() {
			return "condition is not unique";
	}

}
