<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Restaurant</title>
<%@ include file="/WEB-INF/views/header.jsp" %>
</head>
<body ng-app="restaurantApp" class="ng-cloak">

	<%@ include file="/WEB-INF/views/menu.jsp" %>

	<div class="generic-container" ng-controller="RestaurantController">
		<div class="panel panel-default">
			<div class="panel-heading">
				<span class="lead">{{!restaurant.id ? 'Create' : 'Update'}} Restaurant</span>
			</div>
			<div class="formcontainer">
				<form ng-submit="submit()" name="myForm" class="form-horizontal">
					<input type="hidden" ng-model="restaurant.id" />
					<div class="row">
						<div class="form-group col-md-12">
							<label class="col-md-2 control-lable" for="file">Name</label>
							<div class="col-md-7">
								<input type="text" ng-model="restaurant.name" name="uname" class="restaurantname form-control input-sm" placeholder="Enter restaurant name" required />
								<div class="has-error" ng-show="myForm.$dirty">
									<span ng-show="myForm.uname.$error.required">This is a required field</span>
								</div>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="form-group col-md-12">
							<label class="col-md-2 control-lable" for="file">Address</label>
							<div class="col-md-7">
								<input type="text" ng-model="restaurant.address" class="form-control input-sm" placeholder="Enter restaurant address" />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="form-group col-md-12">
							<label class="col-md-2 control-lable" for="file">Description</label>
							<div class="col-md-7">
								<input type="text" ng-model="restaurant.description" name="description" class="form-control input-sm" placeholder="Enter restaurant description" />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="form-actions floatRight">
							<button type="button" ng-click="reset()" class="btn btn-warning btn-sm" ng-show="restaurant.id!=null">Back to add</button>
							<input type="submit" value="{{!restaurant.id ? 'Add' : 'Update'}}" class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid">
						</div>
					</div>
				</form>
			</div>
		</div>
		
		<div class="panel panel-default">
			<!-- Default panel contents -->
			<div class="panel-heading">
				<span class="lead">Import Restaurant </span>
			</div>
			<div class="formcontainer">
				<form ng-submit="uploadFile()" name="myForm" class="form-horizontal" enctype="multipart/form-data">
					<input type="hidden" ng-model="restaurant.id" />
					<div class="row">
						<div class="form-group col-md-12">
							<label class="col-md-2 control-lable" for="file">File input</label>
							<div class="col-md-7">
								<input type="file" id="inputFile" file-model="myFile" required>
								<div class="has-error" ng-show="myForm.$dirty">
									<span ng-show="myForm.unfile.$error.required">This is a required file</span>
								</div>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="form-actions floatRight">
							<input type="submit" value="Import Restaurant" class="btn btn-primary btn-sm">
						</div>
					</div>
				</form>
			</div>
		</div>
		
		<div class="panel panel-default" id="tablecontainer">
			<!-- Default panel contents -->
			<div class="panel-heading">
				<span class="lead">List of Restaurants </span>  
				<span style="float: right; padding-left: 20px;">
				<input ng-click="downloadFile()" type="button" value="Export Restaurant" class="btn btn-primary btn-sm">
				<input type="button" value="Print" ng-click="PrintElem('#tablecontainer')" class="btn btn-primary btn-sm"/>
				</span>
			</div>
			<div class="tablecontainer">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>ID.</th>
							<th>Name</th>
							<th>Address</th>
							<th>Description</th>
							<th width="30%"></th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td></td>
							<td><input ng-model="searchName" class="form-control input-sm" placeholder="Search by name" ng-keyup="search()" /></td>
							<td><input ng-model="searchAddress" class="form-control input-sm" placeholder="Search by address" ng-keyup="search()" /></td>
							<td><input ng-model="searchDescription" class="form-control input-sm" placeholder="Search by description" ng-keyup="search()" /></td>
							<td></td>
						</tr>
						<tr ng-repeat="u in restaurants">
							<td><span ng-bind="u.id"></span></td>
							<td><span ng-bind="u.name"></span></td>
							<td><span ng-bind="u.address"></span></td>
							<td><span ng-bind="u.description"></span></td>
							<td>
								<button type="button" ng-click="edit(u.id)" class="btn btn-success custom-width">Edit</button>
								<button type="button" ng-click="remove(u.id)" class="btn btn-danger custom-width">Remove</button>
								<button type="button" ng-click="review(u)" class="btn btn-default custom-width">Review</button>
								<a href="/mr-project/restaurant/analyze/{{u.id}}" target=_blank><button type="button" class="btn btn-default custom-width">Analyze</button></a>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="panel panel-default" ng-show="selectedRestaurant!=null">
			<!-- Default panel contents -->
			<div class="panel-heading">
				<span class="lead">Reviews of Restaurant: <span ng-bind="selectedRestaurant.name"></span></span>
			</div>
			<div class="tablecontainer">
				<table class="table table-hover">
					<tbody>
						<tr ng-repeat="review in restaurantReviews">
							<td width="100px">
								<span class="glyphicon glyphicon-star" ng-repeat="n in getTimes(review.rating) track by $index"></span>
								<span class="glyphicon glyphicon-star-empty" ng-repeat="n in getTimes(5-review.rating) track by $index"></span>													
							</td>
							<td colspan="2"><pre ng-bind="review.content"></pre></td>
						</tr>
						<tr ng-form="rateForm">
							<td width="100px">
								<span class="{{rate}}" ng-repeat="rate in rates track by $index" ng-click="rateClick($index)" ng-mouseenter="rateMouseEnter($index)" ng-mouseleave="rateMouseLeave()"></span>													
							</td>
							<td>
								<textarea rows="3" ng-model="content" name="content" class="form-control input-sm" placeholder="Enter your comment.." required></textarea>
								<div class="has-error" ng-show="rateForm.$dirty">
									<span ng-show="rateForm.comment.$error.required">This is a required field</span>  
								</div>
							</td>
							<td width="100px">
								<input type="submit" ng-click="addReview()" class="btn btn-primary" ng-disabled="rateForm.$invalid" value="Add Review" />
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>