/**
 * Initializing main module and setting up the routes.
 * Note that in most cases controllers should be assigned
 * here for views e.g. LocationController.
 *
 * @author Vato Maskhulia
 */

(function() {
  'use strict';

  /**
   * Get main module and set up routing.
   */
  var module = angular.module('freeUniTaxiApp', ['ngRoute']);

  module.config(function($locationProvider, $routeProvider) {
    $routeProvider
      .when('/', {
        redirectTo: '/home'
      }).when('/home', {
        templateUrl: 'partials/home.html'
      }).when('/location', {
        controller: 'LocationController',
        templateUrl: 'partials/location.html'
      }).when('/test', {
        templateUrl: 'partials/test.html'
      })
      .otherwise({ redirectTo: '/' });

    // $locationProvider.html5Mode(true);
    });

}());