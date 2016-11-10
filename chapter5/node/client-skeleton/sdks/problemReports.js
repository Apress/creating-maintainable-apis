'use strict';

var util = require('util');
var restletUtils = require('../restletUtils');
var securityUtils = require('../securityUtils');

/**
 * @class ProblemReports
 * @param {string} [endpoint] - The API endpoint
 */
function ProblemReports(endpoint) {
  if (restletUtils.isDefined(endpoint) && (!restletUtils.isString(endpoint) || restletUtils.isString(endpoint) && endpoint.length === 0)) {
    throw new Error('endpoint parameter must be a non-empty string.');
  }

  this.globalSecurity = {};
  this.securityConfigurations = {};
  this.endpoint = restletUtils.stripTrailingSlash(endpoint || 'http://localhost:80/rad-demo');
}

/**
 * Sets up the authentication to be performed through API token
 *
 * @method
 * @name ProblemReports#setApiToken
 * @param {string} tokenName - the name of the query parameter or header based on the location parameter.
 * @param {string} tokenValue - the value of the token
 * @param {string} location - the location of the token, either 'HEADER' or 'QUERY'.
 * If undefined it defaults to 'header'.
 */
ProblemReports.prototype.configureGlobalApiToken = function(tokenName, tokenValue, location) {
  if (restletUtils.isUndefined(location)) {
    util.log('No location defined, it defaults to \'HEADER\'');
    location = 'HEADER';
  }

  if (location.toUpperCase() !== 'HEADER' && location.toUpperCase() !== 'QUERY') {
    throw new Error('Unknown location: ' + location);
  }

  this.globalSecurity = {
    type: 'API_KEY',
    placement: location.toUpperCase(),
    name: tokenName,
    token: tokenValue
  };
};

/**
 * Sets up the authentication to be performed through oAuth2 protocol
 * meaning that the Authorization header will contain a Bearer token.
 *
 * @method
 * @param token - the oAuth token to use
 */
ProblemReports.prototype.configureGlobalOAuth2Token = function (token) {
  this.globalSecurity = {
    type: 'OAUTH2',
    token: 'Bearer ' + token
  };
};

/**
 * Sets up the authentication to be performed through basic auth.
 *
 * @method
 * @name ProblemReports#setBasicAuth
 * @param {string} username - the user's username
 * @param {string} key - the user's key or password
 */
ProblemReports.prototype.configureGlobalBasicAuthentication = function(username, key) {
  this.globalSecurity = {
    type: 'BASIC',
    token: 'Basic ' + new Buffer(username + ':' + key).toString('base64')
  };
};


/**
 * Get the list of URLs pointing to individual reports.
 * @method
 * @name ProblemReports#getReports
 * @param {object} config - the configuration object containing the query parameters and additional headers.
 * @param {object} config.headers - headers to use for the request in addition to the default ones.
 * @param {object} config.queryParameters - query parameters to use for the request in addition to the default ones.
 * @param {Function} callback - the callback called after request completion with the following parameters:
 *  - error if any technical error occured or if the response's status does not belong to the 2xx range. In that case the error would have the following structure:
{
  status : 400,
  message : 'The request cannot be fulfilled due to XXX'
}
 *  - body of the response auto-extracted from the response if the status is in the 2xx range.
 *    - Status code : 200 - Each item in the list is a URL toward an individual report. - Payload :
{
  "reports" : [ "sample reports" ]
}
 *  - response the technical (low-level) node response (c.f. https://nodejs.org/api/http.html#http_http_incomingmessage)
 */
ProblemReports.prototype.getReports = function(config, callback) {
  restletUtils.executeRequest.call(this, 'GET',
    this.endpoint + '/reports',
    callback,
    securityUtils.addSecurityConfiguration(config, this.globalSecurity, this.securityConfigurations)
  );
};

/**
 * Creates a new report, and adds it to this collection.
 * @method
 * @name ProblemReports#postReports
 * @param {object} body - the payload; is of type: Report; has the following structure:
{
  "correlationId" : "sample correlationId",
  "description" : "sample description",
  "errorCode" : "sample errorCode",
  "id" : "sample id",
  "statusCode" : 1,
  "title" : "sample title"
}
 * @param {object} config - the configuration object containing the query parameters and additional headers.
 * @param {object} config.headers - headers to use for the request in addition to the default ones.
 * @param {object} config.queryParameters - query parameters to use for the request in addition to the default ones.
 * @param {Function} callback - the callback called after request completion with the following parameters:
 *  - error if any technical error occured or if the response's status does not belong to the 2xx range. In that case the error would have the following structure:
{
  status : 400,
  message : 'The request cannot be fulfilled due to XXX'
}
 *  - body of the response auto-extracted from the response if the status is in the 2xx range.
 *    - Status code : 201 - The updated report list. - Payload :
{
  "reports" : [ "sample reports" ]
}
 *  - response the technical (low-level) node response (c.f. https://nodejs.org/api/http.html#http_http_incomingmessage)
 */
ProblemReports.prototype.postReports = function(body, config, callback) {
  restletUtils.executeRequest.call(this, 'POST',
    this.endpoint + '/reports',
    callback,
    securityUtils.addSecurityConfiguration(config, this.globalSecurity, this.securityConfigurations),
    body
  );
};

/**
 * This action prunes away all registered problem reports.
 * @method
 * @name ProblemReports#deleteReports
 * @param {object} config - the configuration object containing the query parameters and additional headers.
 * @param {object} config.headers - headers to use for the request in addition to the default ones.
 * @param {object} config.queryParameters - query parameters to use for the request in addition to the default ones.
 * @param {Function} callback - the callback called after request completion with the following parameters:
 *  - error if any technical error occured or if the response's status does not belong to the 2xx range. In that case the error would have the following structure:
{
  status : 400,
  message : 'The request cannot be fulfilled due to XXX'
}
 *  - body of the response auto-extracted from the response if the status is in the 2xx range.
 *  - response the technical (low-level) node response (c.f. https://nodejs.org/api/http.html#http_http_incomingmessage)
 */
ProblemReports.prototype.deleteReports = function(config, callback) {
  restletUtils.executeRequest.call(this, 'DELETE',
    this.endpoint + '/reports',
    callback,
    securityUtils.addSecurityConfiguration(config, this.globalSecurity, this.securityConfigurations)
  );
};

/**
 * Retrieves an individual report.
 * @method
 * @name ProblemReports#getReportsReportReportId
 * @param {string} reportId - REQUIRED - The id of the individual report.
 * @param {object} config - the configuration object containing the query parameters and additional headers.
 * @param {object} config.headers - headers to use for the request in addition to the default ones.
 * @param {object} config.queryParameters - query parameters to use for the request in addition to the default ones.
 * @param {Function} callback - the callback called after request completion with the following parameters:
 *  - error if any technical error occured or if the response's status does not belong to the 2xx range. In that case the error would have the following structure:
{
  status : 400,
  message : 'The request cannot be fulfilled due to XXX'
}
 *  - body of the response auto-extracted from the response if the status is in the 2xx range.
 *    - Status code : 200 - Payload :
{
  "correlationId" : "sample correlationId",
  "description" : "sample description",
  "errorCode" : "sample errorCode",
  "id" : "sample id",
  "statusCode" : 1,
  "title" : "sample title"
}
 *  - response the technical (low-level) node response (c.f. https://nodejs.org/api/http.html#http_http_incomingmessage)
 */
ProblemReports.prototype.getReportsReportReportId = function(reportId, config, callback) {
  restletUtils.checkPathVariables(reportId, 'reportId');

  restletUtils.executeRequest.call(this, 'GET',
    this.endpoint + '/reports/report/' + reportId + '',
    callback,
    securityUtils.addSecurityConfiguration(config, this.globalSecurity, this.securityConfigurations)
  );
};

/**
 * Deletes the given report permanenty from the system.
 * @method
 * @name ProblemReports#deleteReportsReportReportId
 * @param {string} reportId - REQUIRED - The id of the individual report.
 * @param {object} config - the configuration object containing the query parameters and additional headers.
 * @param {object} config.headers - headers to use for the request in addition to the default ones.
 * @param {object} config.queryParameters - query parameters to use for the request in addition to the default ones.
 * @param {Function} callback - the callback called after request completion with the following parameters:
 *  - error if any technical error occured or if the response's status does not belong to the 2xx range. In that case the error would have the following structure:
{
  status : 400,
  message : 'The request cannot be fulfilled due to XXX'
}
 *  - body of the response auto-extracted from the response if the status is in the 2xx range.
 *  - response the technical (low-level) node response (c.f. https://nodejs.org/api/http.html#http_http_incomingmessage)
 */
ProblemReports.prototype.deleteReportsReportReportId = function(reportId, config, callback) {
  restletUtils.checkPathVariables(reportId, 'reportId');

  restletUtils.executeRequest.call(this, 'DELETE',
    this.endpoint + '/reports/report/' + reportId + '',
    callback,
    securityUtils.addSecurityConfiguration(config, this.globalSecurity, this.securityConfigurations)
  );
};

/**
 * The given report is updated to reflect the new variables.
 * @method
 * @name ProblemReports#putReportsReportReportId
 * @param {string} reportId - REQUIRED - The id of the individual report.
 * @param {object} body - the payload; is of type: Report; has the following structure:
{
  "correlationId" : "sample correlationId",
  "description" : "sample description",
  "errorCode" : "sample errorCode",
  "id" : "sample id",
  "statusCode" : 1,
  "title" : "sample title"
}
 * @param {object} config - the configuration object containing the query parameters and additional headers.
 * @param {object} config.headers - headers to use for the request in addition to the default ones.
 * @param {object} config.queryParameters - query parameters to use for the request in addition to the default ones.
 * @param {Function} callback - the callback called after request completion with the following parameters:
 *  - error if any technical error occured or if the response's status does not belong to the 2xx range. In that case the error would have the following structure:
{
  status : 400,
  message : 'The request cannot be fulfilled due to XXX'
}
 *  - body of the response auto-extracted from the response if the status is in the 2xx range.
 *    - Status code : 200 - Payload :
{
  "correlationId" : "sample correlationId",
  "description" : "sample description",
  "errorCode" : "sample errorCode",
  "id" : "sample id",
  "statusCode" : 1,
  "title" : "sample title"
}
 *  - response the technical (low-level) node response (c.f. https://nodejs.org/api/http.html#http_http_incomingmessage)
 */
ProblemReports.prototype.putReportsReportReportId = function(reportId, body, config, callback) {
  restletUtils.checkPathVariables(reportId, 'reportId');

  restletUtils.executeRequest.call(this, 'PUT',
    this.endpoint + '/reports/report/' + reportId + '',
    callback,
    securityUtils.addSecurityConfiguration(config, this.globalSecurity, this.securityConfigurations),
    body
  );
};

module.exports = ProblemReports;
