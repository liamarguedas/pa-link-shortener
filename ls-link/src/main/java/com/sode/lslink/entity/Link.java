package com.sode.lslink.entity;

import com.sode.lslink.utils.Linker;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;


@Entity
@Table(name="tb_links")
public class Link implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String redirect;
	private Instant expiration;

	private String owner;
	
	private String accessKey;
	
	public Link() {}

	public Link(Long id, String redirect, Instant expiration, String owner) {
		this.id = id;
		this.redirect = redirect;
		this.expiration = expiration;
		this.accessKey = generateAccessKey(); 
		this.owner = owner;
	}

	private String generateAccessKey() {
		
		if(redirect != null) {
			return Linker.generateAccessKey();
		}
		return null;
	}
	
	
	public Long getId() {
		return id;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getAccesssKey() {
		return accessKey;
	}
}
