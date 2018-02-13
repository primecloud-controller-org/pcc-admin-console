var getQueryParameters = function() {
  var parameters = {};

  var array = location.search.slice(1).split("&");
  for (var i = 0; i < array.length; i++) {
    kv = array[i].split("=");
    parameters[kv[0]] = kv[1];
  }

  return parameters;
};
