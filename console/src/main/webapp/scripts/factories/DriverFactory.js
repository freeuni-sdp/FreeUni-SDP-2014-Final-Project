/**
 * Factory must provide functionality of CRUD operations on driver object.
 *
 * @author Vato Maskhulia
 */

(function() {
  'use strict';

  angular.module('freeUniTaxiApp')
    .factory('DriverFactory', function($q, $http) {

      /**
       * Helper function that calls provided promise and notifies client.
       * Because the response object is a list of drivers, formats them (time).
       */
      function processDriversPromise(promise) {
        var deferred = $q.defer();

        promise.then(function(res) {
          setLastUpdateFormat(res.data);
          deferred.resolve(res);
        }, function(err) {
          console.error(err);
        });

        return deferred.promise;
      }

      /**
       * Cycles through provided list of drivers and formats associated
       * location_update timestamps.
       */
      function setLastUpdateFormat(drivers) {
        for (var i = 0; i < drivers.length; i++) {
          var driver = drivers[i];

          var d = new Date(driver.locationLastUpdateTime),
              date = d.getDate(),
              month = d.getMonth(),
              year = d.getFullYear(),
              hours = d.getHours(),
              minutes = d.getMinutes();

          driver.locationLastUpdateTime = (hours + ":" + minutes +
              "    " + date + "/" + month + "/" + year);
        }
      }

      return {
        getDrivers: function() {
          return processDriversPromise($http.get('/api/drivers'));
        },

        getAvailableDrivers: function() {
          return processDriversPromise($http.get('/api/drivers/available'));
        },

        update: function(driver) {
          return processDriversPromise($http.put('/api/drivers/' +
              driver.name, driver));
        },

        updateLocation: function(driverId, location) { // +
          return processDriversPromise($http.put('/api/drivers/' +
              driverId + '/location', location));
        },

        add: function(driver) {
          return processDriversPromise($http.post('/api/drivers/', driver));
        }
      };
    });
})();
