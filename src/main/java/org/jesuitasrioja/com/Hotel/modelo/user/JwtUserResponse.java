package org.jesuitasrioja.com.Hotel.modelo.user;

import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JwtUserResponse extends UserEntity {
	private String token;

	// indicaremos otro nombre al builder
	@Builder(builderMethodName = "jwtUserResponseBuilder")
	// los parametros dependeran de lo que contenga el UserEntity
	public JwtUserResponse(String username, String email, Set<UserRole> roles, String token) {
		this.setUsername(username);
		this.setRoles(roles);
		this.token = token;
	}
}
