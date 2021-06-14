package com.signify.review.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.signify.review.dao.AlexaReviewDao;
import com.signify.review.exception.AlexaReviewException;
import com.signify.review.model.AlexaReview;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AlexaReviewServiceImpl implements AlexaReviewService {
	@Autowired
	private AlexaReviewDao alexaReviewDao;

	@Override
	public void saveReview(AlexaReview review) throws AlexaReviewException {
		try {
			alexaReviewDao.save(review);
			log.debug("review saved {}", review);
		} catch (Exception e) {
			throw new AlexaReviewException(e.getMessage(), e);
		}
	}

	@Override
	public void saveReviews(List<AlexaReview> reviews) throws AlexaReviewException {
		try {
			alexaReviewDao.saveAll(reviews);
			log.debug("review saved {}", reviews);
		} catch (Exception e) {
			throw new AlexaReviewException(e.getMessage(), e);
		}
	}

	@Override
	public Iterable<AlexaReview> getAllReviews(Map<String, String[]> parameters) throws AlexaReviewException {
		if (parameters.isEmpty()) {
			return alexaReviewDao.findAll();
		}
		Stream<AlexaReview> filter = Streamable.of(alexaReviewDao.findAll()).stream();
		if (parameters.get("reviewed_date") != null && parameters.get("reviewed_date").length != 0) {
			String reviewedDateStr = parameters.get("reviewed_date")[0];
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date reviewedDate;
			try {
				reviewedDate = sdf.parse(reviewedDateStr);
			} catch (ParseException e) {
				throw new AlexaReviewException("Invalid Date", e);
			}
			filter = filter.filter(review -> DateUtils.isSameDay(review.getReviewedDate(), reviewedDate));
		}

		if (parameters.get("review_source") != null && parameters.get("review_source").length != 0) {
			String reviewSource = parameters.get("review_source")[0];
			filter = filter.filter(review -> review.getReviewSource().equals(reviewSource));
		}

		if (parameters.get("rating") != null && parameters.get("rating").length != 0) {
			int rating;
			try {
				rating = Integer.parseInt(parameters.get("rating")[0]);
			} catch (NumberFormatException e) {
				throw new AlexaReviewException("Invalid Rating", e);
			}
			filter = filter.filter(review -> review.getRating() == rating);
		}
		return filter.collect(Collectors.toList());
	}

	@Override
	public String getAverageMonthlyRatingPerStore() throws AlexaReviewException {
		Map<String, Double> ratingsPerStore = Streamable.of(alexaReviewDao.findAll()).stream().collect(Collectors
				.groupingBy(AlexaReview::getReviewSource, Collectors.averagingDouble(AlexaReview::getRating)));
		try {
			return new ObjectMapper().writeValueAsString(ratingsPerStore);
		} catch (JsonProcessingException e) {
			throw new AlexaReviewException(e.getMessage(), e);
		}
	}

	@Override
	public String getRatingsByCategory() throws AlexaReviewException {
		Map<Double, Long> countByRatings = Streamable.of(alexaReviewDao.findAll()).stream()
				.collect(Collectors.groupingBy(AlexaReview::getRating, Collectors.counting()));
		try {
			return new ObjectMapper().writeValueAsString(countByRatings);
		} catch (JsonProcessingException e) {
			throw new AlexaReviewException(e.getMessage(), e);
		}
	}

	@Override
	public void deleteAllReviews() {
		alexaReviewDao.deleteAll();
	}

}
