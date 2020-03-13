package com.opentrends.repository.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * Class for repository specifications
 * 
 * @author iorodriguez
 *
 */
@Data
@Entity
public class RepositorySpec {

	// TODO: I prefer to use DTO and Entities separately but to simplify I will use entity object end-to-end in this test.

	@Id
	@GeneratedValue(generator = "SEQ_REPO_SPEC")
	@SequenceGenerator(name = "SEQ_REPO_SPEC", sequenceName = "SEQ_REPO_SPEC")
	@JsonIgnore
	private Long id;

	@Size(max = 50)
	@NotBlank(message = "{name.not.empty}")
	private String name;

	@Size(max = 200)
	@NotBlank(message = "{description.not.empty}")
	private String description;

	@Size(max = 50)
	@NotBlank(message = "{tagname.not.empty}")
	private String tagName;

	@Size(max = 100)
	@JsonIgnore
	private String webUrl;

}
