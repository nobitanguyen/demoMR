'use strict';

App.factory('RestaurantService', ['$http', '$q', function($http, $q){

	return {
		
			getAllRestaurants: function() {
					return $http.get('http://localhost:8080/mr-project/restaurant/list')
							.then(
									function(response){
										return response.data;
									}, 
									function(errResponse){
										console.error('Error while fetching restaurants');
										return $q.reject(errResponse);
									}
							);
			},
			
			searchByCondition: function(name, address, description) {
				return $http.get('http://localhost:8080/mr-project/restaurant/search?name='+name+'&address='+address+'&description='+description)
						.then(
								function(response){
									return response.data;
								}, 
								function(errResponse){
									console.error('Error while fetching restaurants');
									return $q.reject(errResponse);
								}
						);
			},
		    
		    createRestaurant: function(restaurant){
					return $http.post('http://localhost:8080/mr-project/restaurant/create', restaurant)
							.then(
									function(response){
										return response.data;
									}, 
									function(errResponse){
										console.error('Error while creating restaurant');
										return $q.reject(errResponse);
									}
							);
		    },
		    
		    updateRestaurant: function(restaurant, id){
					return $http.put('http://localhost:8080/mr-project/restaurant/update/'+id, restaurant)
							.then(
									function(response){
										return response.data;
									}, 
									function(errResponse){
										console.error('Error while updating restaurant');
										return $q.reject(errResponse);
									}
							);
			},
		    
			deleteRestaurant: function(id){
					return $http.delete('http://localhost:8080/mr-project/restaurant/delete/'+id)
							.then(
									function(response){
										return response.data;
									}, 
									function(errResponse){
										console.error('Error while deleting restaurant');
										return $q.reject(errResponse);
									}
							);
			},
		
			getRestaurantReviews: function(restaurantId) {
				return $http.get('http://localhost:8080/mr-project/review/listByRestaurant?restaurantId='+restaurantId)
						.then(
								function(response){
									return response.data;
								}, 
								function(errResponse){
									console.error('Error while fetching restaurants');
									return $q.reject(errResponse);
								}
						);
			},
			
			addReview: function(review){
				return $http.post('http://localhost:8080/mr-project/review/create', review)
						.then(
								function(response){
									return response.data;
								}, 
								function(errResponse){
									console.error('Error while creating review');
									return $q.reject(errResponse);
								}
						);
			}, uploadFileToUrl : function(file, uploadUrl){
		        var fd = new FormData();
		        fd.append('file', file);
		        $http.post(uploadUrl, fd, {
		            transformRequest: angular.identity,
		            headers: {'Content-Type': undefined}
		        })
		        .success(function(){
		        	
		        })
		        .error(function(){
		        });
		    }, downloadFile: function(){
				return $http.get('http://localhost:8080/mr-project/restaurant/downloadFile')
				.then(
						function(response){
							//return response.data;
							window.location = 'http://localhost:8080/mr-project/restaurant/downloadFile';
						}, 
						function(errResponse){
							console.error('Error while creating file');
							return $q.reject(errResponse);
						}
				);
		    }
	};

}]);
