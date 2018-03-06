(function() {
  var createApiCertificateSelectors = {
    UserNo : "#create-api-certificate-modal input[name=userNo]",
    ApiAccessId : "#create-api-certificate-modal input[name=apiAccessId]",
    ApiSecretKey : "#create-api-certificate-modal input[name=apiSecretKey]"
  };

  $("#create-api-certificate-modal-open").on("click", function() {
    $(createApiCertificateSelectors["ApiAccessId"]).val("");
    $(createApiCertificateSelectors["ApiSecretKey"]).val("");

    $("#create-api-certificate-modal-message span").text("")
    $("#create-api-certificate-modal-message").hide();
    $("#create-api-certificate-button").prop("disabled", false);
    $("#create-api-certificate-modal").modal("show");
  });

  $("#create-api-certificate-button").on("click", function() {
    var userNo = $(createApiCertificateSelectors["UserNo"]).val();
    var apiAccessId = $(createApiCertificateSelectors["ApiAccessId"]).val();
    var apiSecretKey = $(createApiCertificateSelectors["ApiSecretKey"]).val();

    $.ajax({
      url : "/rest/apiCertificate/create",
      type : "post",
      data : {
        userNo : userNo,
        apiAccessId : apiAccessId,
        apiSecretKey : apiSecretKey
      },
      dataType : "json",
      cache : false
    }).done(function(response) {
      if (response.error) {
        $("#create-api-certificate-modal-message span").text(response.error.message);
        $("#create-api-certificate-modal-message").show();

        var code = response.error.code;
        for (name in createApiCertificateSelectors) {
          if (code.endsWith(name)) {
            $(createApiCertificateSelectors[name]).focus();
          }
        }

        return;
      }

      location.href = app.contextPath + "/user/show?userNo=" + userNo + "&message=success_create";
    });
  });

  $("#delete-api-certificate-modal-open").on("click", function() {
    $("#delete-api-certificate-modal-message span").text("")
    $("#delete-api-certificate-modal-message").hide();
    $("#delete-api-certificate-button").prop("disabled", false);
    $("#delete-api-certificate-modal").modal("show");
  });

  $("#delete-api-certificate-button").on("click", function() {
    var userNo = $("#delete-api-certificate-modal input[name=userNo]").val();

    $.ajax({
      url : "/rest/apiCertificate/delete",
      type : "post",
      data : {
        userNo : userNo
      },
      dataType : "json",
      cache : false
    }).done(function(response) {
      if (response.error) {
        $("#delete-api-certificate-modal-message span").text(response.error.message);
        $("#delete-api-certificate-modal-message").show();
        return;
      }

      location.href = app.contextPath + "/user/show?userNo=" + userNo + "&message=success_delete";
    });
  });

  $("#enable-api-certificate-modal-open").on("click", function() {
    var userNo = $(this).data("user_no");

    $.ajax({
      url : "/rest/apiCertificate/enable",
      type : "post",
      data : {
        userNo : userNo
      },
      dataType : "json",
      cache : false
    }).done(function(response) {
      if (response.error) {
        $("#error-message span").text(response.error.message);
        $("#error-message").show();
        return;
      }

      location.href = app.contextPath + "/user/show?userNo=" + userNo + "&message=success_enable";
    });
  });

  $("#disable-api-certificate-modal-open").on("click", function() {
    var userNo = $(this).data("user_no");

    $.ajax({
      url : "/rest/apiCertificate/disable",
      type : "post",
      data : {
        userNo : userNo
      },
      dataType : "json",
      cache : false
    }).done(function(response) {
      if (response.error) {
        $("#error-message span").text(response.error.message);
        $("#error-message").show();
        return;
      }

      location.href = app.contextPath + "/user/show?userNo=" + userNo + "&message=success_disable";
    });
  });

}());
