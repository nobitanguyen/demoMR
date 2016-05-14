package com.mr.springmvc.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mr.springmvc.common.SendMail;
import com.mr.springmvc.model.Restaurant;
import com.mr.springmvc.repository.RestaurantRepository;

@Service
public class RestaurantService {

	@Autowired
	private RestaurantRepository restaurantRepository;

	public List<Restaurant> findAll() {
		return restaurantRepository.findAll();
	}

	public Restaurant findById(int id) {
		return restaurantRepository.findOne(id);
	}

	public List<Restaurant> findByCondition(String name, String address, String description) {
		return restaurantRepository.findByCondition(name, address, description);
	}

	@Transactional
	public Restaurant create(Restaurant restaurant) {
		SendMail mail = new SendMail();
		mail.send();
		return restaurantRepository.save(restaurant);
	}

	@Transactional(rollbackFor = Exception.class)
	public Restaurant update(Restaurant restaurant) throws Exception {
		try {
			Restaurant updateRestaurant = restaurantRepository.findOne(restaurant.getId());

			updateRestaurant.setName(restaurant.getName());
			updateRestaurant.setAddress(restaurant.getAddress());
			updateRestaurant.setDescription(restaurant.getDescription());

			return updateRestaurant;
		} catch (Exception e) {
			throw e;
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(int id) throws Exception {
		try {
			restaurantRepository.delete(id);
		} catch (Exception e) {
			throw e;
		}
	}
}
