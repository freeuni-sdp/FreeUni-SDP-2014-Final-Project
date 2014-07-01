/**
 * Factory must provide functionality for CRUD operations on driver
 * object.
 *
 * @author Vato Maskhulia
 */

(function() {
  'use strict';

  angular.module('freeUniTaxiApp')
    .factory('DriverFactory', function($q, $http) {

    // var date = new Date('11/4/2012').getTime(),
    //   drivers = [
    //     { name: 'driverone', location: {
    //       name:'Kandelaki Street', last_update: date } },
    //     { name: 'drivertwo', location: {
    //       name: 'Budapest Street #13a', last_update: date } },
    //     { name: 'driverthree', location: {
    //       name: 'Leselidze St', last_update: date } },
    //     { name: 'driverfour', location: {
    //       name: 'Rustaveli, Kostava Str', last_update: date } },
    //     { name: 'driverfive', location: {
    //       name: 'Irakli Abashidze Street', last_update: date } }
    //   ];

      var drivers = [];

      /**
       * Helper function that calls provided promise and
       * notifies client.
       * Because the response object is a list of
       * drivers, formats them (time).
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
       * Cycles through provided list of drivers and formats
       * associated location update timestamps.
       */
      function setLastUpdateFormat(drivers) {
        for (var i = 0; i < drivers.length; i++) {
          var driver = drivers[i];

          var d = new Date(driver.location.last_update),
            date = d.getDate(),
            month = d.getMonth(),
            year = d.getFullYear(),
            hours = d.getHours(),
            minutes = d.getMinutes();

          driver.location.last_update = (hours + ":" + minutes +
            "    " + date + "/" + month + "/" + year);
        }
      }

      return {

        getDrivers: function() {
          var httpPromise = $http.get('/api/drivers');

          return processDriversPromise(httpPromise);
        },

        update: function(driver) {
          var httpPromise = $http.put('/api/drivers/' +
            driver.name, driver);

          return processDriversPromise(httpPromise);
        }

      };
    });

})();