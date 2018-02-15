(function() {
  $("#remove-modal-open").on("click", function() {
    $("#remove-modal-message span").text("");
    $("#remove-modal-message").hide();
    $("#remove-button").prop("disabled", false);
    $("#remove-modal").modal("show");

    var imageNo = $("#remove-modal input[name=imageNo]").val();

    // Check removable
    $.ajax({
      url : "/rest/image/checkRemove",
      type : "get",
      data : {
        imageNo : imageNo
      },
      dataType : "json",
      cache : false
    }).done(function(response) {
      if (response.error) {
        $("#remove-modal-message span").text(response.error.message);
        $("#remove-modal-message").show();
        $("#remove-button").prop("disabled", true);
        return;
      }
    });
  });

  $("#remove-button").on("click", function() {
    var imageNo = $("#remove-modal input[name=imageNo]").val();

    $.ajax({
      url : "/rest/image/remove",
      type : "post",
      data : {
        imageNo : imageNo
      },
      dataType : "json",
      cache : false
    }).done(function(response) {
      if (response.error) {
        $("#remove-modal-message span").text(response.error.message);
        $("#remove-modal-message").show();
        $("#remove-button").prop("disabled", true);
        return;
      }

      $("#remove-modal").modal("hide");
      location.href = app.contextPath + "/image?message=success_remove";
    });
  });
}());
