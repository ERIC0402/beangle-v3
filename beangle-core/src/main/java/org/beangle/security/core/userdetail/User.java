/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.userdetail;

import java.util.Collection;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.beangle.security.core.GrantedAuthority;

public class User implements UserDetail {

	private static final long serialVersionUID = 1L;
	private String password;
	private String username;
	private Collection<GrantedAuthority> authorities;
	private boolean accountExpired;
	private boolean accountLocked;
	private boolean credentialsExpired;
	private boolean enabled;

	public User() {
		super();
	}

	public User(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		this(username, password, true, false, false, false, authorities);
	}

	/**
	 * Construct the <code>User</code> with the details required by
	 * {@link org.beangle.security.auth.dao.providers.dao.DaoAuthenticationProvider} .
	 * 
	 * @param username
	 *            the username presented to the <code>DaoAuthenticationProvider</code>
	 * @param password
	 *            the password that should be presented to the
	 *            <code>DaoAuthenticationProvider</code>
	 * @param enabled
	 *            set to <code>true</code> if the user is enabled
	 * @param accountExpired
	 *            set to <code>true</code> if the account has expired
	 * @param credentialsExpired
	 *            set to <code>true</code> if the credentials have expired
	 * @param accountLocked
	 *            set to <code>true</code> if the account is locked
	 * @param authorities
	 *            the authorities that should be granted to the caller if they
	 *            presented the correct username and password and the user is
	 *            enabled
	 * @throws IllegalArgumentException
	 *             if a <code>null</code> value was passed either as a parameter
	 *             or as an element in the <code>GrantedAuthority[]</code> array
	 */
	@SuppressWarnings("unchecked")
	public User(String username, String password, boolean enabled, boolean accountExpired,
			boolean credentialsExpired, boolean accountLocked,
			Collection<? extends GrantedAuthority> authorities) throws IllegalArgumentException {
		if (((username == null) || "".equals(username)) || (password == null)) { throw new IllegalArgumentException(
				"Cannot pass null or empty values to constructor"); }

		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.accountExpired = accountExpired;
		this.credentialsExpired = credentialsExpired;
		this.accountLocked = accountLocked;
		this.authorities = (Collection<GrantedAuthority>) authorities;
	}

	public boolean equals(Object rhs) {
		if (!(rhs instanceof User) || (rhs == null)) { return false; }
		User user = (User) rhs;
		return new EqualsBuilder().append(getUsername(), user.getUsername())
				.append(getPassword(), user.getPassword()).append(isAccountExpired(), user.isAccountLocked())
				.append(isAccountLocked(), user.isAccountLocked())
				.append(isCredentialsExpired(), user.isCredentialsExpired())
				.append(getAuthorities(), user.getAuthorities()).append(isEnabled(), user.isEnabled())
				.isEquals();
	}

	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getAuthorities()).append(getPassword()).append(getUsername())
				.append(isAccountExpired()).append(isAccountLocked()).append(isCredentialsExpired())
				.append(isEnabled()).toHashCode();
	}

	public boolean isAccountExpired() {
		return accountExpired;
	}

	public boolean isAccountLocked() {
		return this.accountLocked;
	}

	public boolean isCredentialsExpired() {
		return credentialsExpired;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString()).append(": ");
		sb.append("Username: ").append(this.username).append("; ");
		sb.append("Password: [PROTECTED]; ");
		sb.append("Enabled: ").append(this.enabled).append("; ");
		sb.append("AccountExpired: ").append(this.accountExpired).append("; ");
		sb.append("credentialsExpired: ").append(this.credentialsExpired).append("; ");
		sb.append("AccountLocked: ").append(this.accountLocked).append("; ");

		if (!getAuthorities().isEmpty()) {
			sb.append("Granted Authorities: ");
			for (GrantedAuthority authority : getAuthorities()) {
				sb.append(authority.toString()).append(", ");
			}
			sb.deleteCharAt(sb.length() - 1);
		} else {
			sb.append("Not granted any authorities");
		}
		return sb.toString();
	}
}
