(function() {
  $(".alert .close").on("click", function() {
    $(this).parent().fadeOut();

    var search = location.search.replace(/(^\?|&)message=.*?(&|$)/, function(str) {
      if (str.charAt(str.length - 1) == "&") {
        return str.charAt(0);
      } else {
        return "";
      }
    });

    if (search != location.search) {
      history.replaceState(null, null, location.pathname + search);
    }
  });

  $(document).ready(function() {
    var tablesorter = $(".tablesorter");
    if (tablesorter) {
      tablesorter.tablesorter();
    }
  });
}());
