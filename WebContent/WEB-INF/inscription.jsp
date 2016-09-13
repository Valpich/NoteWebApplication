<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Inscription</title>
<link type="text/css" rel="stylesheet" href="inc/style.css" />
</head>
<body>
	<div>
		<c:import url="/inc/menu.jsp" />
	</div>
	<br />
	<form method="post" action="<c:url value="/CreerUtilisateur"/>">
		<fieldset>
			<legend>Inscription</legend>
			<p>Formulaire d'inscription.</p>

			<label for="login">
				Login
				<span class="requis">*</span>
			</label>
			<input type="text" id="login" name="login"
				value="<c:out value="${utilisateur.login}"/>" size="20"
				maxlength="60" />
			<span class="erreur">${erreurLogin}</span>
			<br />

			<label for="motDePasse">
				Mot de passe
				<span class="requis">*</span>
			</label>
			<input type="password" id="motDePasse" name="motDePasse"
				value="<c:out value=""/>" size="20" maxlength="20" />
			<span class="erreur">${erreurMDP}</span>
			<br />
			<label for="confirmation">
				Confirmation
				<span class="requis">*</span>
			</label>
			<input type="password" id="confirmation" name="confirmation" value=""
				size="20" maxlength="20" />
			<span class="erreur">${erreurMDP}</span>
			<br />
			<label for="admin">
				Mot de passe admin
				<span class="requis">*</span>
			</label>
			<input type="password" id="admin" name="admin"
				value="<c:out value=""/>" size="20" maxlength="20" />
			<span class="erreur">${erreurADMIN}</span>
			<br />
			<input type="submit" value="Creer l'utilisateur" class="sansLabel" />
			<br />
		</fieldset>
	</form>
</body>
</html>