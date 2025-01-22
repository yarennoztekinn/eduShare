package com.CodeUnion.EduShare2.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.CodeUnion.EduShare2.Entities.Ad;
import com.CodeUnion.EduShare2.Request.AdRequest;
import com.CodeUnion.EduShare2.Service.AdService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping(path = "/ad")
public class AdController {
	
	@Autowired
	private AdService adservice;
	
	@GetMapping(path="/get-all")	
	public List<Ad> getAllAds(){
		return adservice.getAllAds();
	}
	
	@PostMapping(path = "/create")
	public ResponseEntity<?> createAd(@RequestParam("title") String title,
	                                  @RequestParam("faculty") String faculty,
	                                  @RequestParam("method") String method,
	                                  @RequestParam("description") String description,
	                                  @RequestParam("price") String price,
	                                  @RequestParam("photo") MultipartFile photo,
	                                  HttpSession session) {

	    AdRequest adRequest = new AdRequest();
	    adRequest.setTitle(title);
	    adRequest.setFaculty(faculty);
	    adRequest.setMethod(method);
	    adRequest.setDescription(description);
	    adRequest.setPrice(price);

	    if (photo != null) {
	        try {
	            adRequest.setPhoto(photo.getBytes());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    } else {
	        // Default photo or handle accordingly
	        adRequest.setPhoto(new byte[0]); // Or some default byte array
	    }

	    if (adservice.createAd(adRequest, session)) {
	        return ResponseEntity.ok("Ad created successfully!");
	    }
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ad couldn't be created!");
	}

	
	@DeleteMapping(path = "/delete")
	public ResponseEntity<?> deleteAd(@RequestBody Ad ad){
		if (adservice.deleteAd(ad)) {
			return ResponseEntity.ok("ad couldn't find!");
		}
		adservice.deleteAd(ad);
		return ResponseEntity.ok("Ad deleted successfuly!");
	}
	
	@GetMapping("/getAdByFacultyName/{faculty}")
    public ResponseEntity<List<Ad>> getAdsByFaculty(@PathVariable("faculty") String faculty) {
		String decodedFacultyName = URLDecoder.decode(faculty, StandardCharsets.UTF_8);
        List<Ad> ads = adservice.getAdsByFaculty(decodedFacultyName);
        return new ResponseEntity<>(ads, HttpStatus.OK);
    }

	@GetMapping(path = "/getDetails/{Id}")
	public ResponseEntity<? >getAdDetailsByAdId(@PathVariable("Id") Long Id) {
		Ad ad = adservice.getAdDetailsByAdId(Id);
		if (ad != null) {
			return ResponseEntity.ok(ad);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ilani gosterirken bir hata olustu!");
	}
	
    @GetMapping(path = "/{Id}/owner")
    public ResponseEntity<Long> getAdOwnerAdId(@PathVariable Long Id) {
        Long ownerId = adservice.getOwnerIdByAdId(Id);
        return ResponseEntity.ok(ownerId);
    }
    
    @GetMapping(path = "/ilanlarim")//ilanlarim butonu
    public List<Ad> getAllAdsByUserId(HttpSession session){
    	List<Ad> myAds = adservice.getAllAdsByUserId(session);
    	return myAds;
    } 
    	
}
