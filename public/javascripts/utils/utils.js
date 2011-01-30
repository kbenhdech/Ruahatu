require.def("utils/utils", [], function() {

	emptyIfUndefined = function (val){
		return (val||'');
	};
	
	return {
		"emptyIfUndefined" : emptyIfUndefined
	}

});
