package org.tondo.issuemanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IssueController {
	
	@Autowired
	private IssueManager issueManager;
	
	@RequestMapping(value = "/")
	public String index(Model model) {
		model.addAttribute("issues", issueManager.findOpenIssues());
		return "index";
	}
	

}
