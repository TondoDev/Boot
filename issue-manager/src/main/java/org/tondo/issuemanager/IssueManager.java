package org.tondo.issuemanager;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.github.api.GitHubIssue;
import org.springframework.social.github.api.impl.GitHubTemplate;
import org.springframework.stereotype.Service;

// this class will be picked by @ComponentScan
@Service
public class IssueManager implements InitializingBean {
	
	@Value("${token}")
	private String githubToken;
	
	@Value("${repos}")
	private String[] repos;
	
	// after : means default value for this property used if any source for this proprty is found
	@Value("${username:spring-projects}")
	private String userName;
	
	// main class for interactiong with github
	// probably constructor with token for OAuth will be needed
	private GitHubTemplate githubTemplate;
	
	
	public List<Issue> findOpenIssues() {
		List<Issue> openIssues = new ArrayList<>();
		for (String repo  : this.repos) {
			openIssues.addAll(findOpenIssues(userName, repo));
		}
		return openIssues;
	}
	
	public List<Issue> findOpenIssues(String user, String repo) {
		List<Issue> openIssues = new ArrayList<>();
		// here will be needed token authorization for user
		List<GitHubIssue> ghIssues = githubTemplate.repoOperations().getIssues(user, repo);
		for (GitHubIssue i : ghIssues) {
			if ("open".equals(i.getState())) {
				openIssues.add(new Issue(repo, i));
			}
		}
		return openIssues;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// template will be created after all properties are set by Spring container
		this.githubTemplate = new GitHubTemplate(githubToken);
	}
}
