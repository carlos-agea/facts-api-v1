package carlos.consumer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import carlos.FactDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Fact {
	private String id;
	private String text;
	private String language;
	private String permalink;

	public Fact() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getPermalink() {
		return permalink;
	}

	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}

	public FactDTO asDTO() {
		return new FactDTO(this.id, this.text, this.permalink, this.language);
	}

}
