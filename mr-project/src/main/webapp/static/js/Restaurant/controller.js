'use strict';

App
		.controller(
				'RestaurantController',
				[
						'$scope',
						'RestaurantService',
						function($scope, RestaurantService) {
							$scope.restaurant = {
								id : null,
								name : '',
								address : '',
								description : ''
							};
							$scope.restaurants = [];
							$scope.searchName = '';
							$scope.searchAddress = '';
							$scope.searchDescription = '';

							$scope.getAllRestaurants = function() {
								RestaurantService
										.getAllRestaurants()
										.then(
												function(d) {
													$scope.restaurants = d;
												},
												function(errResponse) {
													console
															.error('Error while fetching Restaurant');
												});
							};
							$scope.getAllRestaurants();

							$scope.search = function() {
								RestaurantService
										.searchByCondition($scope.searchName,
												$scope.searchAddress,
												$scope.searchDescription)
										.then(
												function(d) {
													$scope.restaurants = d;
												},
												function(errResponse) {
													console
															.error('Error while fetching Restaurant');
												});
							};

							$scope.createRestaurant = function(restaurant) {
								RestaurantService
										.createRestaurant(restaurant)
										.then(
												$scope.reset,
												function(errResponse) {
													console
															.error('Error while creating Restaurant.');
												});
							};

							$scope.updateRestaurant = function(restaurant, id) {
								RestaurantService
										.updateRestaurant(restaurant, id)
										.then(
												$scope.reset,
												function(errResponse) {
													console
															.error('Error while updating Restaurant.');
												});
							};

							$scope.deleteRestaurant = function(id) {
								RestaurantService
										.deleteRestaurant(id)
										.then(
												$scope.reset,
												function(errResponse) {
													console
															.error('Error while deleting Restaurant.');
												});
							};

							$scope.submit = function() {
								if ($scope.restaurant.id == null) {
									console.log('Saving New Restaurant',
											$scope.restaurant);
									$scope.createRestaurant($scope.restaurant);
								} else {
									$scope.updateRestaurant($scope.restaurant,
											$scope.restaurant.id);
									console.log('Restaurant updated with id ',
											$scope.restaurant.id);
								}
							};

							$scope.edit = function(id) {
								console.log('id to be edited', id);
								for (var i = 0; i < $scope.restaurants.length; i++) {
									if ($scope.restaurants[i].id == id) {
										$scope.restaurant = angular
												.copy($scope.restaurants[i]);
										break;
									}
								}
							};

							$scope.remove = function(id) {
								console.log('id to be deleted', id);
								if ($scope.restaurant.id === id) {// clean
									// form if
									// the
									// restaurant
									// to be deleted is shown there.
									// $scope.reset();
								}
								$scope.deleteRestaurant(id);
							};

							$scope.reset = function() {
								$scope.getAllRestaurants();
								$scope.restaurant = {
									id : null,
									name : '',
									address : '',
									description : ''
								};
								$scope.myForm.$setPristine(); // reset Form
							};

							$scope.review = function(restaurant) {
								$scope.selectedRestaurant = restaurant;
								RestaurantService
										.getRestaurantReviews(restaurant.id)
										.then(
												function(d) {
													$scope.restaurantReviews = d;
													generateReview();
												},
												function(errResponse) {
													console
															.error('Error while fetching Review');
												});
							};

							$scope.getTimes = function(n) {
								return new Array(n);
							};

							generateReview();
							function generateReview(){
								$scope.comment='';
								$scope.originRates = [];
								for (var int = 0; int < 5; int++) {
									$scope.originRates.push('glyphicon glyphicon-star-empty');
								}
								$scope.rates = $.extend(true, [], $scope.originRates);
								if($scope.rateForm!=undefined)
									$scope.rateForm.$setPristine(); // reset Form
							}

							$scope.rateClick = function(index) {
								$scope.originRates = $.extend(true, [], $scope.rates);
							};
							
							$scope.rateMouseEnter = function(index) {
								for (var int = 0; int <= index; int++) {
									$scope.rates[int] = 'glyphicon glyphicon-star';
								}
								for (var int = index + 1; int < 5; int++) {
									$scope.rates[int] = 'glyphicon glyphicon-star-empty';
								}
							};
							
							$scope.rateMouseLeave = function() {
								$scope.rates = $.extend(true, [], $scope.originRates);
							};
							
							$scope.addReview = function() {
								var newReview = {
									restaurantId: $scope.selectedRestaurant.id,
									rating: $scope.originRates.filter(function(cl){ return cl == "glyphicon glyphicon-star"; }).length,
									content: $scope.content
								};
								
								RestaurantService
								.addReview(newReview)
								.then(
										function(d) {
											$scope.review($scope.selectedRestaurant);
										},
										function(errResponse) {
											console
													.error('Error while fetching Review');
										});
							};
							
							$scope.uploadFile = function(){
						        var file = $scope.myFile;
						        console.log('file is ' );
						        console.dir(file);
						        var uploadUrl = "http://localhost:8080/mr-project/restaurant/fileUpload";
						        RestaurantService.uploadFileToUrl(file, uploadUrl);
						        $scope.getAllRestaurants();
						    };
						    
							$scope.downloadFile = function(){
						        RestaurantService.downloadFile();
						    };
						    
						    $scope.PrintElem = function(elem)
						    {
						        Popup($(elem).html());
						    }

						    function Popup(data) 
						    {
						        var mywindow = window.open('', 'Print', 'height=400,width=600');
						        mywindow.document.write('<html><head><title>Print</title>');
						        /*optional stylesheet*/ //
						        mywindow.document.write('<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">');
						        mywindow.document.write('<link rel="stylesheet" href="/mr-project/static/css/app.css" type="text/css" />');
						        mywindow.document.write('</head><body class="popup">');
						        mywindow.document.write(data);
						        mywindow.document.write('</body></html>');

						        mywindow.document.close(); // necessary for IE >= 10
						        mywindow.focus(); // necessary for IE >= 10

						        mywindow.print();
						        mywindow.close();

						        return true;
						    }
						} ]);
