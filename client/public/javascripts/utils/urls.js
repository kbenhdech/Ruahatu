require.def("utils/urls", ["json-template"], function(jsonTemplate) {

	getURL = function(url, params){
		return jsontemplate.Template(url).expand(params)
	}

	var WS_SITE = "http://localhost:9001";
	
	return {
		"FISH_LIST_JSON" : WS_SITE + "/poissons",
		"ONE_FISH_JSON" : WS_SITE + "/poisson/{id}",
		"MY_FISH_LIST_JSON" : WS_SITE + "/utilisateur/" + sLogin + "/poissons",
		"MY_ONE_FISH_JSON" : WS_SITE + "/utilisateur/" + sLogin + "/poisson/{id}",
		"MY_AQUARIUM_LIST_JSON" : WS_SITE + "/utilisateur/" + sLogin + "/aquariums",
		"MY_ONE_AQUARIUM_JSON" : WS_SITE + "/utilisateur/" + sLogin + "/aquarium/{id}",
		
		"ONE_FISH_HTML" : "/poisson.html?id={id}",
		"MY_ONE_FISH_HTML" : "/my_fish.html?id={id}",
		"MY_ONE_AQUARIUM_HTML" : "/my_aquarium.html?id={id}",
		
		"MY_PROFIL_JSON" : WS_SITE + "/utilisateur/" + sLogin,
		
		"getURL" : getURL
	}

});
