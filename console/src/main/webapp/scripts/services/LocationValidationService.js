/**
 * This service must validate the correctness of location i.e.
 * check if provided location really exist or is it in the
 * correct format for our app.
 *
 * @author Vato Maskhulia
 */

(function() {
  'use strict';

  angular.module('freeUniTaxiApp')
    .service('LocationValidationService', function() {

      /**
       * Simple check whether provided location
       * is a non empty string.
       */
      function validate(location) {
        if (typeof location !== 'string') {
          return false;
        }

        if (location.length === 0) {
          return false;
        }

        return true;
      }

      return {
        validate: validate
      };

    });

})();