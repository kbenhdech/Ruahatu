require.def("utils/ajaxJson", [], function() {

	getJson = function(myUrl, callBack) {
		$.ajax( {
			type : "GET",
			url : myUrl+"?pseudo="+sLogin+"&cle="+sKey,
			dataType : "jsonp",
			success : function(data) {
				callBack(data);
			}
		});
	};

	return {
		"getJson" : getJson
	}

});
