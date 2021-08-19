package com.ishwari.accountapi.entity;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

@MappedSuperclass
public class BaseTransactionalEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Primary key
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * The time when the entity was created
	 */
	@Column(name = "CREATIONTIME")
	private Instant creationTime;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Instant getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(final Instant creationTime) {
		this.creationTime = creationTime;
	}

	/**
	 * JPA entity event listener that set the <code>creationTime</code> to the
	 * current timestamp before an instance of BaseTransactionalEntity is persisted.
	 */
	@PrePersist
	public void prePersist() {
		setCreationTime(Instant.now());
	}

	/**
	 * Function for the equality of two BaseTransactionalEntity objects. If the
	 * object is null, returns false. If both objects are of the same class, and
	 * their <code>id</code> values are not null and equal, return <code>true</code>
	 * otherwise, return <code>false</code>.
	 *
	 * @param obj An Object
	 * @return A boolean
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (this.getClass().equals(obj.getClass())) {
			final BaseTransactionalEntity entityObj = (BaseTransactionalEntity) obj;
			if (this.getId() == null || entityObj.getId() == null) {
				return false;
			}
			if (this.getId().equals(entityObj.getId())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the hash value of this object.
	 *
	 * @return int
	 */
	@Override
	public int hashCode() {
		if (getId() == null) {
			return -1;
		}
		return getId().hashCode();
	}
}
