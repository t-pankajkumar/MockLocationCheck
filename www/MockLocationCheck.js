var exec = require('cordova/exec');

exports.isMockLocationOn = function (arg0, success, error) {
    exec(success, error, 'MockLocationCheck', 'isMockLocationOn', [arg0]);
};
