package com.mr.springmvc.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mr.springmvc.model.Review;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {
	List<Review> findByRestaurantId(int restaurantId);
}