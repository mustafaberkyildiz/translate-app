package com.mustafaberk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mustafaberk.model.RefreshToken;
import java.util.Optional;
import java.util.List;



@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{
	
	Optional<RefreshToken> findByRefreshToken(String refreshToken);
	
}
