package com.mine.persistence.common;

/**
 * @author Anonymous
 * @sin Apr 14, 2016
 * @version 1.0 
 */
public class ObjectWithCondition {
	Object value;
	Condition condition = Condition.EXAC;
	
	
	public ObjectWithCondition() {
		
	}
	/**
	 * @param value {@link Object Object}
	 * @param condition {@link Condition}
	 */
	public ObjectWithCondition(Object value, Condition condition) {
		super();
		this.value = value;
		if(condition!=null)
			this.condition = condition;

	}
	/**
	 * @param value: {@link Object Object}
	 * 
	 */
	public ObjectWithCondition(Object value) {
		super();
		this.value = value;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public Condition getCondition() {
		return condition;
	}
	public void setCondition(Condition condition) {
		this.condition = condition;
	}
	
}
