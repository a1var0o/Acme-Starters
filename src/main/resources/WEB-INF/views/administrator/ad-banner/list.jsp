<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list navigable = "true">
	<acme:list-column code="administrator.ad-banner.list.label.slogan" path="slogan"/>
	<acme:list-column code="administrator.ad-banner.list.label.targetUrl" path="targetUrl"/>
	<<acme:list-column code="administrator.ad-banner.list.label.pictureUrl" path="pictureUrl"/>
	
</acme:list>

<acme:button code="administrator.ad-banner.list.button.create" action="/administrator/ad-banner/create"/>