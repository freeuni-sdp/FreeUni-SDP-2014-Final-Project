/**
 * Validates parameters of order object. Currently only has to validate the
 * client's phone.
 *
 * @author Vato Maskhulia
 */

(function() {
  'use strict';

  angular.module('freeUniTaxiApp')
    .service('OrderValidationService', function() {

      /**
       * Simple check whether provided phone is a non empty string.
       * @return {boolean} depends on input's correctness.
       */
      function validatePhone(phone) {
        if (typeof phone !== 'string') {
          return false;
        }

        if (phone.length === 0) {
          return false;
        }

        return true;
      }

      return {
        validatePhone: validatePhone
      };
    });
})();
