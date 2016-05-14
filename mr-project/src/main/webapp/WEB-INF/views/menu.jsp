<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="nav-menu container">
	<ul>
		<li><a href="/mr-project/"> Restaurant</a></li>
		<c:if test="${user != null}">
			<li><a href="/mr-project/logout">[${user}] Logout</a></li>
		</c:if>
	</ul>
</div>