package com.mr.springmvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mr.springmvc.model.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

	@Query("SELECT r FROM Restaurant r where r.name LIKE CONCAT('%', :name, '%') AND r.address LIKE CONCAT('%', :address, '%') AND r.description LIKE CONCAT('%', :description, '%')")
	public List<Restaurant> findByCondition(@Param("name") String name,
			@Param("address") String address, @Param("description") String description);

}
