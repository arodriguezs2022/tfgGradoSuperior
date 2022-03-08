package org.jesuitasrioja.com.Hotel.modelo.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
	
	private String username;
	private String password;

}
