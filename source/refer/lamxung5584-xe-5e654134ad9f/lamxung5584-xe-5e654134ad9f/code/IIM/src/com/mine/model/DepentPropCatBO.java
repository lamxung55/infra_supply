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
@Table(name = "DEPENT_PROP_CAT") 
public class DepentPropCatBO implements Serializable {

	private String code;
	private String description;
	private Long depentPropCatId;
	private String value;
	private String name;

        @Column(name = "CODE", length = 100) 
	public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
	@Column(name = "DESCRIPTION", length = 500) 
	public String getDescription() { 
		return description; 
	} 

    
	public void setDescription(String description) { 
		this.description = description; 
	} 
	@SequenceGenerator(name = "generator", sequenceName = "DEPENT_PROP_CAT_SEQ", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "DEPENT_PROP_CAT_ID", unique = true, nullable = false, precision = 22, scale = 0) 
	public Long getDepentPropCatId() { 
		return depentPropCatId; 
	} 
	public void setDepentPropCatId(Long depentPropCatId) { 
		this.depentPropCatId = depentPropCatId; 
	} 
	@Column(name = "VALUE", length = 500) 
	public String getValue() { 
		return value; 
	} 
	public void setValue(String value) { 
		this.value = value; 
	}
        @Column(name = "NAME", length = 500) 
	public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


	public DepentPropCatBO() {
	}
   
	public DepentPropCatBO(Long depentPropCatId) {
		this.depentPropCatId = depentPropCatId;
	}
}