/**
 *
 * @author Vato Maskhulia
 */

(function() {
  'use strict';

  angular.module('freeUniTaxiApp')
    .controller('OrdersController', function($scope, OrderFactory,
      DriverFactory, OrderValidationService, LocationValidationService,
      MoneyValidationService) {

      function IncorrectInputException() {}

      $scope.activeOrders = [];
      $scope.incomingOrders = [];
      $scope.drivers = [];
      $scope.orderForm = {};

      var operatorInput = {
        info: '',
        location: '',
        destination: '',
        amount: '',
        isIncoming: false
      };

      init();

      function init() {
        OrderFactory.getActiveOrders().then(function(res) {
          $scope.activeOrders = res.data;
        });

        OrderFactory.getIncomingOrders().then(function(res) {
          $scope.incomingOrders = res.data;
        });

        DriverFactory.getDrivers().then(function(res) {
          $scope.drivers = res.data;
          $scope.orderForm.driver = $scope.drivers[0];
        });

        OrderFactory.onOrdersUpdate(function(ordersPromise) {
          ordersPromise.then(function(res) {
            $scope.activeOrders = res.data;
          });
        });

        OrderFactory.onIncomingOrders(function(ordersPromise) {
          ordersPromise.then(function(res) {
            $scope.incomingOrders = res.data;
          });
        });
      }

      $scope.getAvailableDrivers = function() {
        DriverFactory.getAvailableDrivers().then(function(res) {
          $scope.orderForm.availableDrivers = [];

          var availableDrivers = res.data;

          for (var i = 0; i < availableDrivers.length; i++) {
            var driver = availableDrivers[i];
            $scope.orderForm.availableDrivers.push({
              id: driver.id,
              name: driver.name,
              location: driver.location.name,
              checked: true
            });
          }

          console.log($scope.orderForm.availableDrivers);
        });
      };

      $scope.addOrder = function() {
        try {
          var order = getOrder();
          order.driver = { id: $scope.orderForm.driver.id };
          OrderFactory.add(order);
          clearFields();
        } catch(e) {}
      };

      $scope.addOrderWithMultipleDrivers = function() {
        try {
          var order = getOrder(),
              drivers = getCheckedAvailableDrivers();
          OrderFactory.addWithMultipleDrivers({
            order: order,
            drivers: drivers
          });
          clearFields();
        } catch(e) {}
      };

      $scope.toInputForm = function() {
        var form = $scope.orderForm;
        form.info = operatorInput.info;
        form.location = operatorInput.location;
        form.destination = operatorInput.destination;
        form.amount = operatorInput.amount;
        operatorInput.isIncoming = false;
      };

      $scope.toIncomingClientForm = function(order) {
        var form = $scope.orderForm;
        if (!operatorInput.isIncoming) {
          operatorInput.info = form.info;
          operatorInput.location = form.location;
          operatorInput.destination = form.destination;
          operatorInput.amount = form.amount;
          operatorInput.isIncoming = true;
        }
        form.info = order.passenger.info;
        form.location = order.passenger.location.name;
        form.destination = order.destination.name;
        form.amount = order.amount;
        form.id = order.id;
      };

      /**
       * throw error if either name or location is invalid
       * @return {Object} Client with name and location.
       */
      function getOrder() {
        var form = $scope.orderForm;

        if (!OrderValidationService.validatePhone(form.info)) {
          alert('Incorrect phone');
          throw new IncorrectInputException();
        }

        if (!LocationValidationService.validate(form.location)) {
          alert('Incorrect location');
          throw new IncorrectInputException();
        }

        if (!LocationValidationService.validate(form.destination)) {
          alert('Incorrect destination');
          throw new IncorrectInputException();
        }

        if (!MoneyValidationService.validate(form.amount)) {
          alert('Incorrect money amount');
          throw new IncorrectInputException();
        }

        var order = {
          passenger: {
            info: form.info,
            location: {
              name: form.location
            }
          },
          destination: {
            name: form.destination
          },
          amount: form.amount
        };

        if (operatorInput.isIncoming) {
          order.id = form.id;
        }

        return order;
      }

      /**
       * @return {Array} Array of checked available drivers.
       */
      function getCheckedAvailableDrivers() {
        var availableDrivers = $scope.orderForm.availableDrivers,
            drivers = [];
        console.log(availableDrivers);

        for (var i = 0; i < availableDrivers.length; i++) {
          var driver = availableDrivers[i];
          if (driver.checked) {
            drivers.push({ id: driver.id });
          }
        }

        if (drivers.length === 0) {
          alert('Choose at least one driver');
          throw new IncorrectInputException();
        }

        return drivers;
      }

      /**
       * Clears input fields of client's phone and location.
       */
      function clearFields() {
        var form = $scope.orderForm;
        form.info = '';
        form.location = '';
        form.destination = '';
        form.amount = '';
        form.availableDrivers = [];
        operatorInput = {};
      }
    });
})();
