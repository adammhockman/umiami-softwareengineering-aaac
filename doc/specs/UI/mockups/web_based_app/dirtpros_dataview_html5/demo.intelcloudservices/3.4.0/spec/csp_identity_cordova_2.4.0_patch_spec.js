/*
 * Copyright (c) 2012, Intel Corporation. All rights reserved.
 * File revision: 04 October 2012
 * Please see http://software.intel.com/html5/license/samples 
 * and the included README.md file for license terms and conditions.
 */
describe('CSP identity patch for Cordova 2.4.0 InAppBrowser', function () {
  function isDeviceReady() {
    return $('#emulate-device-start').is(':hidden');
  };

  it('should not patch child browser until deviceready', function () {
    if (!isDeviceReady()) expect(window.plugins).toBeUndefined();
  });

  describe('when device is ready', function () {
    /* Fire device ready and wait for the patch to work */
    beforeEach(function () {
      runs(function () {
        if (!isDeviceReady()) $('#device-ready-button').click();
      });
  
      waitsFor(function () {
        return isDeviceReady();
      }, 'onDeviceReady should be called once', 500);
    });

    it('should patch child browser after deviceready', function () {
      expect(window.plugins).toBeDefined();
      expect(window.plugins.childBrowser).toBeDefined();
      expect(typeof window.plugins.childBrowser.showWebPage).toMatch('function');
    });

    describe('when childBrowser.showWebPage is called', function () {
      var exitEventSpy = jasmine.createSpy();

      beforeEach(function () {
        window.plugins.childBrowser.onClose = jasmine.createSpy();
        spyOn(window, 'open').andReturn({ addEventListener: exitEventSpy });
        window.plugins.childBrowser.showWebPage("someUrl");
      });

      it('should call window.open', function () {
        expect(window.open).toHaveBeenCalledWith("someUrl", "_blank");
      });

      it('should call addEventListener for the window exit event', function () {
        expect(exitEventSpy.mostRecentCall.args[0]).toEqual('exit');
      });

      it('should not call childBrowser.onClose', function () {
        expect(window.plugins.childBrowser.onClose).not.toHaveBeenCalled();
      });

      describe("when the childBrowser exit event is fired", function () {
        beforeEach(function () {
          exitEventSpy.mostRecentCall.args[1]();
        });

        it('should call childBrowser.onClose', function () {
          expect(window.plugins.childBrowser.onClose).toHaveBeenCalled();
        });
      });
    });
  });
});