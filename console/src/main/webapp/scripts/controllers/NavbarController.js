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
        test: $('#testButton'),
        orders: $('#ordersButton'),
        location: $('#locationButton'),
        test2: $('#test2Button'),
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
          case '#/test': return buttons.test;
          default: return null;
        }
      }
    });
})();
