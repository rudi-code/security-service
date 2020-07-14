package silupa.co.id.securityservice.model;



import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "USERS")
public class User {
	
	@Id
	@Column(name = "USER_ID")
	@GenericGenerator(name = "USER_ID", strategy = "silupa.co.id.securityservice.model.UserIdGenerator")
	@GeneratedValue(generator = "USER_ID")
	@Size(max = 6)
	private String userId;
	
	@Column(name="USERNAME", unique=true, nullable=false)
	@Size(max=25)
	private String username;
	
	@Column(name="PASSWORD")
	@NotBlank
	@Size(min = 6, max = 100)
	private String password;
	
	@Column(name="FULLNAME", nullable = false)
	@Size(max=30)
	private String fullname;
	
	@Column(name="ALAMAT", nullable = false)
	@Size(max=50)
	private String alamat;
	
	@Column(name="NO_TELEPON", nullable = false)
	@Size(max=15)
	private String noTelepon;
	
	@Column(name="SMARTPHONE")
	@Size(max=15)
	private String smartphone;
	
	@Column(name="EMAIL", unique=true, nullable=false)
	@Size(max=50)
	private String email;
	
	@Column(name="ROLE_ID")
	private int roleId;
	
	@Column(name="IS_ACTIVE")
	@Size(max=1)
	private String isActive;
	
	@DateTimeFormat(pattern = "YYYY-MM-dd")
	@Column(name="CREATED_DATE")
	private Date createdDate;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@DateTimeFormat(pattern = "YYYY-MM-dd")
	@Column(name="LAST_MODIFIED")
	private Date lastModified;
	
	@Column(name="LAST_MODIFIED_BY")
	private String lastModifiedBy;

	
	public String getAlamat() {
		return alamat;
	}

	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}

	public String getNoTelepon() {
		return noTelepon;
	}

	public void setNoTelepon(String noTelepon) {
		this.noTelepon = noTelepon;
	}

	public String getSmartphone() {
		return smartphone;
	}

	public void setSmartphone(String smartphone) {
		this.smartphone = smartphone;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String isActive() {
		return isActive;
	}

	public void setActive(@Size(max = 1) String isActive) {
		this.isActive = isActive;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", password=" + password + ", fullname=" + fullname
				+ ", alamat=" + alamat + ", noTelepon=" + noTelepon + ", smartphone=" + smartphone + ", email=" + email
				+ ", roleId=" + roleId + ", isActive=" + isActive + ", createdDate=" + createdDate + ", createdBy="
				+ createdBy + ", lastModified=" + lastModified + ", lastModifiedBy=" + lastModifiedBy + "]";
	}

	
	
}
