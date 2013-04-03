/*
 * Copyright (c) 2012, Intel Corporation. All rights reserved.
 * File revision: 04 October 2012
 * Please see http://software.intel.com/html5/license/samples 
 * and the included README.md file for license terms and conditions.
 */
describe('CSP identity patch for AppMobi 3.4.0 second browser', function () {
  it('should patch child browser after deviceready', function () {
    expect(intel.common.isPhoneGapAvailabe()).toEqual(!AppMobi.isweb && !AppMobi.isxdk);
    expect(window.plugins).toBeDefined();
    expect(window.plugins.childBrowser).toBeDefined();
    expect(typeof window.plugins.childBrowser.showWebPage).toMatch('function');
  });

  describe('when childBrowser.showWebPage is called', function () {
    beforeEach(function () {
      window.plugins.childBrowser.onClose = jasmine.createSpy();
      spyOn(AppMobi.device, 'showRemoteSite');
      spyOn(document, 'addEventListener');
      window.plugins.childBrowser.showWebPage("someUrl");
    });

    it('should call Appmobi.device.showRemoteSite', function () {
      expect(AppMobi.device.showRemoteSite).toHaveBeenCalledWith("someUrl");
    });

    it('should call addEventListener for the appmobi device remove close event', function () {
      expect(document.addEventListener.mostRecentCall.args[0]).toEqual('appMobi.device.remote.close');
    });

    it('should not call childBrowser.onClose', function () {
      expect(window.plugins.childBrowser.onClose).not.toHaveBeenCalled();
    });

    describe("when the appmobi remote close event is fired", function () {
      beforeEach(function () {
        document.addEventListener.mostRecentCall.args[1]();
      });

      it('should call childBrowser.onClose', function () {
        expect(window.plugins.childBrowser.onClose).toHaveBeenCalled();
      });
    });
  });
});