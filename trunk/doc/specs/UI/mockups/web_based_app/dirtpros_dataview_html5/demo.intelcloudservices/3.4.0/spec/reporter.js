$(document).ready(function () {
  var jasmineEnv = jasmine.getEnv();
  jasmineEnv.updateInterval = 1000;

  var htmlReporter = new jasmine.HtmlReporter(null,  document.getElementById('unit-test-results'));
  jasmineEnv.addReporter(htmlReporter);

  jasmineEnv.specFilter = function(spec) {
    return htmlReporter.specFilter(spec);
  };

  $('#unit-tests-heading').bind('click.run-tests', function (event) {
    jasmineEnv.execute();
    $(this).unbind('click.run-tests');
  });
});