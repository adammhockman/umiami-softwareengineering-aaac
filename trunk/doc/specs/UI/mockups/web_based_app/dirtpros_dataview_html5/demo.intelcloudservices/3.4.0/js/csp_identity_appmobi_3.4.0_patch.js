/*
 * Copyright (c) 2012, Intel Corporation. All rights reserved.
 * File revision: 04 October 2012
 * Please see http://software.intel.com/html5/license/samples 
 * and the included README.md file for license terms and conditions.
 */

/* The current version of cloud services javascript SDK does not use the AppMobi 3.4.0 secondary browser.  Instead,
   it assumes the presence of the ChildBrowser plugin from PhoneGap.  This file patches the csp javascript until such time
   as it detects AppMobi
 */
$(document).ready(function () {
  document.addEventListener('appMobi.device.ready', patchCspForAppMobi, false);

  function patchCspForAppMobi() {
    intel.common.isPhoneGapAvailabe = function () { return !AppMobi.isweb && !AppMobi.isxdk; }
    if (!window.plugins) window.plugins = {};
    if (!window.plugins.childBrowser) window.plugins.childBrowser = {};
    window.plugins.childBrowser.showWebPage = function (url, options) {
      AppMobi.device.showRemoteSite(url);
      document.addEventListener('appMobi.device.remote.close', function () {
        if (window.plugins.childBrowser.onClose) {
           window.plugins.childBrowser.onClose();
        }
      }, false);
    };
  };
});

 