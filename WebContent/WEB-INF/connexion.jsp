<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Connexion</title>
<link type="text/css" rel="stylesheet" href="inc/style.css" />
</head>
<body>
	<form method="post" action="<c:url value="/ConnecterUtilisateur"/>">
		<fieldset>
			<legend>Connexion</legend>
			<p>Formulaire de connexion.</p>

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
			<input type="password" id="motDePasse" name="motDePasse" value=""
				size="20" maxlength="20" />
			<span class="erreur">${erreurMDP}</span>
			<br />

			<input type="submit" value="Connexion !" class="sansLabel" />
			<br />


		</fieldset>
	</form>
</body>
</html>