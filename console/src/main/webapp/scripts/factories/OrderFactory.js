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

      var num = 0;
      /**
       * @return {Array} Array of twitter orders.
       */
      function getTwitterOrders() {
        var orders = [{
          name: 'test ' + num++, location: 'Leselidze St'
        }, {
          name: 'test ' + num++, location: 'Budapest Street #13a'
        }];
        console.log('ontwitterorders');
        return orders;
      }

      return {
        getActiveOrders: function() {
          return getActiveOrders();
        },

        getTwitterOrders: function() {
          return getTwitterOrders();
        },

        onOrdersUpdate: function(callback) {
          setInterval(function() {
            callback(getActiveOrders());
          }, 2000);
        },

        onTwitterOrders: function(callback) {
          setInterval(function() {
            callback(getTwitterOrders());
          }, 2000);
        },

        add: function(order) {
          console.log('add', order);
          $http.put('/api/orders', order);
        },

        addWithMultipleDrivers: function(order) {
          console.log('addWithMultipleDrivers', order);
        }
      };
    });
})();
