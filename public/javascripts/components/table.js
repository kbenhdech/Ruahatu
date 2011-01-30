require.def("components/table", [ "utils/ajaxJson", "utils/urls", "plugins/jquery.dataTables", "functional", "utils/utils" ], function(AjaxJson, URLS, Datatable, Functional, Utils) {

	var datatableConfig = {
		"iDisplayLength": 25,
		"oLanguage": {
			"oPaginate": {
				"sFirst": i18n('datatable.sFirst'),
				"sLast": i18n('datatable.sLast'),
				"sNext": i18n('datatable.sNext'),
				"sPrevious": i18n('datatable.sPrevious')
			},
			"sEmptyTable": i18n('datatable.sEmptyTable'),
			"sInfo": i18n('datatable.sInfo'),
			"sInfoEmpty": i18n('datatable.sInfoEmpty'),
			"sInfoFiltered": i18n('datatable.sInfoFiltered'),
			"sInfoPostFix": i18n('datatable.sInfoPostFix'),
			"sLengthMenu":  i18n('datatable.sLengthMenu'),
			"sProcessing": i18n('datatable.sProcessing'),
			"sSearch": i18n('datatable.sSearch'),
			"sZeroRecords": i18n('datatable.sZeroRecords')
		}
	};
	
	header = function(tab) {
		var header = "<thead><tr>";
		$.each(tab, function(i, val){ header += "<th>" + i18n(val) + "</th>"});
		header += "</tr></thead>";
		return header;
	};
	
	body = function(tab, data, id) {
		var body = "<tbody>";    		
		$.each(data, function(i, entity){
			body += '<tr>';
			$.each(tab(entity), function(i, val){ body += '<td>' + Utils.emptyIfUndefined(val) + '</td>'});
    		body += '</tr>';
  		});
		body += "</tbody>";
		return body;
	};
	
	fishes = function(data, id) {
	    var content = header(['poisson.nomCommun', 'poisson.nomScientifique', 'poisson.famille', 'poisson.pays', 'poisson.informationsComplementaire']);
	    fTab = function(poisson){
	    	return ["<a href='" + URLS.getURL(URLS.ONE_FISH_HTML, {"id": poisson.id}) + "'>" + poisson.nomCommun + "</a>", 
             poisson.nomScientifique, poisson.famille, poisson.pays, poisson.informationsComplementaire]	
	    }
		content += body(fTab, data, id);
		$(content).appendTo(id);
		$(id).dataTable(datatableConfig);
	};
	
	myFishes = function(data, id) {
	    var content = header(['myPoisson.pseudo', 'myPoisson.dateAchat', 'myPoisson.dateNaissance', 'myPoisson.commentaire', 
	                          'poisson.nomCommun', 'poisson.nomScientifique', 'poisson.famille', 'poisson.pays', 'poisson.informationsComplementaire']);
	    fTab = function(myPoisson){
	    	return ["<a href='" + URLS.getURL(URLS.MY_ONE_FISH_HTML, {"id": myPoisson.id}) + "'>" + myPoisson.pseudo + "</a>", 
	    	        myPoisson.dateAchat, myPoisson.dateNaissance, myPoisson.commentaire, myPoisson.poisson.nomCommun, myPoisson.poisson.nomScientifique,
	    	        myPoisson.poisson.famille, myPoisson.poisson.pays, myPoisson.poisson.informationsComplementaire]	
	    }
	    content += body(fTab, data, id);
		$(content).appendTo(id);
		$(id).dataTable(datatableConfig);
	};	
	
	return {
		"fishes" : function (id){
			AjaxJson.getJson(URLS.FISH_LIST_JSON, fishes.rcurry(id));
		},
		"myFishes" : function (id){
			AjaxJson.getJson(URLS.MY_FISH_LIST_JSON, myFishes.rcurry(id));
		}
	}
	
});
