package com.sode.lsuser.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class Link implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String url;
	private String redirect;
	
	private Instant expiration;
	
	private User owner;
	
	private String accesssKey;
	
	public Link() {}

	public Link(Long id, String url, String redirect, Instant expiration, User owner) {
		this.id = id;
		this.url = url;
		this.redirect = redirect;
		this.expiration = expiration;
		this.accesssKey = "";
		this.owner = owner;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	public Instant getExpiration() {
		return expiration;
	}

	public void setExpiration(Instant expiration) {
		this.expiration = expiration;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Link other = (Link) obj;
		return Objects.equals(id, other.id);
	}
}
