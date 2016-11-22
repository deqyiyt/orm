package com.hujz.framework.orm.bean;


public class QueryPropert {
	private String param;
	
	private Object value;
	
	private Object type;

	public QueryPropert(String param, Object value) {
		this(param, value, null);
	}

	public QueryPropert(String param, Object value, Object type) {
		super();
		this.param = param;
		this.value = value;
		this.type = type;
	}

	/**
	 * param的获取.
	 * @return String
	 */
	public String getParam() {
		return param;
	}

	/**
	 * 设定param的值.
	 * @param String
	 */
	public void setParam(String param) {
		this.param = param;
	}

	/**
	 * value的获取.
	 * @return Object
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * 设定value的值.
	 * @param Object
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * type的获取.
	 * @return Type
	 */
	public Object getType() {
		return type;
	}

	/**
	 * 设定type的值.
	 * @param Type
	 */
	public void setType(Object type) {
		this.type = type;
	}
}
