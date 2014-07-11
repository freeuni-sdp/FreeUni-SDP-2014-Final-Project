/**
 * Controller for drivers' location module. Provides view with list of drivers,
 * their locations and last time when that information was updated.
 * Has functionallity to update this data. When location is updated changes
 * table view.
 *
 * @author Giorgi Cxondia
 */

(function() {
  'use strict';

  angular.module('freeUniTaxiApp')
    .controller('DriverController', function(
      $scope, DriverFactory) {

          $scope.driverForm = {name: ''};
          DriverFactory.getDrivers().then(function(res) {
              $scope.drivers = res.data;
          });

          $scope.addDriver = function() {
            var driver = {available: true, id: '', lastWorkingDate: '', location: {}, locationLastUpdateTime: '', name: $scope.driverForm.name, working: false, income: 0};
            DriverFactory.add(driver).then(function(res) {
                $scope.drivers.push(res.data);
                $('.add_driver_input').val('');
            });
          };

          $scope.updateDriver = function() {

          };
    });
})();
