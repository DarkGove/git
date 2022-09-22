 package com.open.spring.gitlab.api;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name="GitlabService",description="Gitlab Api")
@RequestMapping("/branch")
public class GitlabService {

	private ObjectMapper mapper = new ObjectMapper();

    private Map<String,String> jsonResponse;

	private static final String TOKEN = "glpat-9ntaVF_vxBy_cfxkAy3D";
	
	private RestTemplate restTemplate = new RestTemplate();
	
	
	@Operation(summary = "Create project", description = "Create project for gitlab", tags = { "GitlabService" })
	@PostMapping("/create")
	public @ResponseBody void createProject() {
		
		String url = "https://gitlab.com/api/v4/projects?private_token=" + TOKEN;
		
		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		// request body parameters
		Map<String, Object> map = new HashMap<>();
		String requestJson = "{\"name\":\"branch" + DigestUtils.md5DigestAsHex(new String("" + System.currentTimeMillis()).getBytes()) + "\"}";
		map.put("data", requestJson);
		map.put("name", "branch-" + DigestUtils.md5DigestAsHex(new String("" + System.currentTimeMillis()).getBytes()));

		// build the request
		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

	
		
		// send POST request
		ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

		// check response
		if (response.getStatusCode() == HttpStatus.CREATED) {
			System.out.println("Project created  Successful");
			System.out.println(response.getBody());
			
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			try {
				jsonResponse = mapper.readValue(response.getBody(), Map.class);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				System.out.println("Json parsed Failed");
			}
			
			if(!jsonResponse.isEmpty()) {
				
				Integer id = Integer.parseInt(String.valueOf(jsonResponse.get("id")));
				createBranch(id, "master");
				createBranch(id, "develop");
				createTag(id, "0.0.1");
			}
			
		} else {
			System.out.println("Project created Failed");
			System.out.println(response.getStatusCode());
		}
	}

	private void createBranch(int id, String name) {
		String url = "https://gitlab.com/api/v4/projects/" + id + "/repository/branches?private_token=" + TOKEN;

		// request body parameters
		Map<String, Object> map = new HashMap<>();
		map.put("branch", name);
		map.put("ref", "main");

		// build the request
		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map);

		// send POST request
		ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

		System.out.println("Status: " + response.getStatusCode());
		// check response
		if (response.getStatusCode() == HttpStatus.CREATED) {
			System.out.println("Branch created Successful");
			System.out.println(response.getBody());
		} else {
			System.out.println("Branch created Failed");
			System.out.println(response.getStatusCode());
		}
	}
	
	private void createTag(int id, String tagName) {
		String url = "https://gitlab.com/api/v4/projects/" + id + "/repository/tags?private_token=" + TOKEN;

		// request body parameters
		Map<String, Object> map = new HashMap<>();
		map.put("tag_name", tagName);
		map.put("ref", "main");

		// build the request
		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map);

		// send POST request
		ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

		System.out.println("Status: " + response.getStatusCode());
		// check response
		if (response.getStatusCode() == HttpStatus.CREATED) {
			System.out.println("Tag created Successful");
			System.out.println(response.getBody());
		} else {
			System.out.println("Tag created Failed");
			System.out.println(response.getStatusCode());
		}
	}

	
	

}
