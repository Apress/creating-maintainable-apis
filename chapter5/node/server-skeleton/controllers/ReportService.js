'use strict';

exports.reportsReportReportIdDELETE = function(args, res, next) {
  /**
   * parameters expected in the args:
  * reportId (String)
  **/
  // no response value expected for this operation
  
  
  res.end();
}

exports.reportsReportReportIdGET = function(args, res, next) {
  /**
   * parameters expected in the args:
  * reportId (String)
  **/
  
  
  var examples = {};
  examples['application/json'] = {
  "statusCode" : 123,
  "id" : "aeiou",
  "title" : "aeiou",
  "correlationId" : "aeiou",
  "description" : "aeiou",
  "errorCode" : "aeiou"
};
  
  if(Object.keys(examples).length > 0) {
    res.setHeader('Content-Type', 'application/json');
    res.end(JSON.stringify(examples[Object.keys(examples)[0]] || {}, null, 2));
  }
  else {
    res.end();
  }
  
  
}

exports.reportsReportReportIdPUT = function(args, res, next) {
  /**
   * parameters expected in the args:
  * reportId (String)
  * body (Report)
  **/
  
  
  var examples = {};
  examples['application/json'] = {
  "statusCode" : 123,
  "id" : "aeiou",
  "title" : "aeiou",
  "correlationId" : "aeiou",
  "description" : "aeiou",
  "errorCode" : "aeiou"
};
  
  if(Object.keys(examples).length > 0) {
    res.setHeader('Content-Type', 'application/json');
    res.end(JSON.stringify(examples[Object.keys(examples)[0]] || {}, null, 2));
  }
  else {
    res.end();
  }
  
  
}

