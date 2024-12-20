package com.datastructure.travelsearch;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/search")
    public String search(@RequestParam String query, Model model) {
        try {
        	HashMap<String, String> results = Search.search(query);
            model.addAttribute("results", results);
            return "index";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
