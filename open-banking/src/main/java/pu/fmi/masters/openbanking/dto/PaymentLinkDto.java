package pu.fmi.masters.openbanking.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * This class defines a data transfer object used for deserializing JSON data.
 */
@JsonDeserialize
public class PaymentLinkDto {
	
	private RedirectLinkDto scaRedirect;
	private SelfRedirectLinkDto self;
	
	/**
	 * No-args constructor.
	 */
	public PaymentLinkDto() {}

	public RedirectLinkDto getScaRedirect() {
		return scaRedirect;
	}

	public void setScaRedirect(RedirectLinkDto scaRedirect) {
		this.scaRedirect = scaRedirect;
	}

	public SelfRedirectLinkDto getSelf() {
		return self;
	}

	public void setSelf(SelfRedirectLinkDto self) {
		this.self = self;
	}
	
}
