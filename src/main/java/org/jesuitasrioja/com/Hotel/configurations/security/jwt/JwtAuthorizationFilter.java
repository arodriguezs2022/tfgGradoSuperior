package org.jesuitasrioja.com.Hotel.configurations.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jesuitasrioja.com.Hotel.modelo.user.UserEntity;
import org.jesuitasrioja.com.Hotel.persistencia.services.CustomUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider tokenProvider = new JwtTokenProvider();
	private final CustomUserDetailsService userDetailsService = new CustomUserDetailsService();

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			String token = getJwtFromRequest(request);

			if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
				String userId = tokenProvider.getUserIdFromJWT(token);

				UserEntity user = (UserEntity) userDetailsService.loadUserById(userId);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,user.getRoles(), user.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);

			}
		} catch (Exception ex) {
			System.out.println(ex);
		}

		filterChain.doFilter(request, response);

	}

	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader(JwtTokenProvider.TOKEN_HEADER);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtTokenProvider.TOKEN_PREFIX)) {
			return bearerToken.substring(JwtTokenProvider.TOKEN_PREFIX.length(), bearerToken.length());
		}
		return null;
	}
}