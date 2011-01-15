require.def("utils/urls", ["json-template"], function(jsonTemplate) {

	getURL = function(url, params){
		return jsontemplate.Template(url).expand(params)
	}

	var WS_SITE = "http://localhost:9001";
	
	return {
		"FISH_LIST_JSON" : WS_SITE + "/poissons",
		"ONE_FISH_JSON" : WS_SITE + "/poisson/{id}",
		
		"ONE_FISH_HTML" : "/poisson.html?id={id}",
		
		"getURL" : getURL
	}

});
