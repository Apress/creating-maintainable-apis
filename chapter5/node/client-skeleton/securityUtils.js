'use strict';

var utils = require('./restletUtils');

/**
 * Enhances the provided request configuration with the configured
 * security requirements.
 *
 * One might notice that the security requirements are not explicitly defined
 * in the method signature. The reason is that one method might have zero,
 * one or more security requirement(s), so security requirements are recovered
 * dynamically from the `arguments`.
 *
 * The security configuration is defined as follow:
 *  - If no specific security requirements is defined for the method then:
 *    - if a global security is set the call will be authenticated
 *    - if no security is configured then the call will be unauthenticated
 *  - If a specific security requirements is defined for the method then:
 *    - one of them is configured and the first of them is used for the authentication
 *    - none of them is configured and an error is thrown
 *
 * @param {Object} config - a configuration object used inside the requests
 * which can contain among other things the following fields: headers, queryParameters
 * @param {Object} globalSecurity - the globalSecurity configuration as it is can be either
 * undefined or an empty objects
 * @param {Object} securityConfigurations - the user configured securityConfigurations which
 * can be used to authenticate the API calls
 * @param {String...} requirement - the name of the security scheme to support
 */
function addSecurityConfiguration (config, globalSecurity, securityConfigurations) {
  var securityRequirements = Array.prototype.slice.call(arguments, 3);

  if (securityRequirements.length === 0) {
    return enhanceWithGlobalSecurity(config, globalSecurity);
  }

  for (var i = 0; i < securityRequirements.length; i++) {
    var securityRequirementName = securityRequirements[i];
    var securityConfig = securityConfigurations[securityRequirementName];

    if (isNotAuthenticated(securityRequirementName)) {
      return config;
    } else if (utils.isDefined(securityConfig)) {
      return enhanceConfigurationWithSpecificSecurity(config, securityConfig);
    }
  }

  throw new Error('There is no configured security scheme found among: ' + securityRequirements.join(', '));
}

function isNotAuthenticated (securityRequirementName) {
  return securityRequirementName === '_NONE';
}

function enhanceWithGlobalSecurity (config, globalSecurity) {
  if (utils.isEmpty(globalSecurity)) {
    return config;
  }

  return enhanceConfigurationWithSpecificSecurity(config, globalSecurity);
}

function enhanceConfigurationWithSpecificSecurity (config, securityConfig) {
  config = config || {};
  securityConfig = securityConfig || {};

  if (!config.headers) {
    config.headers = {};
  }

  if (!config.params) {
    config.params = {};
  }

  if (securityConfig.type === 'BASIC') {
    config.headers.Authorization = securityConfig.token;
  } else if (securityConfig.type === 'API_KEY' && securityConfig.placement === 'HEADER') {
    config.headers[securityConfig.name] = securityConfig.token;
  } else if (securityConfig.type === 'API_KEY' && securityConfig.placement === 'QUERY') {
    config.queryParameters[securityConfig.name] = securityConfig.token;
  } else if (securityConfig.type === 'OAUTH2') {
    config.headers.Authorization = securityConfig.token;
  } else {
    throw new Error('Cannot update config for unknown scheme');
  }

  return config;
}

module.exports = {
  addSecurityConfiguration: addSecurityConfiguration
};