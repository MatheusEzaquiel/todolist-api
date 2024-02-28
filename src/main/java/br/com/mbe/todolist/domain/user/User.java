package br.com.mbe.todolist.domain.user;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name="User")
@Table(name="users")
public class User implements UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name="id")
	private UUID id;
	
	@Column(name="username", unique = true)
	private String username;
	@Column(name="password")
	private String password;
	@Column(name="email")
	private String email;
	
	@Column(name="enabled")
	private boolean enabled;
	
	@Column(name="created_at")
	private LocalDateTime createdAt;
	
	@Column(name="updated_at")
	private LocalDateTime updatedAt;
	
	@Column(name="role")
	private UserRole role;

	public User() {
	}
	
	public User(UUID id, String username, String password, String email, LocalDateTime createdAt,
			LocalDateTime updatedAt, boolean enabled) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.enabled = enabled;
	}
	
	public User(UUID id, String username, String password, String email, LocalDateTime createdAt, UserRole role) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.createdAt = createdAt;
		this.enabled = true;
		this.role = role;
	}
	
	
	public User(UUID id, String username, String password, String email, LocalDateTime createdAt,
			LocalDateTime updatedAt, boolean enabled, UserRole role) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.enabled = enabled;
		this.role = role;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		if(this.role.equals(UserRole.ADMIN)) {
			return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
		} else {
			return List.of(new SimpleGrantedAuthority("ROLE_USER"));
		}
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
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", enabled=" + enabled + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", role=" + role
				+ "]";
	}
	
	
	

}
