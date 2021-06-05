package pu.fmi.masters.openbanking.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * This class defines a data transfer object used for deserializing JSON data.
 */
@JsonDeserialize
public class PaymentResponseDto {
	
	private String transactionStatus;
	private int paymentId;
	private PaymentLinkDto _links;
	
	/**
	 * No-args constructor.
	 */
	public PaymentResponseDto() {}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public PaymentLinkDto get_links() {
		return _links;
	}

	public void set_links(PaymentLinkDto _links) {
		this._links = _links;
	}
	
}
