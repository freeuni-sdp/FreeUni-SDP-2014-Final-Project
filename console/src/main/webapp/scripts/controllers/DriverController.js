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
          $scope.add = function() {
              DriverFactory.add({
                  name: $scope.driverForm.name
              }).then(function(res) {
                  console.log(res);
              });
          }
    });
})();