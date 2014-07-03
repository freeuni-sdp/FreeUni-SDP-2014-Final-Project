/**
 * Factory must provide functionality of CRUD operations on order object.
 *
 * @author Vato Maskhulia
 */

(function() {
  'use strict';

  angular.module('freeUniTaxiApp')
    .factory('OrderFactory', function($q, $http) {

      var orders = [{
        client: {
          name: '557889900',
          location: 'pekini str'
        },
        driver: 'drivertwo'
      }];
      for (var i = 0; i < 5; i++) {
        orders.push({
        client: {
          name: '557889900',
          location: 'pekini str'
        },
        driver: 'drivertwo'
      });
      }

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

      return {
        // TODO
        onUpdate: function(callback) {
          callback(orders);
        },

        onTwitterOrders: function(callback) {
          var orders = [{
            name: 'test', location: 'Leselidze St',
          }, {
            name: 'test1', location: 'Budapest Street #13a'
          }];
          callback(orders);
        },

        add: function(order) {
          console.log('add', order);

          return addOrder('/api/orders/tweet', order);
        },

        addWithDriver: function(order) {
          console.log('addWithDriver client:', order.client);

          return addOrder('api/orders', order);
        }
      };
    });
})();
