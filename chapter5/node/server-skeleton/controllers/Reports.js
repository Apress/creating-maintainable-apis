'use strict';

var url = require('url');


var Reports = require('./ReportsService');


module.exports.reportsDELETE = function reportsDELETE (req, res, next) {
  Reports.reportsDELETE(req.swagger.params, res, next);
};

module.exports.reportsGET = function reportsGET (req, res, next) {
  Reports.reportsGET(req.swagger.params, res, next);
};

module.exports.reportsPOST = function reportsPOST (req, res, next) {
  Reports.reportsPOST(req.swagger.params, res, next);
};
