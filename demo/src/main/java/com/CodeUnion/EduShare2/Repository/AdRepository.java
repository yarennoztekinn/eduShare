package com.CodeUnion.EduShare2.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CodeUnion.EduShare2.Entities.Ad;

@Repository
public interface AdRepository extends JpaRepository<Ad,Long> {
	
	List<Ad> findByFaculty(String faculty);

	List<Ad> findAllById(Long id);

	List<Ad> findAllByUserId(Long id);

}
