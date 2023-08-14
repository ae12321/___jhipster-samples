package com.mycompany.myapp.custom.repository;

import com.mycompany.myapp.custom.dto.AuthorDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorDetailRepository extends JpaRepository<AuthorDetail, Long> {}
