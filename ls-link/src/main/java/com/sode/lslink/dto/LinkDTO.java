package com.sode.lslink.dto;

public class LinkDTO {

	private String link;
	
	public LinkDTO() {}

	public LinkDTO(String link) {
		this.link = link;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public String toString() {
		return "LinkDTO [link=" + link + "]";
	}
	
}
