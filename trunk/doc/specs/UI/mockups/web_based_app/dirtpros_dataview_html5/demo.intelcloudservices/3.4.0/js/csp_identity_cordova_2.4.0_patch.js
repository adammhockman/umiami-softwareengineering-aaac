/*
 * Copyright (c) 2012, Intel Corporation. All rights reserved.
 * File revision: 04 October 2012
 * Please see http://software.intel.com/html5/license/samples 
 * and the included README.md file for license terms and conditions.
 */

/* The current version of cloud services javascript SDK does not use the Cordova 2.4.0 InAppBrowser.  Instead,
   it assumes the presence of the ChildBrowser plugin.  This file patches the csp javascript until such time
   as it detects InAppBrowser
 */
$(document).ready(function () {
  document.addEventListener('deviceready', patchCspIdForInAppBrowser, false);

  function patchCspIdForInAppBrowser() {
    if (!window.plugins) window.plugins = {};
    if (!window.plugins.childBrowser) window.plugins.childBrowser = {};
    window.plugins.childBrowser.showWebPage = function (url, options) {
      var childBrowser = window.open(url, "_blank");
      childBrowser.addEventListener('exit', function() {
        if (window.plugins.childBrowser.onClose) {
           window.plugins.childBrowser.onClose();
        }
      });
    };
  };
});

