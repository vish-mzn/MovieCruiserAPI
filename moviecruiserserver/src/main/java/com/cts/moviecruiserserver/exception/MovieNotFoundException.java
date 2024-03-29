package com.cts.moviecruiserserver.exception;

@SuppressWarnings("serial")
public class MovieNotFoundException extends Exception{
	
	public String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MovieNotFoundException(String message) {
		super();
		this.message = message;
	}

	@Override
	public String toString() {
		return "MovieNotFoundException [message=" + message + "]";
	}

}
