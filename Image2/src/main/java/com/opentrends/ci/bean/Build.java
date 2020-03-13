package com.opentrends.ci.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * Class for build specifications
 * 
 * @author iorodriguez
 *
 */
@Entity
@Data
public class Build {
	
	// TODO: I prefer to use DTO and Entities separately but to simplify I will use entity object end-to-end in this test.
	
	@Id
	@GeneratedValue(generator = "SEQ_BUILD_SPEC")
	@SequenceGenerator(name = "SEQ_BUILD_SPEC", sequenceName = "SEQ_BUILD_SPEC")
	@JsonIgnore
	private Long id;
	
	@NotNull(message = "{build.not.empty}")
	@Pattern(regexp="\\d+", message="{build.pattern.error}")
	private String buildId; //number
	
	@NotBlank(message = "{name.not.empty}")
	@Pattern(regexp="[^0-9]*", message="{name.pattern.error}")
	private String name; //string only letters
	
	@NotBlank(message = "{pathRepo.not.empty}")
	@Pattern(regexp="^[a-zA-Z0-9\\\\]+$", message="{pathRepo.pattern.error}")
	private String pathRepo; //(string with slashes(\)). NOTE: Escape \ with double \\ in Swagger post because otherwise it will broke the request.
	
	@NotBlank(message = "{version.not.empty}")
	@Pattern(regexp="\\d+.\\d+.\\d+", message="{version.pattern.error}")
	private String version; //(M.m.f)

}
