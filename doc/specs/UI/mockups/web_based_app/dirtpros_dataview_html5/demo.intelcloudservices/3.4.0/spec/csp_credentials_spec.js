/*
 * Copyright (c) 2012, Intel Corporation. All rights reserved.
 * File revision: 04 October 2012
 * Please see http://software.intel.com/html5/license/samples 
 * and the included README.md file for license terms and conditions.
 */
describe('Credentials', function () {
  /* Don't distrube the real credentials */
  var originalClientId = csp.credentials.client_id;
  var originalSecretId = csp.credentials.secret_id;

  afterEach(function () {
    csp.credentials.client_id = originalClientId;
    csp.credentials.secret_id = originalSecretId;
  });

  beforeEach(function () {
    csp.credentials.client_id = "someclientid";
    csp.credentials.secret_id = "somesecretid";
    spyOn(bootbox, 'alert');
  });

  it('should alert when the client_id is not set', function () {
    csp.credentials.client_id = null;
    expect(csp.credentials.authCredentials()).toBeNull();
    expect(bootbox.alert).toHaveBeenCalled();
  });

  it('should alert when the secret_id is not set', function () {
    csp.credentials.secret_id = null;
    expect(csp.credentials.authCredentials()).toBeNull();
    expect(bootbox.alert).toHaveBeenCalled();
  });

  it('should alert when the client_id is set to TODO', function () {
    csp.credentials.client_id = "TODO";
    expect(csp.credentials.authCredentials()).toBeNull();
    expect(bootbox.alert).toHaveBeenCalled();
  });

  it('should alert when the secret_id is set to TODO', function () {
    csp.credentials.secret_id = "TODO";
    expect(csp.credentials.authCredentials()).toBeNull();
    expect(bootbox.alert).toHaveBeenCalled();
  });

  it('should not alert when the credentials are set', function () {
    var c = csp.credentials.authCredentials();
    expect(c).not.toBeNull();
    expect(c.client_id).toEqual('someclientid');
    expect(c.secret_id).toEqual('somesecretid');
  });

  it('should merge the scope option into the credentials', function () {
    csp.credentials.scope = "somescope";
    expect(csp.credentials.authCredentials().scope).toEqual("somescope");
  });
});