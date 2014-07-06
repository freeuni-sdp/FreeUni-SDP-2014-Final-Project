/**
 *
 * @author Vato Maskhulia
 */

(function() {
  'use strict';

  angular.module('freeUniTaxiApp')
    .controller('OrdersController', function(
      $scope, OrderFactory, DriverFactory, OrderValidationService,
      LocationValidationService, socket) {

      function IncorrectInputException() {}

      $scope.orders = [];
      $scope.twitterOrders = [];
      $scope.drivers = [];
      $scope.orderForm = {
        client: {},
        availableDrivers: {}
      };

      var operatorInput = {
        phone: '',
        location: '',
        save: true
      };

      init();

      function init() {
        OrderFactory.onUpdate(function(orders) {
          $scope.orders = orders;
        });

        OrderFactory.onTwitterOrders(function(orders) {
          $scope.twitterOrders = orders;
        });

        DriverFactory.getDrivers().then(function(res) {
          $scope.drivers = res.data;
          $scope.orderForm.selectedDriver = $scope.drivers[0];
        });
      }

      $scope.getAvailableDrivers = function() {
        $scope.orderForm.availableDrivers = [];

        var availableDrivers = DriverFactory.getAvailableDrivers();

        for (var i = 0; i < availableDrivers.length; i++) {
          var driver = availableDrivers[i];
          $scope.orderForm.availableDrivers.push({
            name: driver.name,
            location: driver.location.name,
            checked: true
          });
        }

        console.log($scope.orderForm.availableDrivers);
      };

      $scope.addOrder = function() {
        try {
          var client = getClient(),
              drivers = [];

          var availableDrivers = $scope.orderForm.availableDrivers;

          for (var i = 0; i < availableDrivers.length; i++) {
            var driver = availableDrivers[i];
            if (driver.checked) {
              drivers.push(driver.name);
            }
          }

          if (drivers.length === 0) {
            alert('Choose at least one driver');
            return;
          }

          OrderFactory.add({client: client, drivers: drivers});
          clearFields();
        } catch(e) {}
      };

      $scope.addOrderWithDriver = function() {
        try {
          var form = getClient(),
              driver = $scope.orderForm.selectedDriver.name;
          OrderFactory.addWithDriver({client: {
            name: form.phone,
            location: form.location
          }, driver: driver});
          clearFields();
        } catch(e) {}
      };

      $scope.toInputForm = function() {
        var form = $scope.orderForm.client;
        form.phone = operatorInput.phone;
        form.location = operatorInput.location;
        $scope.orderForm.availableDrivers = [];
        operatorInput.save = true;
      };

      $scope.toTwitterClientForm = function(order) {
        var form = $scope.orderForm.client;
        if (operatorInput.save) {
          operatorInput.phone = form.phone;
          operatorInput.location = form.location;
          operatorInput.save = false;
        }
        form.phone = order.name;
        form.location = order.location;
        $scope.orderForm.availableDrivers = [];
      };

      /**
       * throw error if either name or location is invalid
       * @return {Object} Client with name and location.
       */
      function getClient() {
        var form = $scope.orderForm.client;

        if (!OrderValidationService.validatePhone(form.phone)) {
          alert('Incorrect phone');
          throw new IncorrectInputException();
        }

        if (!LocationValidationService.validate(form.location)) {
          alert('Incorrect location');
          throw new IncorrectInputException();
        }

        return form;
      }

      /**
       * Clears input fields of client's phone and location.
       */
      function clearFields() {
        var form = $scope.orderForm.client;
        form.phone = '';
        form.location = '';
        $scope.orderForm.availableDrivers = [];
        operatorInput = {};
      }
    });
})();
