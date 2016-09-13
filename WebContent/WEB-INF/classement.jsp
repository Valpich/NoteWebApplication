<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link type="text/css" rel="stylesheet" href="<c:url value="style.css"/>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Affichage du classement</title>
</head>
<body>
	<div>
		<c:import url="/inc/menu.jsp" />
	</div>
	<br />
	<c:choose>
		<c:when test="${ empty sessionScope.eleves }">
			<p class="erreur">Erreur lors de la récupération des étudiants !</p>
		</c:when>
		<c:otherwise>
			<div class="CSS_Tableau">
				<table>
					<tr>
						<th>Nom</th>
						<th>Moyenne</th>
						<th>Classement</th>
					</tr>
					<c:forEach items="${ sessionScope.eleves }" var="eleve"
						varStatus="boucle">
						<tr class="${boucle.index % 2 == 0 ? 'pair' : 'impair'}">
							<td>
								<c:out value="${ eleve.nom }" />
							</td>
							<td>
								<c:out value="${ eleve.moyenne }" />
							</td>
							<td>
								<c:out value="${ eleve.classement }" />
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</c:otherwise>
	</c:choose>
</body>
</html>