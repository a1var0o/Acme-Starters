<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.sponsorship.list.label.ticker" path="ticker" width="40%"/>
	<acme:list-column code="any.sponsorship.list.label.name" path="name" width="60%"/>
</acme:list>
