
<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list navigable = "true">
	<acme:list-column code="spokesperson.campaign.list.label.ticker" path="ticker" width="10%"/>
	<acme:list-column code="spokesperson.campaign.list.label.name" path="name" width="25%"/>
	<acme:list-column code="spokesperson.campaign.list.label.description" path="description" width="30%"/>
	<acme:list-column code="spokesperson.campaign.list.label.startMoment" path="startMoment" width="15%"/>
	<acme:list-column code="spokesperson.campaign.list.label.endMoment" path="endMoment" width="15%"/>
	<acme:list-column code="spokesperson.campaign.list.label.draftMode" path="draftMode" width="5%"/>
	<acme:list-hidden path="moreInfo"/>
	<acme:list-hidden path="draftMode"/>
</acme:list>