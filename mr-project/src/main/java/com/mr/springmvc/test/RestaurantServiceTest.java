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
import com.mr.springmvc.model.Restaurant;
import com.mr.springmvc.service.RestaurantService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { HibernateConfiguration.class })
@WebAppConfiguration
public class RestaurantServiceTest {

	@Autowired
	private RestaurantService restaurantService;

	@Test
	public final void findAllTestHasData() {
		assertTrue(restaurantService.findAll().size() > 0);
	}

	@Test
	public final void findAllTestNoData() {
		assertTrue(restaurantService.findAll().size() == 0);
	}

	@Test
	public final void findByIdTestHasId10() {
		assertNotNull(restaurantService.findById(10));
	}

	@Test
	public final void findByIdTestNotFoundId2() {
		assertNull(restaurantService.findById(2));
	}

	@Test
	public final void findByConditionTestNoCondition() {
		assertTrue(restaurantService.findByCondition("", "", "").size() > 0);
	}

	@Test
	public final void findByConditionTestByNameFound() {
		assertTrue(restaurantService.findByCondition("Hai", "", "").size() > 0);
	}

	@Test
	public final void findByConditionTestByNameNotFound() {
		assertTrue(restaurantService.findByCondition("fff", "", "").size() == 0);
	}

	@Test
	public final void findByConditionTestByAddressFound() {
		assertTrue(restaurantService.findByCondition("", "HCM", "").size() > 0);
	}

	@Test
	public final void findByConditionTestByAddressNotFound() {
		assertTrue(restaurantService.findByCondition("", "Uzebkistan", "").size() == 0);
	}

	@Test
	public final void createRestaurantTestSuccess() {
		Restaurant restaurant = new Restaurant();
		restaurant.setName("Test Name");
		restaurant.setAddress("Test Address");
		restaurant.setDescription("Test Description");

		assertNotNull(restaurantService.create(restaurant).getId());
	}

	@Test
	public final void updateRestaurantTestSuccess() {
		Restaurant restaurant = new Restaurant();
		restaurant.setName("Nha hang create");

		restaurant = restaurantService.create(restaurant);
		restaurant.setName("Test Name 1");
		restaurant.setAddress("Test Address 1");
		restaurant.setDescription("Test Description 1");

		try {
			restaurantService.update(restaurant);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Restaurant updatedRestaurant = restaurantService.findById(restaurant.getId());
		assertEquals(updatedRestaurant.getName(), restaurant.getName());
		assertEquals(updatedRestaurant.getAddress(), restaurant.getAddress());
		assertEquals(updatedRestaurant.getDescription(), restaurant.getDescription());
	}

	@Test
	public final void deleteRestaurantTestSuccess() {
		Restaurant restaurant = new Restaurant();
		restaurant.setName("Nha hang create");

		restaurant = restaurantService.create(restaurant);
		assertNotNull(restaurant);

		try {
			restaurantService.delete(restaurant.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Restaurant deleteRestaurant = restaurantService.findById(restaurant.getId());
		assertNull(deleteRestaurant);
	}
}
