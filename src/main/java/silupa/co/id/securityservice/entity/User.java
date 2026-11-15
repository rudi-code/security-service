package silupa.co.id.securityservice.entity;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(nullable = false, updatable = false, length = 36)
	@Setter(AccessLevel.NONE)
	private String userId;

	@Column(unique = true, nullable = false, length = 25)
	private String username;

	@Column(nullable = false, length = 100)
	@JsonIgnore
	private String password;

	@Column(nullable = false, length = 30)
	private String fullname;

	@Column(nullable = false, length = 50)
	private String alamat;

	@Column(nullable = false, length = 15)
	private String noTelepon;

	@Column(length = 15)
	private String smartphone;

	@Column(unique = true, nullable = false, length = 254)
	private String email;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(nullable = false)
	private Role role;

	@Column(nullable = false, length = 1)
	@Convert(converter = ActiveStatusConverter.class)
	private boolean active;

	private Instant createdDate;

	private String createdBy;

	private Instant lastModified;

	private String lastModifiedBy;

	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + "]";
	}
}
