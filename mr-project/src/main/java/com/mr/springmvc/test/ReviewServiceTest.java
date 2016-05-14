package com.mr.springmvc.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.mr.springmvc.configuration.HibernateConfiguration;
import com.mr.springmvc.model.Review;
import com.mr.springmvc.service.ReviewService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { HibernateConfiguration.class })
@WebAppConfiguration
public class ReviewServiceTest {

	@Autowired
	private ReviewService reviewService;

	@Test
	public final void findAllTestHasData() {
		assertTrue(reviewService.findAll().size() > 0);
	}

	@Test
	public final void findAllTestNoData() {
		assertTrue(reviewService.findAll().size() == 0);
	}

	@Test
	public final void findByRestaurantIdTestFound() {
		assertTrue(reviewService.findByRestaurantId(10).size() > 0);
	}

	@Test
	public final void findByRestaurantIdTestNotFound() {
		assertTrue(reviewService.findByRestaurantId(2).size() == 0);
	}
}
