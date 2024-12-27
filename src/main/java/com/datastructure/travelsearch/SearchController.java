package com.datastructure.travelsearch;

import java.util.ArrayList;
import java.util.PriorityQueue;

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
        	Pair<ArrayList<WebPage>, ArrayList<String>> query_return = Search.search(query);
        	ArrayList<WebPage> results = query_return.get1();
            model.addAttribute("results", results);
            ArrayList<String> related = query_return.get2();
            model.addAttribute("related", related);
            return "index";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
