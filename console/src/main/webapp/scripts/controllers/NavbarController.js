/**
 * Controller for navigating through app. When loaded, retrieves the url and
 * based on it highlights the associated navigation button.
 *
 * @author Vato Maskhulia
 */

(function() {
  'use strict';

  angular.module('freeUniTaxiApp')
    .controller('NavbarController', function($scope, $location) {

      var buttons = {
        home: $('#homeButton'),
        driver: $('#driverButton'),
        orders: $('#ordersButton'),
        location: $('#locationButton')
      };

      for (var i in buttons) {
        buttons[i].removeClass('active');
      }

      getButton().addClass('active');

      /**
       * @return {Element} navbar button associated with current url.
       */
      function getButton() {
        var pathname = window.location.hash;

        switch(pathname) {
          case '#/home': return buttons.home;
          case '#/orders': return buttons.orders;
          case '#/location': return buttons.location;
          case '#/driver': return buttons.driver;
          default: return null;
        }
      }
    });
})();
