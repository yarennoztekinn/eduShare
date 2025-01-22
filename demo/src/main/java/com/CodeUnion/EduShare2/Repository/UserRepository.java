package com.CodeUnion.EduShare2.Repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CodeUnion.EduShare2.Entities.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
	
	Optional<User> findByUniversityMail(String universityEmail);
	
	Optional<User> findByGoogleId(String googleId);

	Optional<User> findByFullName(String fullName);

}
