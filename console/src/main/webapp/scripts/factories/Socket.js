/**
 *
 * @author Vato Maskhulia
 */

(function() {
  'use strict';

  angular.module('freeUniTaxiApp')
    .factory('socket', function() {
      var config = {
        socket: 'ws://' + 'localhost:8080' + '/socket'
        // socket: 'ws://echo.websocket.org'
      };

//      connect();

//      function connect() {
//        if (!('WebSocket' in window)) {
//          console.log('browser doesn\'t support websockets');
//          return;
//        }
//
//        var ws = new WebSocket(config.socket);
//
//        ws.onopen = function() {
//          console.log('connection established');
//          ws.send('haha');
//        };
//
//        ws.onmessage = function (event) {
//          console.log('onmessage');
//          console.log(event);
//        };
//
//        ws.onclose = function () {
//          console.error('connection closed');
//        };
//
//
//        console.log('hu');
//      }

      return {
      };
    });
})();
