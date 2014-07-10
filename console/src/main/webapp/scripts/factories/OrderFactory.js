/**
 * Factory must provide functionality of CRUD operations on order object.
 *
 * @author Vato Maskhulia
 */

(function() {
  'use strict';

  angular.module('freeUniTaxiApp')
    .factory('OrderFactory', function($q, $http) {

      function addOrder(url, order) {
        var httpPromise = $http.post(url, order);
        var deferred = $q.defer();

        httpPromise.then(function(res) {
          deferred.resolve(res);
        }, function(err) {
          console.error(err);
        });

        return deferred.promise;
      }

      var amount = 0;
      /**
       * @return {Array} Array of active order objects.
       */
      function getActiveOrders() {
        var orders = [];
        for (var i = 0; i < 20; i++) {
          orders.push({
            amount: amount++,
            destination: {
              name: 'location'
            },
            driver: {
              name: 'drivertwo'
            },
            passenger: {
              info: 'passenger 1',
              location: {
              }
            }
          });
        }
        console.log('getActiveOrders');
        return orders;
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
          setInterval(function() {// TODO in one function maybe
            callback(getTwitterOrders());
          }, 2000);
        },

        add: function(order) {
          console.log('add', order);
          return addOrder('/api/orders/tweet', order);
        },

        addWithDriver: function(order) {
          console.log('addWithDriver', order);
          return addOrder('api/orders', order);
        }
      };
    });
})();
