<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Affichage des notes d'un eleve</title>
<link type="text/css" rel="stylesheet"
	href="<c:url value="/inc/style.css"/>" />
</head>
<body>
	<div>
		<c:import url="/inc/menu.jsp" />
	</div>
	<br/>
	<div>
		<c:choose>
			<c:when test="${ empty sessionScope.eleveComplet }">
				<p class="erreur">Erreur lors de la récupération de l'étudiant !</p>
			</c:when>
			<c:otherwise>
							<p class="succes">Notes de l'étudiant <c:out value="${sessionScope.nomEleve}"/> :)</p>			
				<table>
					<tr>
						<th>Nom UE</th>
						<th>Coefficient UE</th>
						<th>Note UE</th>
					</tr>
					<c:forEach items="${ sessionScope.eleveComplet }" var="noteComplet"
						varStatus="boucle">
						<tr class="${boucle.index % 2 == 0 ? 'pair' : 'impair'}">
							<td>
								<c:out value="${ noteComplet.UE }" />
							</td>
							<td>
								<c:out value="${ noteComplet.coef }" />
							</td>
							<td>
								<c:out value="${ noteComplet.note }" />
							</td>
						</tr>
					</c:forEach>

					<tr>
						<td>
							<c:out value="Somme des coefficients" />
						</td>
						<td>
							<c:out value="${ sessionScope.sommeCoeff}" />
						</td>
						<td>
							<c:out value="" />
						</td>
					</tr>
					<tr>
						<td>
							<c:out value="Moyenne" />
						</td>
						<td>
							<c:out value="${ sessionScope.moyenne }" />
						</td>
						<td>
							<c:out value="" />
						</td>
					</tr>
				</table>
			</c:otherwise>
		</c:choose>
	</div>
		<br/>
	<div>
		<form method="post" action="<c:url value="/AfficherEleve"/>">
			<fieldset>
				<legend>Choix de la personne</legend>
				<c:if test="${ !empty sessionScope.listeEleve }">
					<div id="eleve">
						<select name="listeEleve" id="listeEleve">
							<option value="choixEleve">Choisissez un eleve !</option>
							<c:forEach items="${ sessionScope.listeEleve }" var="mapEleve">
								<option value="${ mapEleve }">${ mapEleve }</option>
							</c:forEach>
						</select>
					</div>
				</c:if>
			</fieldset>
			<input type="submit" value="Valider" />
			<input type="reset" value="Remettre à zéro" />
			<br />
		</form>
	</div>

</body>
</html>