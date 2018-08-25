<%@ include file="../common/header.jspf" %>
<%@ include file="../common/navigation.jspf" %>
<!------ WEB DESIGN ---------->



<!------ ELEMENTS : See Dropdowns ---------->
<c:forEach items="${places}" var="place">
	<!------
	<h4 class="place">[${place.placeID}] ${place.placeName}</h4>
	-------->
	<div class="row" style = "margin: 5px">
		<c:forEach items="${place.getDevices()}" var="device">
			<div class="card device bg-light border-secondary" style = "margin: 5px; width: 16rem; text-align: center;">
				<div class="card-header border-secondary" style ="text-align: left;">${device.name}</div>
				<div class="card-body border-secondary">
					<c:choose>
						<c:when test="${(device.state == 'NONE')}">
							<h5 class="card-title">DISCONNECTED</h5>
							<p class="card-text"> </p>
						</c:when>
						<c:when test="${device.value > '0'}">
							<h5 class="card-title">${device.value}</h5>
							<p class="card-text">${device.state}</p>
						</c:when>
						<c:otherwise>
							<h5 class="card-title">${device.state}</h5>
							<p class="card-text"> </p>
						</c:otherwise>
					</c:choose>
				</div>
				<p class="card-text">[ Place : ${device.getPlaceID()} | ID : ${device.getId()} ]</p>
				<div class="card-foot border-secondary">
					<input id="productId" name="devicePk" type="hidden"
						value="${device.pk}">
					<button type="submit" class="btn btn-primary">NNN</button>
				</div>
			</div>
		</c:forEach>
	</div>
	<!------
 <button type="submit" class="btn btn-primary">MM</button> 
 -------->
</c:forEach>


<%@ include file="../common/footer.jspf" %>