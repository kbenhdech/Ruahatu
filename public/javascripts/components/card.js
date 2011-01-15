require.def("components/card", [ "utils/ajaxJson", "utils/urls", "plugins/jquery.dataTables", "functional" ], function(AjaxJson, URLS, Datatable, Functional) {

	oneFish = function (poisson, id) {
		content = "<h1>" + poisson.nomCommun + " (" + poisson.nomScientifique + ")" + "</h1>";
		content += "<table>";
		content += "<tr><td>" + i18n('poisson.tailleAdulteMin') + ":"  +  "</td><td>" + poisson.tailleAdulteMin + "</td></tr>";
		content += "<tr><td>" + i18n('poisson.tailleAdulteMax') + ":"  +  "</td><td>" + poisson.tailleAdulteMax + "</td></tr>";
		content += "<tr><td>" + i18n('poisson.temperatureMin') + ":"  +  "</td><td>" + poisson.temperatureMin + "</td></tr>";
		content += "<tr><td>" + i18n('poisson.temperatureMax') + ":"  +  "</td><td>" + poisson.temperatureMax + "</td></tr>";
		content += "<tr><td>" + i18n('poisson.aciditeMin') + ":"  +  "</td><td>" + poisson.aciditeMin + "</td></tr>";
		content += "<tr><td>" + i18n('poisson.aciditeMax') + ":"  +  "</td><td>" + poisson.aciditeMax + "</td></tr>";
		content += "<tr><td>" + i18n('poisson.dureteMin') + ":"  +  "</td><td>" + poisson.dureteMin + "</td></tr>";
		content += "<tr><td>" + i18n('poisson.dureteMax') + ":"  +  "</td><td>" + poisson.dureteMax + "</td></tr>";
		content += "<tr><td>" + i18n('poisson.pays') + ":"  +  "</td><td>" + poisson.pays + "</td></tr>";
		content += "<tr><td>" + i18n('poisson.famille') + ":"  +  "</td><td>" + poisson.famille + "</td></tr>";
		content += "<tr><td>" + i18n('poisson.zoneDeVie') + ":"  +  "</td><td>" + poisson.zoneDeVie + "</td></tr>";
		content += "<tr><td>" + i18n('poisson.informationsComplementaire') + ":"  +  "</td><td>" + poisson.informationsComplementaire + "</td></tr>";
		content += "</table>";
		$(content).appendTo(id);
	}	
	
	return {
		"oneFish" : function (idClass, idFish){
			AjaxJson.getJson(URLS.getURL(URLS.ONE_FISH_JSON, {"id":idFish}), oneFish.rcurry(idClass));
		}
	}
	
});


