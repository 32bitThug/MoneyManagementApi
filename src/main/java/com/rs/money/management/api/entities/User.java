package com.rs.money.management.api.entities;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.rs.money.management.api.enums.Roles;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import lombok.Builder;

// REMOVE @Builder from class level
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User implements UserDetails {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;

	private String fullName;

	@Email
	private String email;

	private String password;

	private Date createdDate;

	@OneToMany(targetEntity = Credits.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Credits> credits;

	@OneToMany(targetEntity = Debits.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Debits> debits;

	@Enumerated(EnumType.STRING)
	private Roles roles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(roles.name()));
	}

	@Override
	public String getUsername() {
		return email;
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
	public String getPassword() {
		return password;
	}

	// ✅ Custom Builder Method
	@Builder
	public static User createUser(String fullName, String email, String password, Roles roles) {
		User user = new User();
		user.fullName = fullName;
		user.email = email;
		user.password = password;
		user.roles = roles;
		user.createdDate = new Date();
		return user;
	}

}
