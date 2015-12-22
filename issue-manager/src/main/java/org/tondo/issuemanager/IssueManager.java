package org.tondo.issuemanager;


import java.util.ArrayList;
import java.util.List;

import org.springframework.social.github.api.GitHubIssue;
import org.springframework.social.github.api.impl.GitHubTemplate;
import org.springframework.stereotype.Service;

// this class will be picked by @ComponentScan
@Service
public class IssueManager {
	
	private String githubToken = "59093dc58055434349ce37f9ddac83948449eb22";
	private String[] repos  = new String[]{"spring-boot", "spring-boot-issues"};
	
	// main class for interactiong with github
	// probably constructor with token for OAuth will be needed
	private GitHubTemplate githubTemplate = new GitHubTemplate();
	
	
	public List<Issue> findOpenIssues() {
		List<Issue> openIssues = new ArrayList<>();
		for (String repo  : this.repos) {
			openIssues.addAll(findOpenIssues("spring-projects", repo));
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
}
