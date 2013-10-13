package org.example.patterns.creational;

import java.lang.reflect.Field;

/**
 * A simple, reusable implementation of the Factory Method GOF pattern.
 * 
 * This concrete implementation class to be created, is specified with a
 * run-time property, whose name is passed in the constructor. The creation
 * strategy is simple and tries to instantiate or retrieve instances by the two
 * following mechanisms:
 * <ul>
 * <li><code>INSTANCE</code> field of a Singleton class implemented as an enum.
 * <li>The public constructor with no arguments.</li>
 * </ul>
 * 
 * <p>
 * Example (assuming you have an interface or super class named
 * <code>Interface</code>, and an impl. class, <code>Impl</code>, on the class
 * path):
 * </p>
 * 
 * {@code
 *  SimpleFactory factory = new SimpleFactory(Impl.class.getName());
 * 	Interface result = factory.create();
 * }
 * <p>
 * You can also subclass the factory in another class to hide the  
 * configuration details from your clients.
 * 
 * @author Sven-Jørgen Karlsen <svenjok@gmail.com>
 * 
 */
public class SimpleFactory {

	private final String implClsProperty;

	/**
	 * Create a new factory.
	 * 
	 * @param implClsProperty
	 *            Name of a run-time property specifying the name of the
	 *            implementation class to create.
	 */
	public SimpleFactory(String implClsProperty) {
		this.implClsProperty = implClsProperty;
	}

	/**
	 * Create/retrieve an object instance.
	 * 
	 * The class to be instantiated or retrieved (singleton variation) can be
	 * specified by setting a system property of the name specified in the
	 * <code>implClsProperty</code> attribute to the desired class. If the
	 * property is not set, the factory will simply try to instantiate a class
	 * of the same name as the property name.
	 * 
	 * @return an instance of <code>T</code>.
	 */
	public final <T> T create() {
		Class<?> cls;
		String implClsName = System.getProperty(implClsProperty,
				implClsProperty);
		try {
			cls = (Class<?>) Class.forName(implClsName);
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(
					String.format(
							"Can't load class object for impl. class: %s",
							implClsName), e);
		}
		try {
			Field field = findField(cls, "INSTANCE");
			Object resultObject;
			if (field != null) {
				resultObject = field.get(cls);
			} else {
				resultObject = cls.newInstance();
			}
			@SuppressWarnings("unchecked")
			T result = (T) resultObject;
			return result;
		} catch (Exception e) {
			assert e instanceof InstantiationException
					|| e instanceof IllegalAccessException;
			throw new IllegalArgumentException(String.format(
					"Cant instantiate object of class: %s", implClsName), e);
		}
	}

	/**
	 * Find a declared field by name in a class.
	 * 
	 * @param cls
	 *            class to search.
	 * @param name
	 *            Name of the field to find.
	 * @return the field if found, otherwise <code>null</code>.
	 */
	private static final Field findField(Class<?> cls, String name) {
		for (Field field : cls.getDeclaredFields()) {
			if (field.getName().equals(name)) {
				return field;
			}
		}
		return null;
	}

}
