<%@ include file="../common/header.jspf" %>
<%@ include file="../common/navigation.jspf" %>
<!------ WEB DESIGN ---------->



<!------ ELEMENTS : See Dropdowns ---------->
<div class="row content">
	<div class="d-flex flex-row col-sm-8">

		<c:forEach items="${places}" var="place">
			<div class="d-flex flex-row place">
				<div class="well">${place.placeName}</div>
				<p>${place.placeID}</p>
				<p>${place.description}</p>
				<input id="placePk" name="placePk" type="hidden" value="${place.pk}">
			</div>
			<c:forEach items="${place.getDevices()}" var="device">
				<div class="d-flex device">
					<div class="card-header">${device.name}</div>
					<div class="card-body">${device.state}</div>
					<div class="card-body">${device.value}</div>
					<div class="card-footer">Footer</div>
					<input id="devicePk" name="devicePk" type="hidden"
						value="${device.pk}">
					<button type="submit" class="btn btn-primary">NNN</button>
					
			<!-- CREDIT CARD FORM STARTS HERE -->
            <div class="panel panel-default credit-card-box">
                <div class="panel-heading display-table" >
                    <div class="row display-tr" >
                        <h3 class="panel-title display-td" >${device.name}</h3>
                    </div>                    
                </div>
                <div class="panel-body">
                    <form role="form" id="payment-form" method="POST" action="javascript:void(0);">
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label for="cardNumber">CARD NUMBER</label>
                                    <div class="input-group">
										<label for="cardExpiry"><span class="hidden-xs">${device.state}</span><span class="visible-xs-inline">EXP</span> DATE</label>
                                        <span class="input-group-addon"><i class="fa fa-credit-card"></i></span>
                                    </div>
                                </div>                            
                            </div>
                        </div>
                    </form>
                </div>
            </div>            
            <!-- CREDIT CARD FORM ENDS HERE -->
					
					
				</div>
			</c:forEach>
			<!------ <button type="submit" class="btn btn-primary">MM</button> -------->
		</c:forEach>
		<!------
	        	<c:forEach items="${devices}" var="device">
						<div class="card info-panel" style="width: 18rem;">
							<div class="card-header">
								${device.name}
							</div>
							<div class="card-body">
							    <h5 class="card-title">${device.id}</h5>
							    <p class="card-text">${device.description}</p>
							    <input id="productId" name="devicePk" type="hidden" value="${device.pk}">
								<button type="submit" class="btn btn-primary">NNN</button>
							</div>
						</div>
				</c:forEach>
	        	-------->	        	
	</div>
</div>

<c:forEach items="${places}" var="place">
	<c:forEach items="${place.getDevices()}" var="device">
		<div class="card info-panel" style="width: 18rem;">
			<div class="card-header">${device.name}</div>
			<div class="card-body">
				<h5 class="card-title">${device.id}</h5>
				<p class="card-text">${device.description}</p>
				<input id="productId" name="devicePk" type="hidden"
					value="${device.pk}">
				<button type="submit" class="btn btn-primary">NNN</button>
			</div>
		</div>
	</c:forEach>
	<!------ <button type="submit" class="btn btn-primary">MM</button> -------->
</c:forEach>

<%@ include file="../common/footer.jspf" %>