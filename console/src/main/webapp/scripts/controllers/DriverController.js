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
              res.data.forEach(function(driver) {
                  $('.driversT tbody').append('<tr>'+
                      '<td class="nameC" align="middle"><input class="form-control" type="text" spellcheck="false" autocomplete="off" value="'+ driver.name +'" /></td>'+
                      '<td class="incomeC" align="middle"><input class="form-control" type="text" spellcheck="false" autocomplete="off" value="'+ driver.income +'" /></td>'+
                      '<td class="editC" align="center" valign="middle"><button class="btn btn-primary">Edit</button></td>'+
                      '<td class="deleteC" align="center" valign="middle"><button class="btn btn-danger">Delete</button></td>'+
                      '</tr>');
              });
          });

          $scope.addDriver = function() {
            var driver = {available: true, id: '', lastWorkingDate: '', location: {}, locationLastUpdateTime: '', name: $scope.driverForm.name, working: false};
            DriverFactory.add(driver).then(function(res) {
                var driver = res.data;
                console.log(driver);
                $('.driversT tbody').append('<tr>'+
                    '<td class="nameC" align="middle"><input class="form-control" type="text" spellcheck="false" autocomplete="off" value="'+ driver.name +'" /></td>'+
                    '<td class="incomeC" align="middle"><input class="form-control" type="text" spellcheck="false" autocomplete="off" value="'+ driver.income +'" /></td>'+
                    '<td class="editC" align="center" valign="middle"><button class="btn btn-primary">Edit</button></td>'+
                    '<td class="deleteC" align="center" valign="middle"><button class="btn btn-danger">Delete</button></td>'+
                    '</tr>');
                $('.add_driver_input').val('');
            });
          }
    });
})();
