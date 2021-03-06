/**
 * Controller for drivers' location module. Provides view with list of drivers,
 * their locations and last time when that information was updated.
 * Has functionality to update this data. When location is updated changes
 * table view.
 *
 * @author Vato Maskhulia
 */

(function() {
  'use strict';

  angular.module('freeUniTaxiApp')
    .controller('LocationController', function(
      $scope, DriverFactory, LocationValidationService) {

      $scope.drivers = [];
      $scope.locationForm = {};

      init();

      function init() {
        DriverFactory.getDrivers().then(function(res) {
          $scope.drivers = res.data;
          $scope.locationForm.selectedDriver = $scope.drivers[0];
        });
      }

      function getDriverId(name) {
        var drivers = $scope.drivers;
        for (var i = 0; i < drivers.length; i++) {
            var driver = drivers[i];
            if (driver.name === name) {
                return driver.id;
            }
        }
        return -1;
      }

      $scope.updateLocation = function() {
        // return if driver list is empty
        if (!$scope.locationForm.selectedDriver) {
          return;
        }

        var driver = $scope.locationForm.selectedDriver.name,
            location = $scope.locationForm.location;

        if (!LocationValidationService.validate(location)) {
          alert("Incorrent Location!");
          return;
        }

        DriverFactory.updateLocation(getDriverId(driver), {
            name: location
        }).then(function(res) {
          $scope.drivers = res.data;
          $scope.locationForm.selectedDriver = $scope.drivers[0]; // TODO
        });
      };
    });
})();
