package com.mine.model;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.SequenceGenerator;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "MD_DEPENDENT") 
public class MdDependentBO implements Serializable { 

	private Long status;
	private String description;
	private Long mdDependentId;
	private Long dependentId;
	private Long depentPropertiesType;
	private String depentValue;
	private Long mdId;

	@Column(name = "STATUS", precision = 22, scale = 0) 
	public Long getStatus() { 
		return status; 
	} 
	public void setStatus(Long status) { 
		this.status = status; 
	} 
	@Column(name = "DESCRIPTION", length = 500) 
	public String getDescription() { 
		return description; 
	} 
	public void setDescription(String description) { 
		this.description = description; 
	} 
	@SequenceGenerator(name = "generator", sequenceName = "MD_DEPENDENT_SEQ", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "MD_DEPENDENT_ID", unique = true, nullable = false, precision = 22, scale = 0) 
	public Long getMdDependentId() { 
		return mdDependentId; 
	} 
	public void setMdDependentId(Long mdDependentId) { 
		this.mdDependentId = mdDependentId; 
	} 
	@Column(name = "DEPENDENT_ID", precision = 22, scale = 0) 
	public Long getDependentId() { 
		return dependentId; 
	} 
	public void setDependentId(Long dependentId) { 
		this.dependentId = dependentId; 
	} 
	@Column(name = "DEPENT_PROPERTIES_TYPE", precision = 22, scale = 0) 
	public Long getDepentPropertiesType() { 
		return depentPropertiesType; 
	} 
	public void setDepentPropertiesType(Long depentPropertiesType) { 
		this.depentPropertiesType = depentPropertiesType; 
	} 
	@Column(name = "DEPENT_VALUE", length = 100) 
	public String getDepentValue() { 
		return depentValue; 
	} 
	public void setDepentValue(String depentValue) { 
		this.depentValue = depentValue; 
	} 
	@Column(name = "MD_ID", precision = 22, scale = 0) 
	public Long getMdId() { 
		return mdId; 
	} 
	public void setMdId(Long mdId) { 
		this.mdId = mdId; 
	} 


	public MdDependentBO() {
	}
	public MdDependentBO(Long mdDependentId) {
		this.mdDependentId = mdDependentId;
	}
}