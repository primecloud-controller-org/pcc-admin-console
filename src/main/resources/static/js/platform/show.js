(function() {
  $("#remove-modal-open").on("click", function() {
    $("#remove-modal-message span").text("");
    $("#remove-modal-message").hide();
    $("#remove-button").prop("disabled", false);
    $("#remove-modal").modal("show");

    var platformNo = $("#remove-modal input[name=platformNo]").val();

    // Check removable
    $.ajax({
      url : "/rest/platform/checkRemove",
      type : "get",
      data : {
        platformNo : platformNo
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
    var platformNo = $("#remove-modal input[name=platformNo]").val();

    $.ajax({
      url : "/rest/platform/remove",
      type : "post",
      data : {
        platformNo : platformNo
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
      location.href = app.contextPath + "/platform?message=success_remove";
    });
  });

  var selectors = {
    PlatformNo : "#add-vmware-instance-type-modal input[name=platformNo]",
    InstanceTypeName : "#add-vmware-instance-type-modal input[name=instanceTypeName]",
    Cpu : "#add-vmware-instance-type-modal input[name=cpu]",
    Memory : "#add-vmware-instance-type-modal input[name=memory]"
  };

  $("#add-vmware-instance-type-modal-open").on("click", function() {
    $(selectors["InstanceTypeName"]).val("");
    $(selectors["Cpu"]).val("");
    $(selectors["Memory"]).val("");

    $("#add-vmware-instance-type-modal-message span").text("");
    $("#add-vmware-instance-type-modal-message").hide();
    $("#add-vmware-instance-type-modal").modal("show");
  });

  $("#add-vmware-instance-type-button").on("click", function() {
    var platformNo = $(selectors["PlatformNo"]).val();

    $.ajax({
      url : "/rest/platform/addVmwareInstanceType",
      type : "post",
      data : {
        platformNo : platformNo,
        instanceTypeName : $(selectors["InstanceTypeName"]).val(),
        cpu : $(selectors["Cpu"]).val(),
        memory : $(selectors["Memory"]).val()
      },
      dataType : "json",
      cache : false
    }).done(function(response) {
      if (response.error) {
        $("#add-vmware-instance-type-modal-message span").text(response.error.message);
        $("#add-vmware-instance-type-modal-message").show();

        var code = response.error.code;
        for (name in selectors) {
          if (code.endsWith(name)) {
            $(selectors[name]).focus();
          }
        }

        return;
      }

      $("#add-vmware-instance-type-modal").modal("hide");
      location.href = app.contextPath + "/platform/show?platformNo=" + platformNo + "&message=success_add_vmware_instance_type";
    });
  });

  $(".remove-vmware-instance-type-modal-open").on("click", function() {
    var platformNo = $("#remove-vmware-instance-type-modal input[name=platformNo]").val();
    var instanceTypeName = $(this).data("instance_type_name");

    $("#remove-vmware-instance-type-modal input[name=instanceTypeName]").val(instanceTypeName);
    $("#remove-vmware-instance-type-modal input[name=cpu]").val($(this).data("cpu"));
    $("#remove-vmware-instance-type-modal input[name=memory]").val($(this).data("memory"));

    $("#remove-vmware-instance-type-modal-message span").text("")
    $("#remove-vmware-instance-type-modal-message").hide();
    $("#remove-vmware-instance-type-button").prop("disabled", false);
    $("#remove-vmware-instance-type-modal").modal("show");

    // Check removable
    $.ajax({
      url : "/rest/platform/checkRemoveVmareInstanceType",
      type : "get",
      data : {
        platformNo : platformNo,
        instanceTypeName : instanceTypeName
      },
      dataType : "json",
      cache : false
    }).done(function(response) {
      if (response.error) {
        $("#remove-vmware-instance-type-modal-message span").text(response.error.message);
        $("#remove-vmware-instance-type-modal-message").show();
        $("#remove-vmware-instance-type-button").prop("disabled", true);
        return;
      }
    });
  });

  $("#remove-vmware-instance-type-button").on("click", function() {
    var platformNo = $("#remove-vmware-instance-type-modal input[name=platformNo]").val();
    var instanceTypeName = $("#remove-vmware-instance-type-modal input[name=instanceTypeName]").val();

    $.ajax({
      url : "/rest/platform/removeVmareInstanceType",
      type : "post",
      data : {
        platformNo : platformNo,
        instanceTypeName : instanceTypeName
      },
      dataType : "json",
      cache : false
    }).done(function(response) {
      if (response.error) {
        $("#remove-vmware-instance-type-modal-message span").text(response.error.message);
        $("#remove-vmware-instance-type-modal-message").show();
        $("#remove-vmware-instance-type-button").prop("disabled", true);
        return;
      }

      $("#remove-vmware-instance-type-modal").modal("hide");
      location.href = app.contextPath + "/platform/show?platformNo=" + platformNo + "&message=success_remove_vmware_instance_type";
    });
  });

}());
