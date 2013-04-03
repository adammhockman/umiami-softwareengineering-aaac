/*
 * Copyright (c) 2012, Intel Corporation. All rights reserved.
 * File revision: 04 October 2012
 * Please see http://software.intel.com/html5/license/samples 
 * and the included README.md file for license terms and conditions.
 */
describe('CSP Identity sample', function () {
  /* Don't distrube the real credentials */
  var originalClientId = csp.credentials.client_id;
  var originalSecretId = csp.credentials.secret_id;

  afterEach(function () {
    csp.credentials.client_id = originalClientId;
    csp.credentials.secret_id = originalSecretId;
  });

  beforeEach(function () {
    spyOn(bootbox, 'alert');
    spyOn(csp.samples.identity, 'status');
    csp.credentials.client_id = "someclientid";
    csp.credentials.secret_id = "somesecretid";
  });

  it('should report the user is not signed in', function () {
    expect(csp.samples.identity.isUserSignedIn()).toBeFalsy();
  });

  it('should call intel.auth.init on signin', function () {
    spyOn(intel.auth, 'init');
    $('#signin').click();
    expect(intel.auth.init.mostRecentCall.args[0])
      .toEqual(csp.credentials.authCredentials("user:details profile:full"));
  });

  it('should display an error when intel.auth.init has an error', function () {
    spyOn(intel.auth, 'init').andCallFake(function (opts, success, fail) {
      fail({ desc: "someiniterror" });
    });

    $('#signin').click();
    expect(csp.samples.identity.status.mostRecentCall.args[0]).toMatch(/someiniterror/);
  });

  describe('with a successful intel.auth.init', function () {
    describe('with invalid data', function () {
      beforeEach(function () {
        spyOn(intel.auth, 'login');
        spyOn(intel.auth, 'init').andCallFake(function (opts, success, fail) {
          success(null);
        });
      });

      it('should alert the user', function () {
        $('#signin').click();
        expect(bootbox.alert).toHaveBeenCalled();       
      });
    });

    describe('with valid data', function () {
      beforeEach(function () {
        spyOn(intel.auth, 'init').andCallFake(function (opts, success, fail) {
          success({ access_token: { authentication_type: 0 } });
        });
      });

      it('should call intel.auth.login', function () {
        spyOn(intel.auth, 'login');
        $('#signin').click();
        expect(intel.auth.login).toHaveBeenCalled();          
      });

      it('should display an error when intel.auth.login has an error', function () {
        spyOn(intel.auth, 'login').andCallFake(function (opts, success, fail) {
          fail({ desc: "someloginerror" });
        });

        $('#signin').click();
        expect(csp.samples.identity.status.mostRecentCall.args[0]).toMatch(/someloginerror/);
      });

      describe('with a successful intel.auth.login', function () {
        describe('with invalid data', function () {
          beforeEach(function () {
            spyOn(intel.profile, 'getUserProfile');
            spyOn(intel.auth, 'login').andCallFake(function (opts, success, fail) {
              success(null);
            });
          });

          it('should alert the user', function () {
            $('#signin').click();
            expect(bootbox.alert).toHaveBeenCalled();       
          });
        });

        describe('with valid data', function () {
          beforeEach(function () {
            spyOn(intel.auth, 'login').andCallFake(function (opts, success, fail) {
              success({ access_token: { authentication_type: 0 } });
            });
          });

          it('should call intel.profile.getUserProfile', function () {
            spyOn(intel.profile, 'getUserProfile');
            $('#signin').click();
            expect(intel.profile.getUserProfile).toHaveBeenCalled();          
          });

          it('should display an error when intel.auth.login has an error', function () {
            spyOn(intel.profile, 'getUserProfile').andCallFake(function (success, fail) {
              fail({ desc: "someprofileerror" });
            });

            $('#signin').click();
            expect(csp.samples.identity.status.mostRecentCall.args[0]).toMatch(/someprofileerror/);
          });

          describe('with a successful intel.profile.getUserProfile', function () {
            describe('with invalid data', function () {
              beforeEach(function () {
                spyOn(intel.profile, 'getUserProfile').andCallFake(function (success, fail) {
                  success(null);
                });
              });

              it('should alert the user', function () {
                $('#signin').click();
                expect(bootbox.alert).toHaveBeenCalled();       
              });
            });

            describe('with valid data', function () {
              beforeEach(function () {
                spyOn(intel.profile, 'getUserProfile').andCallFake(function (success, fail) {
                  success({ basic: { firstName: 'testfirstname', lastName: 'testlastname' } });
                });
              });

              afterEach(function () {
                $('#signout').click();
              })

              it('should change the welcome banner', function () {
                spyOn(csp.samples.identity, 'updateUserInfo');
                $('#signin').click();
                expect(csp.samples.identity.updateUserInfo).toHaveBeenCalled();
              });

              it('should report the user is signed in', function () {
                expect(csp.samples.identity.isUserSignedIn()).toBeTruthy();
              });
            });
          });
        });
      });
    });
  });
});