package org.tondo.issuemanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IssueController {
	
	@Autowired
	private IssueManager issueManager;
	
	@RequestMapping(value = "/")
	public String index(Model model) {
		model.addAttribute("issues", issueManager.findOpenIssues());
		return "index";
	}
	
	
	@RequestMapping(value = "/custom")
	public String custom(Model model, @RequestParam(value="user") String user, @RequestParam(value="repo") String repo) {
		if (user == null || repo == null) {
			model.addAttribute("issues", issueManager.findOpenIssues());
		} else {
			model.addAttribute("issues", issueManager.findOpenIssues(user, repo));
		}
		
		return "index";
	}
	

}
