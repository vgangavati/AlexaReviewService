package com.signify.review.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.signify.review.model.AlexaReview;

@Repository
public interface AlexaReviewDao extends CrudRepository<AlexaReview, String> {
}
