
<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.strategy.list.label.ticker" path="ticker" width="10%"/>
	<acme:list-column code="authenticated.strategy.list.label.name" path="name" width="10%"/>
	<acme:list-column code="authenticated.strategy.list.label.description" path="description" width="50%"/>
	<acme:list-column code="authenticated.strategy.list.label.startMoment" path="startMoment" width="10%"/>
	<acme:list-column code="authenticated.strategy.list.label.endMoment" path="endMoment" width="10%"/>
	<acme:list-column code="authenticated.strategy.list.label.fundraiser" path="fundraiser" width="10%"/>
	<acme:list-hidden path="moreInfo"/>
	<acme:list-hidden path="draftMode"/>
</acme:list>