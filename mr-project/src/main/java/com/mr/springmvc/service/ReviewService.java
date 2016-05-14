package com.mr.springmvc.service;

import java.util.Date;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mr.springmvc.model.Review;
import com.mr.springmvc.repository.ReviewRepository;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	public List<Review> findAll() {
		return reviewRepository.findAll();
	}

	public Review findById(String id) {
		return reviewRepository.findOne(id);
	}

	public List<Review> findByRestaurantId(int restaurantId) {
		return reviewRepository.findByRestaurantId(restaurantId);
	}

	public Review create(Review review) {
		review.setReviewDate(new Date());
		return reviewRepository.save(review);
	}
}
