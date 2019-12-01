package carlos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StatusDTO {
	private String status;
	@JsonProperty("facts")
	private FactsStatusDTO factsStatus;

	public StatusDTO(String status, FactsStatusDTO factsStatus) {
		this.status = status;
		this.factsStatus = factsStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public FactsStatusDTO getFactsStatus() {
		return factsStatus;
	}

	public void setFactsStatus(FactsStatusDTO factsStatus) {
		this.factsStatus = factsStatus;
	}

}
