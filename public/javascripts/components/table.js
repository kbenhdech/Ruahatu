require.def("components/table", [ "utils/ajaxJson", "utils/urls", "plugins/jquery.dataTables", "functional" ], function(AjaxJson, URLS, Datatable, Functional) {

	fishes = function(data, id) {
	    content = "<thead><tr>";
	    content += "<th>" + i18n('poisson.nomCommun') + "</th>";
	    content += "<th>" + i18n('poisson.nomScientifique') + "</th>";
	    content += "<th>" + i18n('poisson.famille') + "</th>";
	    content += "<th>" + i18n('poisson.pays') + "</th>";
	    content += "<th>" + i18n('poisson.informationsComplementaire') + "</th>";
	    content += "</tr></thead><tbody>";
		$(content).appendTo(id);
		
		$.each(data, function(i, poisson){
			content = '<tr>';
    		content += '<td>' + "<a href='" + URLS.getURL(URLS.ONE_FISH_HTML, {"id": poisson.id}) + "'>" + poisson.nomCommun + "</a>" + '</td>';
    		content += '<td>' + poisson.nomScientifique + '</td>';
    		content += '<td>' + poisson.famille + '</td>';
    		content += '<td>' + poisson.pays + '</td>';
    		content += '<td>' + poisson.informationsComplementaire + '</td>';
			content += '</tr>';
    		$(content).appendTo(id);
  		});
		content = "</tbody>";
		$(content).appendTo(id);
		
		$(id).dataTable({
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
		});
	}	
	
	return {
		"fishes" : function (id){
			AjaxJson.getJson(URLS.FISH_LIST_JSON, fishes.rcurry(id));
		}
	}
	
});
