
<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list navigable = "true">
	<acme:list-column code="fundraiser.strategy.list.label.ticker" path="ticker" width="15%"/>
	<acme:list-column code="fundraiser.strategy.list.label.name" path="name" width="15%"/>
	<acme:list-column code="fundraiser.strategy.list.label.description" path="description" width="40%"/>
	<acme:list-column code="fundraiser.strategy.list.label.startMoment" path="startMoment" width="12%"/>
	<acme:list-column code="fundraiser.strategy.list.label.endMoment" path="endMoment" width="12%"/>
	<acme:list-column code="fundraiser.strategy.list.label.draftMode" path="draftMode" width="5%"/>
	<acme:list-hidden path="moreInfo"/>
	<acme:list-hidden path="draftMode"/>
</acme:list>