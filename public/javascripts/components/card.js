require.def("components/card", [ "utils/ajaxJson", "utils/urls", "plugins/jquery.dataTables", "functional", "utils/utils"], function(AjaxJson, URLS, Datatable, Functional, Utils) {
	
	row = function (name, value){
		return "<tr><td>" + i18n(name) + ":"  +  "</td><td>" + Utils.emptyIfUndefined(value) + "</td></tr>";
	}
	
	oneFish = function (poisson, id) {
		content = "<h1>" + poisson.nomCommun + " (" + poisson.nomScientifique + ")" + "</h1>";
		content += "<table>";
		content += row('poisson.tailleAdulteMin', poisson.tailleAdulteMin);
		content += row('poisson.tailleAdulteMax', poisson.tailleAdulteMax);
		content += row('poisson.temperatureMin', poisson.temperatureMin);
		content += row('poisson.temperatureMax', poisson.temperatureMax);
		content += row('poisson.aciditeMin', poisson.aciditeMin);
		content += row('poisson.aciditeMax', poisson.aciditeMax);
		content += row('poisson.dureteMin', poisson.dureteMin);
		content += row('poisson.dureteMax', poisson.dureteMax);
		content += row('poisson.pays', poisson.pays);
		content += row('poisson.famille', poisson.famille);
		content += row('poisson.zoneDeVie', poisson.zoneDeVie);
		content += row('poisson.informationsComplementaire', poisson.informationsComplementaire);
		content += "</table>";
		$(content).appendTo(id);
	};
	
	myOneFish = function (myFish, id) {
		content = "<h1>" + myFish.pseudo + "</h1>";
		content += "<table>";
		content += row('myPoisson.pseudo', myFish.pseudo);
		content += row('myPoisson.dateAchat', myFish.dateAchat);
		content += row('myPoisson.dateNaissance', myFish.dateNaissance);
		content += row('myPoisson.commentaire', myFish.commentaire);
		content += row('poisson.nomCommun', myFish.poisson.nomCommun);
		content += row('poisson.nomScientifique', myFish.poisson.nomScientifique);
		content += row('poisson.tailleAdulteMin', myFish.poisson.tailleAdulteMin);
		content += row('poisson.tailleAdulteMax', myFish.poisson.tailleAdulteMax);
		content += row('poisson.temperatureMin', myFish.poisson.temperatureMin);
		content += row('poisson.temperatureMax', myFish.poisson.temperatureMax);
		content += row('poisson.aciditeMin', myFish.poisson.aciditeMin);
		content += row('poisson.aciditeMax', myFish.poisson.aciditeMax);
		content += row('poisson.dureteMin', myFish.poisson.dureteMin);
		content += row('poisson.dureteMax', myFish.poisson.dureteMax);
		content += row('poisson.pays', myFish.poisson.pays);
		content += row('poisson.famille', myFish.poisson.famille);
		content += row('poisson.zoneDeVie', myFish.poisson.zoneDeVie);
		content += row('poisson.informationsComplementaire', myFish.poisson.informationsComplementaire);
		content += "</table>";
		$(content).appendTo(id);
	};	
	
	myOneAquarium = function (myAquarium, id) {
		content = "<h1>" + myAquarium.nom + "</h1>";
		content += "<table>";
		content += row('myAquarium.volumeEau', myAquarium.volumeEau);
		content += row('myAquarium.temperatureEau', myAquarium.temperatureEau);
		content += row('myAquarium.dureteEau', myAquarium.dureteEau);
		content += row('myAquarium.aciditeEau', myAquarium.aciditeEau);
		content += "</table>";
		$(content).appendTo(id);
	};	
	
	myProfil = function (user, id) {
		content = "<h1>" + user.pseudo + "</h1>";
		content += "<table>";
		content += "</table>";
		$(content).appendTo(id).attr("id", "userInformations");
		content = row('user.courriel', user.courriel);
		content += row('user.nomComplet', user.nomComplet);
		$(content).appendTo("userInformations");
		AjaxJson.getJson(URLS.getURL("http://localhost:9001" + user.plus, {}), myProfilMore.rcurry("userInformations"));
	};
	
	myProfilMore = function (userMore, id) {
		content = row('userMore.nombrePoissons', userMore.nombrePoissons);
		$(content).appendTo(id);
	};
	
	return {
		"oneFish" : function (idClass, idFish){
			AjaxJson.getJson(URLS.getURL(URLS.ONE_FISH_JSON, {"id":idFish}), oneFish.rcurry(idClass));
		},
		
		"myOneFish" : function (idClass, idFish){
			AjaxJson.getJson(URLS.getURL(URLS.MY_ONE_FISH_JSON, {"id":idFish}), myOneFish.rcurry(idClass));
		},
		
		"myOneAquarium" : function (idClass, idAquarium){
			AjaxJson.getJson(URLS.getURL(URLS.MY_ONE_AQUARIUM_JSON, {"id":idAquarium}), myOneAquarium.rcurry(idClass));
		},
		
		"myProfil" : function (idClass){
			AjaxJson.getJson(URLS.getURL(URLS.MY_PROFIL_JSON, {}), myProfil.rcurry(idClass));
		}
	}
	
});


