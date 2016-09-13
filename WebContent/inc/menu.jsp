<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<fieldset>
	<div id="menu">
		<c:choose>
			<c:when test="${ empty sessionScope.utilisateur }">
				<p>
					<a href="<c:url value="/ConnecterUtilisateur"/>">Se connecter</a>
				</p>
			</c:when>
			<c:otherwise>
				<p>
					<a href="<c:url value="/CreerUtilisateur"/>">Creer un nouvel
						utilisateur</a>
				</p>
				<p>
					<a href="<c:url value="/DeconnecterUtilisateur"/>">Se
						deconnecter</a>
				</p>
				<p>
					<a href="<c:url value="/AfficherClassement"/>">Afficher le
						classement</a>
				</p>
				<p>
					<a href="<c:url value="/AfficherEleve"/>">Afficher les notes
						d'un eleve</a>
				</p>
			</c:otherwise>
		</c:choose>
	</div>
</fieldset>
