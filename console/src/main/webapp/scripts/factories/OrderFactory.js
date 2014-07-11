/**
 * Factory must provide functionality of CRUD operations on order object.
 *
 * @author Vato Maskhulia
 */

(function() {
  'use strict';

  angular.module('freeUniTaxiApp')
    .factory('OrderFactory', function($q, $http) {

      function processPromise(promise) {
        var deferred = $q.defer();

        promise.then(function(res) {
          deferred.resolve(res);
        }, function(err) {
          console.error(err);
        });

        return deferred.promise;
      }

      /**
       * @return {Array} Array of active order objects.
       */
      function getActiveOrders() {
        return processPromise($http.get('/api/orders/active'));
      }

      /**
       * @return {Array} Array of incoming orders. In our case coming from
       * twitter.
       */
      function getIncomingOrders() {
        return processPromise($http.get('/api/orders/incoming'));
      }

      return {
        getActiveOrders: function() {
          return getActiveOrders();
        },

        getIncomingOrders: function() {
          return getIncomingOrders();
        },

        onOrdersUpdate: function(callback) {
          setInterval(function() {
            callback(getActiveOrders());
          }, 2000);
        },

        onIncomingOrders: function(callback) {
          setInterval(function() {
            callback(getIncomingOrders());
          }, 2000);
        },

        add: function(order) {
          console.log('add', order);
          $http.put('/api/orders', order);
        },

        addWithMultipleDrivers: function(order) {
          console.log('addWithMultipleDrivers', order);
          $http.put('/api/orders/multiple_drivers', order);
        }
      };
    });
})();
