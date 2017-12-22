package decaf.error;

import decaf.Location;

/**
 * example：cannot access field 'homework' from 'Others'<br>
 * 指通过类名来访问类成员，Others是类名<br>
 * example：cannot access field 'homework' from 'int[]'<br>
 * 指通过非类成员变量来访问类成员，int[]是该变量的类型名字<br>
 * PA2
 */
public class NotSuperFieldError extends DecafError {

	public NotSuperFieldError(Location location) {
		super(location);
	}

	@Override
	protected String getErrMsg() {
		return "super.member_var is not supported";
	}

}
