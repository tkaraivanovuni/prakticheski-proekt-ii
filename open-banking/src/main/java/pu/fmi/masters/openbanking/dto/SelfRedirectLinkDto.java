package pu.fmi.masters.openbanking.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * This class defines a data transfer object used for deserializing JSON data.
 */
@JsonDeserialize
public class SelfRedirectLinkDto {
	
	private String href;
	
	/**
	 * No-args constructor.
	 */
	public SelfRedirectLinkDto() {}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

}
