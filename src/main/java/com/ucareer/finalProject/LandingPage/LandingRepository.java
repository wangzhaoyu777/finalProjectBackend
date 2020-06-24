package com.ucareer.finalProject.LandingPage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LandingRepository extends JpaRepository<Landing, Long> {
}

