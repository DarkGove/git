 package com.open.spring.gitlab.api;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name="GitlabService",description="Gitlab Api")
@RequestMapping("/branch")
public class GitlabService {

	private static final String TOKEN = "glpat-9ntaVF_vxBy_cfxkAy3D";
	
//	curl --request POST --header "PRIVATE-TOKEN: <your-token>" \
//    --header "Content-Type: application/json" --data '{
//       "name": "new_project", "description": "New Project", "path": "new_project",
//       "namespace_id": "42", "initialize_with_readme": "true"}' \
//    --url 'https://gitlab.example.com/api/v4/projects/'
	
	@Operation(summary = "Create project", description = "Create project for gitlab", tags = { "GitlabService" })
	@PostMapping("/create")
	public @ResponseBody String createBranch() {
		RestTemplate restTemplate = new RestTemplate();
		
		String url = "https://gitlab.example.com/api/v4/projects?private_token=" + TOKEN;
		
	
	
		return restTemplate.postForObject(url,);
	}
	
	
	
	

}
