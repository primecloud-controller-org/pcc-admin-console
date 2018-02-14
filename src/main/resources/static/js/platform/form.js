(function() {
  var selectors = {
    PlatformNo : "#platformBasic input[name=platformNo]",
    PlatformName : "#platformBasic input[name=platformName]",
    PlatformNameDisp : "#platformBasic input[name=platformNameDisp]",
    PlatformSimplenameDisp : "#platformBasic input[name=platformSimplenameDisp]",
    Internal : "#platformBasic input[name=internal]",
    Proxy : "#platformBasic input[name=proxy]",
    PlatformType : "#platformBasic select[name=platformType]",
    Selectable : "#platformBasic input[name=selectable]",
    ViewOrder : "#platformBasic input[name=viewOrder]",

    AwsHost : "#platformAws input[name=host]",
    AwsPort : "#platformAws input[name=port]",
    AwsSecure : "#platformAws input[name=secure]",
    AwsEuca : "#platformAws input[name=euca]",
    AwsVpc : "#platformAws input[name=vpc]",
    AwsAvailabilityZone : "#platformAws input[name=availabilityZone]",
    AwsVpcId : "#platformAws input[name=vpcId]",
    AwsSubnetId : "#platformAws input[name=subnetId]",

    VmwareUrl : "#platformVmware input[name=url]",
    VmwareUsername : "#platformVmware input[name=username]",
    VmwarePassword : "#platformVmware input[name=password]",
    VmwareDatacenter : "#platformVmware input[name=datacenter]",
    VmwarePublicNetwork : "#platformVmware input[name=publicNetwork]",
    VmwarePrivateNetwork : "#platformVmware input[name=privateNetwork]",
    VmwareComputeResource : "#platformVmware input[name=computeResource]"
  };

  var changePlatformType = function(platformType) {
    // AWS
    if (platformType == "aws") {
      $("#platformAws").removeClass("hidden");
      $("#platformVmware").addClass("hidden");

      var euca = $(selectors["AwsEuca"]).prop("checked");
      var vpc = $(selectors["AwsVpc"]).prop("checked");
      changeAwsEucaVpc(euca, vpc);
    }
    // VMware
    else if (platformType == "vmware") {
      $("#platformAws").addClass("hidden");
      $("#platformVmware").removeClass("hidden");
    }
  };

  $(selectors["PlatformType"]).change(function() {
    var platformType = $(selectors["PlatformType"]).val();
    changePlatformType(platformType);
  });

  var changeAwsEucaVpc = function(euca, vpc) {
    if (euca) {
      $(selectors["AwsVpc"]).prop("disabled", true);
      $(selectors["AwsAvailabilityZone"]).prop("disabled", true);
      $(selectors["AwsVpcId"]).prop("disabled", true);
      $(selectors["AwsSubnetId"]).prop("disabled", true);
    } else if (vpc) {
      $(selectors["AwsVpc"]).prop("disabled", false);
      $(selectors["AwsAvailabilityZone"]).prop("disabled", true);
      $(selectors["AwsVpcId"]).prop("disabled", false);
      $(selectors["AwsSubnetId"]).prop("disabled", false);
    } else {
      $(selectors["AwsVpc"]).prop("disabled", false);
      $(selectors["AwsAvailabilityZone"]).prop("disabled", false);
      $(selectors["AwsVpcId"]).prop("disabled", true);
      $(selectors["AwsSubnetId"]).prop("disabled", true);
    }
  };

  $(selectors["AwsEuca"]).change(function() {
    var euca = $(selectors["AwsEuca"]).prop("checked");
    var vpc = $(selectors["AwsVpc"]).prop("checked");
    changeAwsEucaVpc(euca, vpc);
  });

  $(selectors["AwsVpc"]).change(function() {
    var euca = $(selectors["AwsEuca"]).prop("checked");
    var vpc = $(selectors["AwsVpc"]).prop("checked");
    changeAwsEucaVpc(euca, vpc);
  });

  $("#addButton").click(function() {
    submitPlatform("add");
  });

  $("#editButton").click(function() {
    submitPlatform("edit");
  });

  var submitPlatform = function(mode) {
    $("#message").text("");
    $("#message").addClass("hidden");

    var data = {
      mode : mode,
      platformNo : $(selectors["PlatformNo"]).val(),
      platformName : $(selectors["PlatformName"]).val(),
      platformNameDisp : $(selectors["PlatformNameDisp"]).val(),
      platformSimplenameDisp : $(selectors["PlatformSimplenameDisp"]).val(),
      internal : $(selectors["Internal"]).prop("checked"),
      proxy : $(selectors["Proxy"]).prop("checked"),
      selectable : $(selectors["Selectable"]).prop("checked"),
      viewOrder : $(selectors["VsiewOrder"]).val()
    };

    var actionUrl;
    var platformType = $(selectors["PlatformType"]).val();

    // AWS
    if (platformType == "aws") {
      actionUrl = "/rest/platform/saveAws";

      data = Object.assign(data, {
        host : $(selectors["AwsHost"]).val(),
        port : $(selectors["AwsPort"]).val(),
        secure : $(selectors["AwsSecure"]).prop("checked"),
        euca : $(selectors["AwsEuca"]).prop("checked"),
        vpc : $(selectors["AwsVpc"]).prop("checked"),
        availabilityZone : $(selectors["AwsAvailabilityZone"]).val(),
        vpcId : $(selectors["AwsVpcId"]).val(),
        subnetId : $(selectors["AwsSubnetId"]).val()
      });
    }
    // VMware
    else if (platformType == "vmware") {
      actionUrl = "/rest/platform/saveVmware";

      data = Object.assign(data, {
        url : $(selectors["VmwareUrl"]).val(),
        username : $(selectors["VmwareUsername"]).val(),
        password : $(selectors["VmwarePassword"]).val(),
        datacenter : $(selectors["VmwareDatacenter"]).val(),
        publicNetwork : $(selectors["VmwarePublicNetwork"]).val(),
        privateNetwork : $(selectors["VmwarePrivateNetwork"]).val(),
        computeResource : $(selectors["VmwareComputeResource"]).val()
      });
    }

    $.ajax({
      url : actionUrl,
      type : "post",
      data : data,
      dataType : "json",
      cache : false
    }).done(function(response) {
      if (response.error) {
        $("#message").text(response.error.message);
        $("#message").removeClass("hidden");
        $("html,body").animate({
          scrollTop : 0
        }, "fast");

        var code = response.error.code;
        for (name in selectors) {
          if (code.endsWith(name)) {
            $(selectors[name]).focus();
          }
        }

        return;
      }

      var platform = response.result;

      if (mode == "add") {
        location.href = app.contextPath + "/platform/show?platformNo=" + platform.platformNo + "&message=success_add";
      } else {
        location.href = app.contextPath + "/platform/show?platformNo=" + platform.platformNo + "&message=success_edit";
      }
    });
  };

  $(document).ready(function() {
    var platformType = $(selectors["PlatformType"]).val();
    changePlatformType(platformType);
  });
}());
