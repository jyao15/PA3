package decaf.error;

import decaf.Location;

/**
 * example：cannot access field 'homework' from 'Others'<br>
 * 指通过类名来访问类成员，Others是类名<br>
 * example：cannot access field 'homework' from 'int[]'<br>
 * 指通过非类成员变量来访问类成员，int[]是该变量的类型名字<br>
 * PA2
 */
public class NotSuperParentError extends DecafError {

	private String name;

	public NotSuperParentError(Location location, String name) {
		super(location);
		this.name = name;
	}

	@Override
	protected String getErrMsg() {
		return "no parent class exist for " + name;
	}

}
