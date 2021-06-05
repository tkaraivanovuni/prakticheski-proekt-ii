package pu.fmi.masters.openbanking.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * This class defines a data transfer object used for deserializing JSON data
 * representing bank accounts.
 */
@JsonDeserialize
public class BankAccountDto {
	
	private String resourceId;
	private String iban;
	private String currency;
	private String product;
	private String name;
	private LinksDto _links;
	
	/**
	 * No-arguments constructor.
	 */
	public BankAccountDto() {}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LinksDto get_links() {
		return _links;
	}

	public void set_links(LinksDto _links) {
		this._links = _links;
	}
	
}
