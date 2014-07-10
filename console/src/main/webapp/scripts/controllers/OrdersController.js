/**
 *
 * @author Vato Maskhulia
 */

(function() {
  'use strict';

  angular.module('freeUniTaxiApp')
    .controller('OrdersController', function(
      $scope, OrderFactory, DriverFactory, OrderValidationService,
      LocationValidationService) {

      function IncorrectInputException() {}

      $scope.activeOrders = [];
      $scope.twitterOrders = [];
      $scope.drivers = [];
      $scope.orderForm = {
        destination: {},
        passenger: {
          location: {}
        }
      };

      var operatorInput = { // TODO
        phone: '',
        location: '',
        save: true
      };

      init();

      function init() {
        $scope.twitterOrders = OrderFactory.getTwitterOrders(); // temp

        OrderFactory.getActiveOrders().then(function(res) {
          $scope.activeOrders = res.data;
        });

        // OrderFactory.getTwitterOrders().then(function(orders) {
        //   $scope.twitterOrders = orders;
        // });

        OrderFactory.onOrdersUpdate(function(ordersPromise) {
          ordersPromise.then(function(res) {
       //     console.log('onupdate', res);
            $scope.activeOrders = res.data;
            // $scope.$apply();
          });
        });

        OrderFactory.onTwitterOrders(function(ordersPromise) {
          $scope.twitterOrders = ordersPromise;
          // $scope.$apply();
        });

        DriverFactory.getDrivers().then(function(res) {
          $scope.drivers = res.data;
          $scope.orderForm.driver = $scope.drivers[0];
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
          var order = getOrder();
          order.driver = { id: $scope.orderForm.driver.id };
          OrderFactory.add(order);
          clearFields();
        } catch(e) {}
      };

      $scope.addOrderWithMultipleDrivers = function() {
        // try {
        //   var client = getOrder(),
        //       drivers = [];

        //   var availableDrivers = $scope.orderForm.availableDrivers;

        //   for (var i = 0; i < availableDrivers.length; i++) {
        //     var driver = availableDrivers[i];
        //     if (driver.checked) {
        //       drivers.push(driver.name);
        //     }
        //   }

        //   if (drivers.length === 0) {
        //     alert('Choose at least one driver');
        //     return;
        //   }

        //   OrderFactory.add({client: client, drivers: drivers});
        //   clearFields();
        // } catch(e) {}
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
      function getOrder() {
        var form = $scope.orderForm;

        if (!OrderValidationService.validatePhone(form.passenger.info)) {
          alert('Incorrect phone');
          throw new IncorrectInputException();
        }

        if (!LocationValidationService.validate(form.passenger.location.name)) {
          alert('Incorrect location');
          throw new IncorrectInputException();
        }

        if (!LocationValidationService.validate(form.destination.name)) {
          alert('Incorrect destination');
          throw new IncorrectInputException();
        }

        return {
          passenger: {
            info: form.passenger.info,
            location: {
              name: form.passenger.location.name
            }
          },
          destination: {
            name: form.destination.name
          },
          amount: form.amount
        };
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
