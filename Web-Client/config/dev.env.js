'use strict';
const merge = require('webpack-merge');
const prodEnv = require('./prod.env');

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  DEBUG_MODE: true,
  URL: '"http://localhost:8080/Eventer/"',
  HOST: '"localhost:8081"'
});
