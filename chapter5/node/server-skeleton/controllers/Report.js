'use strict';

var url = require('url');


var Report = require('./ReportService');


module.exports.reportsReportReportIdDELETE = function reportsReportReportIdDELETE (req, res, next) {
  Report.reportsReportReportIdDELETE(req.swagger.params, res, next);
};

module.exports.reportsReportReportIdGET = function reportsReportReportIdGET (req, res, next) {
  Report.reportsReportReportIdGET(req.swagger.params, res, next);
};

module.exports.reportsReportReportIdPUT = function reportsReportReportIdPUT (req, res, next) {
  Report.reportsReportReportIdPUT(req.swagger.params, res, next);
};
