package pu.fmi.masters.openbanking.dto;

/**
 * This class defines a data transfer object used for deserializing JSON data.
 */
public class BalancesDto {
	
	private String href;
	
	/**
	 * No-arguments constructor.
	 */
	public BalancesDto() {}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

}
