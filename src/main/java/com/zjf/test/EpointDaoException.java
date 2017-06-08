package com.zjf.test;

public class EpointDaoException extends RuntimeException {
	
	private static final long serialVersionUID = 342820722361408621L;
	
	public EpointDaoException(String message) {
		super(message);
	}
	
	public EpointDaoException(Throwable cause) {
		super(cause);
	}
	
	public EpointDaoException(String message, Throwable cause) {
		super(message, cause);
	}
}
