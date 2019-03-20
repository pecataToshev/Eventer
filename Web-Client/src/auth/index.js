const TokenBearer = "TOKEN";

let b64DecodeUnicode = str => {
  return decodeURIComponent(atob(str).split('')
    .map(function(c) {
      return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    })
    .join(''));
};

let parseJwt = token => {
  try {
    return JSON.parse(b64DecodeUnicode((token.split('.')[1]).replace(/-/g, '+').replace(/_/g, '/')));
  } catch (e) {
    console.log(e);
    console.log(token);
    return null;
  }
};

export default {
  login(token) {
    const Vue = window.vueApp;
    localStorage.setItem(TokenBearer,token);
    Vue.$cookie.set(TokenBearer, token, {
      domain: process.env.HOST
    });
    Vue.authenticated = true;
  },
  // To log out, we just need to remove the token
  logout() {
    localStorage.removeItem(TokenBearer);
    window.vueApp.$cookie.delete(TokenBearer);
    window.vueApp.authenticated = false;
  },

  checkAuth() {
    let jwt = localStorage.getItem(TokenBearer);
    let Vue = window.vueApp;
    if (!jwt) {
      if (Vue) {
        jwt = Vue.$cookie.get(TokenBearer);
      }
    }
    let auth = Boolean(jwt);
    if (auth) {
      let token = parseJwt(jwt);
      if (token.exp) {
        auth = ((new Date()) < (new Date(Date(token.exp))));
      }
    }
    if (window.vueApp) {
      window.vueApp.authenticated = auth;
    }
    return auth;
  }
};
