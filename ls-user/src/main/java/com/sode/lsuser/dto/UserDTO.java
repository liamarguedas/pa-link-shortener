package com.sode.lsuser.dto;

import com.sode.lsuser.entity.User;

public class UserDTO {
	
	private Long id;
	private String name;
	private String username;
	private String email;
	private String password;
	
	public UserDTO() {}
	
	   public UserDTO(Long id, String name, String username, String email) {
	        this.id = id;
	        this.name = name;
	        this.username = username;
	        this.email = email;
	    }

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getUsername() {
	        return username;
	    }

	    public void setUsername(String username) {
	        this.username = username;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }
	
	    public static UserDTO fromEntity(User user) {

	        return new UserDTO(
	            user.getId(),
	            user.getName(),
	            user.getUsername(),
	            user.getEmail()
	        );
	    }

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}


}
