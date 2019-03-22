import 'bootstrap-css-only/css/bootstrap.min.css';
import 'mdbvue/build/css/mdb.css';
import "@fortawesome/fontawesome-free/css/all.min.css";
import "flag-icon-css/css/flag-icon.min.css";
import Vue from 'vue';

import router from './router';
import auth from './auth';

import VueCookie from 'vue-cookie';
Vue.use(VueCookie);

// region Components
import App from './App';
import Navigation from './components/Nav';
import myFooter from './components/Footer';
// endregion

Vue.config.productionTip = false;

/* eslint-disable no-new */
const app = new Vue({
  el: '#mainApp',
  router,
  data: {
    authenticated: auth.checkAuth()
  },
  components: {
    App,
    Navigation,
    myFooter
  },
  methods: {
    formatLink: router.options.methods.formatCustomLink
  }
});
window.vueApp = app;

// set beforeEach function for router
router.beforeEach(router.options.methods.beforeEach);
// set page title
router.onReady(() => {
  let page = router.history.current;
  // eslint-disable-next-line no-empty-function
  router.options.methods.beforeEach(page, page, () => { });
});
