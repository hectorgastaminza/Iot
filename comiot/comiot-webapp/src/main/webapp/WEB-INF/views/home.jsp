<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigation.jspf"%>
<!------ WEB DESIGN ---------->



<!------ ELEMENTS : See Dropdowns ---------->
<c:forEach items="${places}" var="place">
	<!------
	<h4 class="place">[${place.placeID}] ${place.placeName}</h4>
	-------->
	<div class="row" style="margin: 5px">
		<c:forEach items="${place.getDevices()}" var="device">
			<div class="card device bg-light border-secondary"
				style="margin: 5px; width: 16rem; text-align: center;">
				<a class="card-header border-secondary" style="text-align: left;"
					href="/app/devicecreate?update=${device.pk}">${device.name}</a>
				<div class="card-body border-secondary">
					<c:choose>
						<c:when test="${(device.state == 'NONE')}">
							<h5 class="card-title text-warning">DISCONNECTED</h5>
							<p class="card-text"></p>
						</c:when>
						<c:when test="${(device.state == 'ON_VALUE')}">
							<h3 class="card-title text-success">${device.value}</h3>
						</c:when>
						<c:otherwise>
							<h3 class="card-title text-success">${device.state}</h3>
							<p class="card-text"></p>
						</c:otherwise>
					</c:choose>
				</div>
				<p class="card-text">[ Place : ${device.getPlaceID()} | ID :
					${device.getId()} ]</p>
				<div class="card-foot border-secondary">
					<input id="devicePk" name="devicePk" type="hidden"
						value="${device.pk}">
					<div class="btn-group" style="margin: 2px;">
						<button type="button"
							class="btn btn-sm btn-success dropdown-toggle dropdown-toggle-split"
							data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							<span class="sr-only">Toggle Dropdown</span> Send
						</button>
						<div class="dropdown-menu">
							<a class="dropdown-item"
								href="/app/commandsend?on=${device.getId()}">ON</a> <a
								class="dropdown-item"
								href="/app/commandsend?off=${device.getId()}">OFF</a>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
	<!------
 <button type="submit" class="btn btn-primary">MM</button> 
 -------->
</c:forEach>


<%@ include file="../common/footer.jspf"%>