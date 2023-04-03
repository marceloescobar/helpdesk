package com.mescobar.helpdesk.exception;

import java.io.Serializable;

public class StandardError implements Serializable {

	private static final long serialVersionUID = 1L;
	private String error;
	private String message;
	private Integer status;
	private String path;
	private Long timestamp;

	public StandardError() {
		super();
	}

	public StandardError(String error, String message, Integer status, String path, Long timestamp) {
		super();
		this.error = error;
		this.message = message;
		this.status = status;
		this.path = path;
		this.timestamp = timestamp;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

}
