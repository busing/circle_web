package com.xwtec.xwserver.util;

import java.util.Collection;
import java.util.Map;

/**
 * assertion for some types <br>
 * <p>
 * Copyright: Copyright (c) 2013-11-7 上午10:50:39
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author liu_tao@xwtec.cn
 * @version 1.0.0
 */
public class Assert {

	/**
	 * Assert a boolean expression with a specified message, throwing <code>IllegalArgumentException</code>
	 * @param expression
	 * @param message
	 */
	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * Assert a boolean expression with a default message, throwing <code>IllegalArgumentException</code>
	 * @param expression
	 */
	public static void isTrue(boolean expression) {
		isTrue(expression, "[Assertion failed] - this expression must be true");
	}

	/**
	 * Assert an object is null with a specified message, throwing <code>IllegalArgumentException</code>
	 * @param object
	 * @param message
	 */
	public static void isNull(Object object, String message) {
		if (object != null) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * Assert an object is null with a default message, throwing <code>IllegalArgumentException</code>
	 * @param object
	 */
	public static void isNull(Object object) {
		isNull(object, "[Assertion failed] - the object argument must be null");
	}

	/**
	 * Assert an object is not null with a specified message, throwing <code>IllegalArgumentException</code>
	 * @param object
	 * @param message
	 */
	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * Assert an object is not null with a default message, throwing <code>IllegalArgumentException</code>
	 * @param object
	 */
	public static void notNull(Object object) {
		notNull(object, "[Assertion failed] - this argument is required; it must not be null");
	}
	
	/**
	 * Assert a string is empty with a specified message, throwing <code>IllegalArgumentException</code>
	 * @param string
	 * @param message
	 */
	public static void isEmpty(String string, String message) {
		if(!(string == null || string.trim().equals("") || string.trim().equals("null"))){
			throw new IllegalArgumentException(message);
		}
	}
	
	/**
	 * Assert a string is empty with a default message, throwing <code>IllegalArgumentException</code>
	 * @param string
	 */
	public static void isEmpty(String string) {
		isEmpty(string, "[Assertion failed] - the string argument must be empty [e.g. null、\"\"、\"null\"]");
	}
	
	/**
	 * Assert a string is not empty with a specified message, throwing <code>IllegalArgumentException</code>
	 * @param string
	 * @param message
	 */
	public static void notEmpty(String string, String message) {
		if(string == null || string.trim().equals("") || string.trim().equals("null")){
			throw new IllegalArgumentException(message);
		}
	}
	
	/**
	 * Assert a string is not empty with a default message, throwing <code>IllegalArgumentException</code>
	 * @param string
	 */
	public static void notEmpty(String string) {
		notEmpty(string, "[Assertion failed] - the string argument must be not empty [e.g. null、\"\"、\"null\"]");
	}
	
	/**
	 * Assert a collection is not empty(null or empty collection) with a specified message, throwing <code>IllegalArgumentException</code>
	 * @param collection
	 * @param message
	 */
	public static void notEmpty(Collection<?> collection, String message) {
		if(collection == null || collection.isEmpty()){
			throw new IllegalArgumentException(message);
		}
	}
	
	/**
	 * Assert a collection is not empty(null or empty collection) with a default message, throwing <code>IllegalArgumentException</code>
	 * @param collection
	 */
	public static void notEmpty(Collection<?> collection) {
		if(collection == null || collection.isEmpty()){
			throw new IllegalArgumentException("[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
		}
	}
	
	/**
	 * Assert a array is not empty(null or empty array) with a specified message, throwing <code>IllegalArgumentException</code>
	 * @param collection
	 * @param message
	 */
	public static void notEmpty(Object[] array, String message) {
		if(array == null || array.length == 0){
			throw new IllegalArgumentException(message);
		}
	}
	
	/**
	 * Assert a array is not empty(null or empty array) with a specified message, throwing <code>IllegalArgumentException</code>
	 * @param collection
	 */
	public static void notEmpty(Object[] array) {
		notEmpty(array, "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
	}
	
	/**
	 * Assert a map is not empty(null or empty array) with a specified message, throwing <code>IllegalArgumentException</code>
	 * @param collection
	 * @param message
	 */
	public static void notEmpty(Map<?, ?> map, String message) {
		if(map == null || map.isEmpty()){
			throw new IllegalArgumentException(message);
		}
	}
	
	/**
	 * Assert a map is not empty(null or empty array) with a default message, throwing <code>IllegalArgumentException</code>
	 * @param collection
	 */
	public static void notEmpty(Map<?, ?> map) {
		notEmpty(map, "[Assertion failed] - this map must not be empty; it must contain at least one entry");
	}
	
	/**
	 * Assert a array does not contains null element with a specified message, throwing <code>IllegalArgumentException</code>
	 * @param array
	 * @param message
	 */
	public static void noNullElements(Object[] array, String message) {
		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				if (array[i] == null) {
					throw new IllegalArgumentException(message);
				}
			}
		}
	}
	
	/**
	 * Assert a array does not contains null element with a default message, throwing <code>IllegalArgumentException</code>
	 * @param array
	 */
	public static void noNullElements(Object[] array) {
		noNullElements(array, "[Assertion failed] - this array must not contain any null elements");
	}
	
	/**
	 * Assert a array does not contains null element with a specified message, throwing <code>IllegalArgumentException</code>
	 * @param array
	 * @param message
	 */
	public static void noNullElements(Collection<?> collection, String message) {
		if (collection != null) {
			for (Object object : collection) {
				if (object == null) {
					throw new IllegalArgumentException(message);
				}
			}
		}
	}
	
	/**
	 * Assert a array does not contains null element with a default message, throwing <code>IllegalArgumentException</code>
	 * @param array
	 */
	public static void noNullElements(Collection<?> collection) {
		noNullElements(collection, "[Assertion failed] - this array must not contain any null elements");
	}
	
	/**
	 * Assert that the provided object is an instance of the provided class.
	 * @param clazz
	 * @param obj
	 */
	public static void isInstanceOf(Class<?> clazz, Object obj) {
		isInstanceOf(clazz, obj, "");
	}

	/**
	 * Assert that the provided object is an instance of the provided class.
	 * @param type
	 * @param obj
	 * @param message
	 */
	public static void isInstanceOf(Class<?> type, Object obj, String message) {
		notNull(type, "Type to check against must not be null");
		if (!type.isInstance(obj)) {
			throw new IllegalArgumentException(message +
					"Object of class [" + (obj != null ? obj.getClass().getName() : "null") +
					"] must be an instance of " + type);
		}
	}
}
