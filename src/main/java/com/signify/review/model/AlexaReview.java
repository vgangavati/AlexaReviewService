package com.signify.review.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "all" })
public class AlexaReview {
	private String review;
	private String author;
	@JsonProperty("review_source")
	private String reviewSource;
	private double rating;
	private String title;
	@JsonProperty("product_name")
	private String productName;
	@JsonProperty("reviewed_date")
	private Date reviewedDate;
}
