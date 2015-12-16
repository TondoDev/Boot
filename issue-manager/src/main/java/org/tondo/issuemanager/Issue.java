package org.tondo.issuemanager;

import org.springframework.social.github.api.GitHubIssue;

/**
 * Extension of standard GithubIssue class by Repositary URL
 * @author TondoDev
 *
 */
public class Issue {
	private String repo;
	private GitHubIssue githubIssue;
	
	public Issue(String repo, GitHubIssue issue) {
		this.repo = repo;
		this.githubIssue = issue;
	}
	
	
	public String getRepo() {
		return repo;
	}
	
	public GitHubIssue getGitHubIssue() {
		return githubIssue;
	}
	
}
