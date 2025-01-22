package com.CodeUnion.EduShare2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.CodeUnion.EduShare2.Entities.Ad;
import com.CodeUnion.EduShare2.Service.AdService;
import org.springframework.ui.Model;


@Controller
public class PageController {
	
	@Autowired
	private AdService adService;
	
	@GetMapping(path = "/home")
	public String homepage(){
		return "dashboard";
	}
	
	@GetMapping(path = "/start")
	public String registerpage(){
		return "lets-start";
	
	}
	
	@GetMapping(path = "/create-ad")
	public String createAdPage() {
		return "create";
	}
	
	@GetMapping(path = "/show-ads")
	public String showAdsPage() {
		return "browse";
	}
	
	@GetMapping(path = "/ad-details")
	public String detailsAdPage(@RequestParam Long id, Model model) {
		Ad ad = adService.getAdDetailsByAdId(id);
		
		model.addAttribute("ad", ad);
		return "ad-details";
	}

	@GetMapping(path = "/forum")
	public String forumPage() {
		return "forum";
	}
	
	@GetMapping(path = "/notifications")
	public String showMyNotifications() {
		return "bildirimler";
	}
	
	
	@GetMapping(path = "/afterGoogle")
	public String afterGoogle(){
		return "afterGoogle";
	}
	
	@GetMapping(path = "/my-ads")
	public String myAds(){
		return "my-ads";
	}
	
	
}