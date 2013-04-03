/*
 * Copyright (c) 2013, Intel Corporation. All rights reserved.
 * File revision: 04 March 2013
 * Please see http://software.intel.com/html5/license/samples 
 * and the included README.md file for license terms and conditions.
 */

/**
  csp_identity.js - manages the signing process - updating the user interface accordingly 

  @requires Bootbox
  @requires jQuery

  The csp.samples.identity object connects the UI to the Intel CSP login process.
 
  The work begins when the document loads at which time, the appMobi.device.ready signal is waited on.
  Once the device is ready, the signin button is watched and when clicked, the authentication process is
  started.  When the authentication process completes, the user's profile (i.e., name and email address)
  are fetched and displayed on the screen.
*/

/* Create the csp.samples.identity namespace, if it does not already exist */
var csp;
if (!csp) csp = {};
else if (typeof csp != "object") throw new Error("csp namespace not available");

if (!csp.samples) csp.samples = {};
else if (typeof csp.samples != "object") throw new Error("csp.samples namespace not available");

if (typeof csp.samples.identity != "undefined") throw new Error("csp.samples.identity namespace already taken");

csp.samples.identity = {
  /** The user's profile - saved after login */
  userProfile: undefined,

  /** @return {Boolean} indicating whether or not the user is signed in */
  isUserSignedIn: function () {
    return !!this.userProfile;
  },

  /** 
   *  Called after the document has been loaded.  Hides the signout button and waits for the device
   *  to become ready before allowing signin
   */
  initialize: function () {
    /* If someone signs in before the device is ready, let them know to be patient. */
    $('#signin').bind('click.beforeready', function (event) {
      bootbox.alert("Whoa there!  The device isn't ready yet.");
    });
    $('#signout').hide();
    $('#loading').hide();

    /* Wait for the device to become ready */
    document.addEventListener("appMobi.device.ready", this.onDeviceReady.bind(this), false);
  },

  /**
   *  When the device is ready, wait for the signin button to start the CSP login process
   */
  onDeviceReady:  function () {
    this.updateUserInfo();
    AppMobi.device.hideSplashScreen();

    var self = this;
    $('#signin').unbind('click.beforeready').bind('click', function (event) {
      var initOptions = csp.credentials.authCredentials();
      if (initOptions) {  
        self.status("Starting basic authentication...", { nobreak: true });    
        $('#loading').show();
        intel.auth.init(initOptions,
                        self.authInitSuccessCallback.bind(self),
                        self.authErrorCallback.bind(self));
      }
    });
  },

  /**
   *  When basic authentication is complete, the application is authenticatied with CSP.
   *  Next, we ask the user to log in - the extended authentication
   */
  authInitSuccessCallback: function (data) {
    try {
      this.status('done');
      this.validateAuthData(data);
     
      this.status('Starting extended authentication...', { nobreak: true });
      loginOptions = {
         provider: "",
         name: "loginWindowName",
         specs: "location=1,status=1,scrollbars=1,width=600,height=800"
      };
      intel.auth.login(loginOptions,
                       this.authLoginSuccessCallback.bind(this),
                       this.authErrorCallback.bind(this));
    } catch (e) {
      $('#loading').hide();
    }
  },

  /**
   * When the user authentication is complete, the user is logged in. 
   * We can now request basic profile information about our user 
   */
  authLoginSuccessCallback: function (data) {
    try {
      this.status('done');
      this.validateAuthData(data);

      this.status("Fetching user profile...", { nobreak: true });   
      intel.profile.getUserProfile(this.authGetUserProfileSuccessCallback.bind(this),
                                   this.authErrorCallback.bind(this));
    } catch (e) {
      $('#loading').hide();
    }
  },

  /**
   * The user's profile was obtained 
   */
  authGetUserProfileSuccessCallback: function (profile) {
    this.status('done');
    $('#loading').hide();

    if (!profile || !profile.basic || !profile.basic.firstName || !profile.basic.lastName) {
      bootbox.alert("intel.profile.getUserProfile returned unexpected data");
      return;
    }
    this.userProfile = profile;
    this.updateUserInfo();

    /* Disable the signin button, and watch for signout */
    $('#signin').hide();
    $('#signout').show().bind('click.signout', function (event) {
      intel.auth.logout(function () {
        $('#signout').unbind('click.signoout').hide();
        $('#signin').show();
        $('#user-information').html('Sign in using the menu');
        $('#status').html('');
      }, function () {
        bootbox.alert("Failed to logout");
      });
    });
  },

  /**
   * Any errors in the authentication process report to here 
   */
  authErrorCallback: function (error) {
    $('#loading').hide();
    this.status("Error: " + error.desc);
  },

  /**
   * Validate any successful auth callback data.  If the data is invalid, display an alert and
   * throw an exception.
   */
  validateAuthData: function (data) {
    if (!data || !data.access_token || (typeof data.access_token.authentication_type == "undefined")) {
      bootbox.alert("intel authentication returned invalid data");
      throw new Exception("invalid authentication response");
    }
  },

  /**
   * Update a div to inform the user of the authentication process 
   */
  status: function (text, options) {
    var brk = (options && options.nobreak) ? "" : "<br />";
    var status = $('#status');
    status.html(status.html() + text + brk);
  },

  /**
   * Update the user information 
   */
  updateUserInfo: function () {
    if (this.isUserSignedIn()) {
      $('#user-information').html('Welcome ' + this.userProfile.basic.firstName + 
        ' ' + this.userProfile.basic.lastName);
    } else {
      $('#user-information').html('Sign in using the menu');
    }
  }
};

/**
 * When the document as loaded, run the sample javascript 
 */
$(document).ready(function () {
  csp.samples.identity.initialize();
});



