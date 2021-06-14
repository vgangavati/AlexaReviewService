package com.signify.review.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.signify.review.exception.AlexaReviewException;
import com.signify.review.model.AlexaReview;
import com.signify.review.service.AlexaReviewService;

@RestController
public class AlexaReviewController {

	@Autowired
	private AlexaReviewService alexaReviewService;

	@PostMapping("alexa/review")
	public ResponseEntity<String> saveReview(@RequestBody AlexaReview review) throws AlexaReviewException {
		alexaReviewService.saveReview(review);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/alexa/reviews")
	public ResponseEntity<Iterable<AlexaReview>> getAllReviews(HttpServletRequest request) throws AlexaReviewException {
		return new ResponseEntity<Iterable<AlexaReview>>(alexaReviewService.getAllReviews(request.getParameterMap()),
				HttpStatus.OK);
	}

	@PostMapping("alexa/reviews")
	public ResponseEntity<String> saveReviews(@RequestBody List<AlexaReview> reviews) throws AlexaReviewException {
		alexaReviewService.saveReviews(reviews);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("alexa/reviews")
	public ResponseEntity<String> deleteReviews() throws AlexaReviewException {
		alexaReviewService.deleteAllReviews();
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("alexa/monthlyAverageByStore")
	public ResponseEntity<String> getMonthlyAverageRatings() throws AlexaReviewException {
		return new ResponseEntity<String>(alexaReviewService.getAverageMonthlyRatingPerStore(), HttpStatus.OK);
	}

	@GetMapping("alexa/ratingsPerCategory")
	public ResponseEntity<String> getRatingsPerCategory() throws AlexaReviewException {
		return new ResponseEntity<String>(alexaReviewService.getRatingsByCategory(), HttpStatus.OK);
	}
}
