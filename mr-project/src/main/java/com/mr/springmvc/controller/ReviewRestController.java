package com.mr.springmvc.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mr.springmvc.model.Review;
import com.mr.springmvc.service.ReviewService;

@Controller
@RequestMapping(value = "/review")
public class ReviewRestController {

	@Autowired
	private ReviewService reviewService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseEntity<List<Review>> listAllReviews() {
		List<Review> reviews = reviewService.findAll();

		if (reviews.isEmpty()) {
			return new ResponseEntity<List<Review>>(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}

		return new ResponseEntity<List<Review>>(reviews, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/listByRestaurant", method = RequestMethod.GET)
	public ResponseEntity<List<Review>> getRestaurantReviews(@RequestParam("restaurantId") int restaurantId) {
		List<Review> reviews = reviewService.findByRestaurantId(restaurantId);

		if (reviews.isEmpty()) {
			return new ResponseEntity<List<Review>>(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}

		return new ResponseEntity<List<Review>>(reviews, HttpStatus.OK);
	}

	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Review> getReview(@PathVariable("id") String id) {
		Review review = reviewService.findById(id);

		if (review == null) {
			return new ResponseEntity<Review>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Review>(review, HttpStatus.OK);
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<Review> createReview(@RequestBody Review review) {
		try {
			review.setReviewDate(new Date());
			reviewService.create(review);
			return new ResponseEntity<Review>(review, HttpStatus.CREATED);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<Review>(HttpStatus.NOT_FOUND);
		}
	}
}
