'use strict';

exports.reportsDELETE = function(args, res, next) {
  /**
   * parameters expected in the args:
  **/
  // no response value expected for this operation
  
  
  res.end();
}

exports.reportsGET = function(args, res, next) {
  /**
   * parameters expected in the args:
  **/
  
  
  var examples = {};
  examples['application/json'] = {
  "reports" : [ "aeiou" ]
};
  
  if(Object.keys(examples).length > 0) {
    res.setHeader('Content-Type', 'application/json');
    res.end(JSON.stringify(examples[Object.keys(examples)[0]] || {}, null, 2));
  }
  else {
    res.end();
  }
  
  
}

exports.reportsPOST = function(args, res, next) {
  /**
   * parameters expected in the args:
  * body (Report)
  **/
  
  
  var examples = {};
  examples['application/json'] = {
  "reports" : [ "aeiou" ]
};
  
  if(Object.keys(examples).length > 0) {
    res.setHeader('Content-Type', 'application/json');
    res.end(JSON.stringify(examples[Object.keys(examples)[0]] || {}, null, 2));
  }
  else {
    res.end();
  }
  
  
}

