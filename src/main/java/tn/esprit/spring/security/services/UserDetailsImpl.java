package tn.esprit.spring.security.services;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tn.esprit.spring.entities.Utilisateur;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String username;

	private String email;

	@JsonIgnore
	private String password;

	private GrantedAuthority authority; // Store a single authority directly

	// Constructor to initialize with a single authority
	public UserDetailsImpl(Long id, String email, String password, GrantedAuthority authority) {
		this.id = id;
		this.email = email;
		this.username = email; // Assuming the username is the email
		this.password = password;
		this.authority = authority;
	}

	// Static method to build a UserDetailsImpl instance from a Utilisateur instance
	public static UserDetailsImpl build(Utilisateur user) {
		GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().name());

		return new UserDetailsImpl(
				user.getId(),
				user.getEmail(),
				user.getMotDePasse(),
				authority
		);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(authority); // Return a singleton collection containing the single authority
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}
}
