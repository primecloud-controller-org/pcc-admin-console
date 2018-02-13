(function() {
  $("#encodeButton").on("click", function() {
    var password = $("input[name=password]").val();

    $.ajax({
      url : "/rest/password/encode",
      type : "post",
      data : {
        password : password
      },
      dataType : "json",
      cache : false
    }).done(function(response) {
      var encodedPassword = response.result;

      $("input[name=encodedPassword]").val(encodedPassword);
    });
  });

}());
