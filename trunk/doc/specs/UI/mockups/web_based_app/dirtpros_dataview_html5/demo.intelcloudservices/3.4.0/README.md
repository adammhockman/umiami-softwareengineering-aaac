This sample demonstrates the use of the Intel Cloud Services Platfrom (CSP) Identity service
to authenticate the user of your mobile application.

The sample application presents a login button that when pressed requests the CSP identity service
authenticate the user.  This authentication process requires the application contain a valid
CSP client id and secret key. To obtain these two values, follow the instructions here:

http://software.intel.com/en-us/articles/intel-cloud-services-platform-beta-getting-started

Specifically, when registering your application, enable the Identity service.  For the callback URN, you may
enter: urn:intel:identity:oauth:oob:async

The application uses the following libraries: jQuery, Twitter Bootstrap and Bootbox.

Unit tests are available for this application.  Click the unit tests button to run the tests.

Intel(R) HTML5 Development Environment Beta
-------------------------------------------
This sample is part of the Intel(R) HTML5 Development Environment tool. 
Please sign up the beta at http://software.intel.com/en-us/html5.
To see the technical detail of the sample, please visit the sample article page 
at http://software.intel.com/en-us/articles/TBD. 

License Information Follows
---------------------------
* index.html
* css/index.css
* js/csp_credentials.js
* js/csp_identity.js
* js/csp_identity_cordova_2.4.0_patch.js
* abstract.txt
* app.json
* config.xml
* README.md
* icon.png
* screenshot.png
* spec/csp_identity_cordova_2.4.0_patch_spec.js
* spec/csp_identity_spec.js

Copyright (c) 2012, Intel Corporation. All rights reserved.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

- Redistributions of source code must retain the above copyright notice, 
  this list of conditions and the following disclaimer.

- Redistributions in binary form must reproduce the above copyright notice, 
  this list of conditions and the following disclaimer in the documentation 
  and/or other materials provided with the distribution.

- Neither the name of Intel Corporation nor the names of its contributors 
  may be used to endorse or promote products derived from this software 
  without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE 
LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE 
GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) 
HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT 
LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT 
OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.


jQuery
------
* vendor/jquery-1.8.2.min.js

Copyright (c) 2012, jQuery Foundation and other contributors.

* source:  http://jquery.com/
* source:  http://jquerymobile.com/
* license:  http://github.com/jquery/jquery/blob/master/MIT-LICENSE.txt


Cordova (PhoneGap) Library
--------------------------
* phonegap.js (cordova.js)
* spec/reporter.js
* spec/helper.js

* source:  http://www.phonegap.com/
* license:  http://www.apache.org/licenses/LICENSE-2.0.html

Jasmine Testing Framework
-------------------------
* spec/lib/jasmine-1.2.0/*

* license: spec/lib/jasmine-1.2.0/MIT.LICENSE

Twitter Bootstrap
-----------------
* vendor/boostrap/*

* source: http://twitter.github.com/bootstrap/
* license: http://www.apache.org/licenses/LICENSE-2.0.html

Bootbox
-------
* vendor/bootbox/*

* source: http://bootboxjs.com/
* license: http://bootboxjs.com/license.txt