package com.signify.review.service;

import java.util.List;
import java.util.Map;

import com.signify.review.exception.AlexaReviewException;
import com.signify.review.model.AlexaReview;

public interface AlexaReviewService {
	/**
	 * Saves review to database.
	 * 
	 * @param review
	 * @throws AlexaReviewException
	 */
	void saveReview(AlexaReview review) throws AlexaReviewException;

	/**
	 * Save all the reviews to database.
	 * 
	 * @param reviews
	 * @throws AlexaReviewException
	 */
	void saveReviews(List<AlexaReview> reviews) throws AlexaReviewException;

	/**
	 * returns all alexa reviews.
	 * 
	 * @param parameters
	 * 
	 * @return
	 * @throws AlexaReviewException
	 */
	Iterable<AlexaReview> getAllReviews(Map<String, String[]> parameters) throws AlexaReviewException;

	/**
	 * Get average monthly rating by store.
	 * 
	 * @return
	 * @throws AlexaReviewException
	 */
	String getAverageMonthlyRatingPerStore() throws AlexaReviewException;

	/**
	 * Deletes all the reviews.
	 */
	void deleteAllReviews();

	/**
	 * @return
	 * @throws AlexaReviewException
	 */
	String getRatingsByCategory() throws AlexaReviewException;

}
