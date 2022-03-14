package org.jesuitasrioja.com.Hotel.modelo.user;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class GetUserDTO {
	private String id;
	private String username;
	private String password;
	private Set<String> roles;
	private String telefono;
	private String email;
}
