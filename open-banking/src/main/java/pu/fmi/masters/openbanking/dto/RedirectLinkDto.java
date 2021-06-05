package pu.fmi.masters.openbanking.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * This class defines a data transfer object used for deserializing JSON data.
 */
@JsonDeserialize
public class RedirectLinkDto {
	
	private String href;
	
	/**
	 * No-args constructor.
	 */
	public RedirectLinkDto() {}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}
	
}
