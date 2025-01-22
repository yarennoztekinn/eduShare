package com.CodeUnion.EduShare2.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.CodeUnion.EduShare2.Entities.Ad;
import com.CodeUnion.EduShare2.Entities.User;
import com.CodeUnion.EduShare2.Repository.AdRepository;
import com.CodeUnion.EduShare2.Request.AdRequest;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class AdService {
	
	@Autowired
	private AdRepository adRepository;

	public List<Ad> getAllAds() {
		return adRepository.findAll();
	}

	public boolean deleteAd(Ad ad) {
		if (ad == null) {
			return false;
		}
		adRepository.delete(ad);
		return true;
	}

	public boolean createAd(AdRequest adRequest,HttpSession session) {
		User loggedUser = (User) session.getAttribute("loggedUser");
		Ad ad = new Ad();
	    BeanUtils.copyProperties(adRequest, ad);
	    ad.setUser(loggedUser);
	    
	    adRepository.save(ad);
		return true;
	}

	@Transactional
	public List<Ad> getAdsByFaculty(String faculty) {
        return adRepository.findByFaculty(faculty);
    }

	public Ad getAdDetailsByAdId(Long id) {
		Ad ad = adRepository.findById(id).orElseThrow(() -> new RuntimeException("Not bulunamadı"));
		return ad;
	}

    public Long getOwnerIdByAdId(Long Id) {
        Ad ad = adRepository.findById(Id).orElseThrow(() -> new IllegalArgumentException("İlan bulunamadı: " + Id));// İlanı veritabanından bul
        return ad.getUser().getId(); // İlanı koyan kişinin id'sini döndür
    }
    
    @Transactional
    public List<Ad> getAllAdsByUserId(HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            throw new RuntimeException("Oturum açmamış kullanıcı!");
        }
        Long userId = loggedUser.getId();
        return adRepository.findAllByUserId(userId);
    }

}
