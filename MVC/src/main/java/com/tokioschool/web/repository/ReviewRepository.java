package com.tokioschool.web.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tokioschool.web.domain.Review;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {

}
