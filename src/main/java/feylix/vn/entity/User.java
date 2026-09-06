package feylix.vn.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "role")
	private int role; // Quy ước: 1 = Admin, 0 = User thường (hoặc String role = "ADMIN")

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String password;

	private String fullname;
	private String phone;
	private String images;

	@Column(unique = true)
	private String email;

	@Column(name = "otp_code")
	private String otpCode;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "otp_expiry")
	private java.util.Date otpExpiry;

	// 0 = chua kich hoat (cho xac thuc OTP), 1 = da kich hoat
	@Column(name = "active")
	private int active;

	public User() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOtpCode() {
		return otpCode;
	}

	public void setOtpCode(String otpCode) {
		this.otpCode = otpCode;
	}

	public java.util.Date getOtpExpiry() {
		return otpExpiry;
	}

	public void setOtpExpiry(java.util.Date otpExpiry) {
		this.otpExpiry = otpExpiry;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	// Getters và Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}
}