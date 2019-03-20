import axios from 'axios';
import auth from '../auth';

const backend = {
  error: response => {
    console.log("ERROR");
    if (response instanceof Error) {
      console.log("response.message");
      console.log(response.message);
    } else {
      console.log("response.data");
      console.log(response.data);
    }
    console.log("response");
    console.log(response);
    console.log("response.status");
    console.log(response.status);
    console.log("response.code");
    console.log(response.code);
    console.log("response.response.status");
    console.log(response.response.status);
    console.log("response.response.data");
    console.log(response.response.data);
    console.log("response.response.headers");
    console.log(response.response.headers);
  },
  success: response => {
    try {
      console.log(response);
      let result = response.content.toJSON();
      console.log(result);
    } catch (e) {
      console.log("WTF");
      console.log(e);
    }
    console.log("asd");
  }
};

/**
 * Convert JSON obj to serialized form string.
 * @param {JSON} obj The JSON data object to be converted.
 * @returns {string} Converted string.
 */
const JsonToFormSerialization = obj => {
  let res = [];
  for (let key in obj) {
    if (Object.prototype.hasOwnProperty.call(obj, key)) {
      res.push(key + "=" + encodeURIComponent(obj[key]));
    }
  }
  return res.join("&");
};

export default {
  JsonToFormSerialization,

  /**
   * Send data to the server.
   * @param {string} url The url data to be send.
   * @param {string} method HTTP request method.
   * @param {JSON} data  The JSON data object to be converted.
   * @param {function} successCallback Callback when success.
   * @param {function} errorCallback Callback when error.
   * @returns {void}
   */
  // eslint-disable-next-line max-params
  requestToBackend: (url, method, data, successCallback, errorCallback) => {
    console.log(method || 'POST');
    axios({
      method: (method || 'POST'),
      url: process.env.URL + url,
      data: JsonToFormSerialization(data),
      crossDomain: true,
      withCredentials: true
    }).then(
      (successCallback || backend.success),
      response => {
        if (response.response.status == 401) {
          auth.logout();
        }
        (errorCallback || backend.error)(response);
      }
    );
  }
};
