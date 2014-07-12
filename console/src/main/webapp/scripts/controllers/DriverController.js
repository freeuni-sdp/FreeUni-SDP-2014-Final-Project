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

          $scope.updateDriver = function(event) {
            var element = angular.element(event.srcElement);
            var id = $(element).parent().parent().find('.driverID').val();
            var name = $(element).parent().parent().find('.update_driver_name_input').val();
            var editDriver = undefined;
            $scope.drivers.forEach(function(driver) {
                if (driver.id == id)
                    editDriver = driver;
            });
            editDriver.name = name;
            DriverFactory.update(editDriver).then(function(res) {
                console.log(res);
            });
          };

          $scope.deleteDriver = function(id) {
            DriverFactory.remove(id).then(function() {
               console.log('deleted ' + id);
            });
          };
    });
})();