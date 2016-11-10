'use strict';

var request = require('request');

function executeRequest (method, uri, callback, config, body) {
  request(
    prepareRequest.call(this, method, uri, config, body),
    completeRequest.bind(this, callback)
  );
}

function prepareRequest (method, uri, config, body) {
  if (isUndefined(config)) {
    config = {};
  }

  var isJsonBody = typeof(body) === 'object' && !(body instanceof Buffer);

  return {
    method: method,
    uri: uri,
    qs: config.queryParameters,
    headers: config.headers,
    body: body,
    json: isJsonBody
  };
}

function completeRequest (callback, error, response, body) {
  if (error) {
    callback(error, body, response);
  } else if (response.statusCode < 200 || response.statusCode > 299) {
    callback({
      status: response.statusCode,
      message: response.body
    }, body, response);
  } else if (isJsonMimeType(response.headers['content-type'])) {
    handleJson(callback, response, body);
  } else {
    callback(undefined, body, response);
  }
}

function checkPathVariables () {
  var errors = [];
  for (var i = 0; i < arguments.length; i += 2) {
    if (!isNumber(arguments[i]) && !isString(arguments[i]) && !isBoolean(arguments[i])) {
      errors.push(arguments[i + 1]);
    }
  }
  if (errors.length > 0) {
    throw new Error(errors.join(', ') + ' must be defined');
  }
}

function isDefined (value) {
  return !isUndefined(value);
}

function isUndefined (value) {
  return is(value, 'undefined');
}

function isString (value) {
  return is(value, 'string');
}

function isBoolean (value) {
  return is(value, 'boolean');
}

function isNumber (value) {
  return is(value, 'number');
}

function isFunction (value) {
  return is(value, 'function');
}

function isObject (value) {
  return is(value, 'object');
}

function is (value, type) {
  return typeof value === type;
}

function isJsonMimeType (contentType) {
  return /^application\/(.*\\+)?json/.test(contentType);
}

function handleJson (callback, response, body) {
  var error;
  if (isString(body)) {
    try {
      body = JSON.parse(body);
    } catch (e) {
      error = {
        status: 0,
        message: 'Received JSON is invalid'
      };
    }
  }

  callback(error, body, response);
}

function stripTrailingSlash (value) {
  return value.replace(/\/$/i, '');
}

function isEmpty (obj) {
  return Object.keys(obj).length === 0;
}

module.exports = {
  isString: isString,
  isUndefined: isUndefined,
  isDefined: isDefined,
  isFunction: isFunction,
  isEmpty: isEmpty,
  stripTrailingSlash: stripTrailingSlash,
  checkPathVariables: checkPathVariables,
  executeRequest: executeRequest
};