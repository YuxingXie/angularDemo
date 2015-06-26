package com.dabast.common.base;

public abstract class  BaseEntity implements java.io.Serializable,Cloneable {  //NOSONAR

	private static final long serialVersionUID = -7200095849148417467L;

	protected static final String DATE_FORMAT = "yyyy-MM-dd";
	
	protected static final String TIME_FORMAT = "HH:mm:ss";
	
	protected static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	protected static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
	
    protected static final String COMPANT_TIMESTAMP_MM_FORMAT = "yy-MM-dd HH:mm";

    public Object clone() {//NOSONAR
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);//NOSONAR
        }
    }

}
