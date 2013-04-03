/*
 * Copyright (c) 2013, Intel Corporation. All rights reserved.
 * File revision: 04 March 2013
 * Please see http://software.intel.com/html5/license/samples 
 * and the included README.md file for license terms and conditions.
 */

/**
   csp_credentials.js -- holds the CSP client_id, secret_id, and scope values
   @requires Bootbox
 
   This file contains the CSP application credentails: the client_id and secret_id.
   Both of these values must be filled in for the sample to work. To obtain these credentails, check README.md
   or begin here:

   http://software.intel.com/en-us/articles/intel-cloud-services-platform-beta-getting-started

   It is important to understand the implications of embedding these credentials inside your application.
   You should assume that somone can obtain this source code for your application and hence can
   obtain your client_id and secret_id.  Using these credentials, someone could impersonate this application
   and whatever access it has been created within Intel Cloud Services Platform.  For the identity service,
   this means these credentials can be used, along with a user's password, to obtain the user's email
   address and name.  This same information can be obtained with the user's password and so the security
   concerns are equivalent to knowing the user's password.

   However, if the CSP application has been authorized to access the Commerce service, for example, then these
   credentials could be used to issue refunds and view the entire order history for the application.
   In such situations where access to the client_id and secret_id represent a risk you are unwilling to take, 
   do not embedd the client_id and secret_id directly into the application.  Instead, only use the client_id
   and secret_id from within an authenticated service that you control.
 */

/** Create the csp.credentials namespace, if it does not already exist */
var csp;
if (!csp) csp = {};
else if (typeof csp != "object") throw new Error("csp namespace not available");
if (typeof csp.credentials != "undefined") throw new Error("csp.credentials namespace already taken");

/**
   The credentials holder contains the client_id and secret_id, but also ensures that if these values
   have not been set that a dialog box displays to alert the developer to this oversight.  This file assumes
   bootbox to display the alert.  The properties of this object are public to ease testing.

   To use this file, either replace the TODO strings for client_id and secret_id with the values obtained
   from the CSP application dashboard, or set them in another file such as:

   @example
     csp.credentails.client_id = "SOMECLIENTID";
     csp.credentials.secret_id = "SOMESECRETID";
     csp.credentials.scope = "user:details profile:basic";

   Once the client id, secret id and scope have been set, call authCredentials to format these values
   into the stucture that can be passed into intel.auth.init(), like:

   @example
     var authOpts = csp.credentials.authCredentials();
     if (authOpts) {
       intel.auth.init(authOpts, ...);
     }
 */
csp.credentials = {
  /* Fill in these two values */
  client_id: "TODO",
  secret_id: "TODO",
  scope: "user:details profile:full",

  /** 
   * Format the client id, secret id, and scope for use by intel.auth.init()
   * @return {Object} an object appropriate for the first parameter to intel.auth.init() or null if
   *                  the client id or secret id are not set
   */
  authCredentials: function () {
    if (!this.client_id || !this.secret_id || this.client_id === "TODO" || this.secret_id === "TODO") {
      bootbox.alert("Oops!  Please set the client_id and secret_id for your application in js/csp_credentials.js");
      return null;
    } else {
      return { 
        client_id: this.client_id,
        secret_id: this.secret_id,
        scope: this.scope
      }
    }
  }
};

