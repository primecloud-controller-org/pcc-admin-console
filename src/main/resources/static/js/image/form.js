(function() {
  var selectors = {
    ImageNo : "#imageBasic input[name=imageNo]",
    ImageName : "#imageBasic input[name=imageName]",
    ImageNameDisp : "#imageBasic input[name=imageNameDisp]",
    PlatformNo : "#imageBasic select[name=platformNo]",
    Os : "#imageBasic input[name=os]",
    OsDisp : "#imageBasic input[name=osDisp]",
    Selectable : "#imageBasic input[name=selectable]",
    ComponentTypeNos : "#imageBasic input[name=componentTypeNos]",
    ZabbixTemplate : "#imageBasic input[name=zabbixTemplate]",
    ZabbixDisabled : "#imageBasic input[name=zabbixDisabled]",
    PuppetDisabled : "#imageBasic input[name=puppetDisabled]",
    ViewOrder : "#imageBasic input[name=viewOrder]",

    AwsImageId : "#imageAws input[name=imageId]",
    AwsKernelId : "#imageAws input[name=kernelId]",
    AwsRamdiskId : "#imageAws input[name=ramdiskId]",
    AwsInstanceTypes : "#imageAws input[name=instanceTypes]",
    AwsDefaultInstanceType : "#imageAws input[name=defaultInstanceType]",
    AwsEbsImage : "#imageAws input[name=ebsImage]",
    AwsRootSize : "#imageAws input[name=rootSize]",

    VmwareTemplateName : "#imageVmware input[name=templateName]",
    VmwareInstanceTypes : "#imageVmware input[name=instanceTypes]",
    VmwareDefaultInstanceType : "#imageVmware input[name=defaultInstanceType]",
    VmwareRootSize : "#imageVmware input[name=rootSize]"
  };

  var changePlatformType = function(platformType) {
    // AWS
    if (platformType == "aws") {
      $("#imageAws").removeClass("hidden");
      $("#imageVmware").addClass("hidden");
    }
    // VMware
    else if (platformType == "vmware") {
      $("#imageAws").addClass("hidden");
      $("#imageVmware").removeClass("hidden");
    }
  };

  $(selectors["PlatformNo"]).change(function() {
    var platformType = $(selectors["PlatformNo"] + " option:selected").data("platform_type");
    changePlatformType(platformType);
  });

  $("#addButton").click(function() {
    submitImage("add");
  });

  $("#editButton").click(function() {
    submitImage("edit");
  });

  var submitImage = function(mode) {
    $("#message span").text("");
    $("#message").hide();

    var data = {
      mode : mode,
      imageNo : $(selectors["ImageNo"]).val(),
      imageName : $(selectors["ImageName"]).val(),
      imageNameDisp : $(selectors["ImageNameDisp"]).val(),
      platformNo : $(selectors["PlatformNo"]).val(),
      os : $(selectors["Os"]).val(),
      osDisp : $(selectors["OsDisp"]).val(),
      selectable : $(selectors["Selectable"]).prop("checked"),
      componentTypeNos : $(selectors["ComponentTypeNos"]).val(),
      zabbixTemplate : $(selectors["ZabbixTemplate"]).val(),
      zabbixDisabled : $(selectors["ZabbixDisabled"]).prop("checked"),
      puppetDisabled : $(selectors["PuppetDisabled"]).prop("checked"),
      viewOrder : $(selectors["ViewOrder"]).val()
    };

    var actionUrl;
    var platformType = $(selectors["PlatformNo"] + " option:selected").data("platform_type");

    // AWS
    if (platformType == "aws") {
      actionUrl = "/rest/image/saveAws";

      data = Object.assign(data, {
        imageId : $(selectors["AwsImageId"]).val(),
        kernelId : $(selectors["AwsKernelId"]).val(),
        ramdiskId : $(selectors["AwsRamdiskId"]).val(),
        instanceTypes : $(selectors["AwsInstanceTypes"]).val(),
        defaultInstanceType : $(selectors["AwsDefaultInstanceType"]).val(),
        ebsImage : $(selectors["AwsEbsImage"]).prop("checked"),
        rootSize : $(selectors["AwsRootSize"]).val()
      });
    }
    // VMware
    else if (platformType == "vmware") {
      actionUrl = "/rest/image/saveVmware";

      data = Object.assign(data, {
        templateName : $(selectors["VmwareTemplateName"]).val(),
        instanceTypes : $(selectors["VmwareInstanceTypes"]).val(),
        defaultInstanceType : $(selectors["VmwareDefaultInstanceType"]).val(),
        rootSize : $(selectors["VmwareRootSize"]).val()
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
        $("#message span").text(response.error.message);
        $("#message").show();
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

      var image = response.result;

      if (mode == "add") {
        location.href = app.contextPath + "/image/show?imageNo=" + image.imageNo + "&message=success_add";
      } else {
        location.href = app.contextPath + "/image/show?imageNo=" + image.imageNo + "&message=success_edit";
      }
    });
  };

  $(document).ready(function() {
    var platformType = $(selectors["PlatformNo"] + " option:selected").data("platform_type");
    changePlatformType(platformType);
  });
}());
