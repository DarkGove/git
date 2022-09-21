package com.open.spring.gitlab.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@ConfigurationProperties(prefix = "gitlabl" )
public class GitlabController {

	@Autowired
	GitlabService gitlabService;
	
	@PostMapping("/create/branch")
	public void createBranch() {
		gitlabService.createBranch();
	}
}
