/**
 * Validates provided money string.
 *
 * @author Vato Maskhulia
 */

(function() {
  'use strict';

  angular.module('freeUniTaxiApp')
    .service('MoneyValidationService', function() {
      return {
      /**
       * Simple check whether provided money is a number.
       * @return {boolean} Depends on input's correctness.
       */
        validate: function(money) {
          return !isNaN(money);
        }
      };
    });
})();
