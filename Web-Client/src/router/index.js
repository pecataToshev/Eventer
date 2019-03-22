// region libraries
import Vue from 'vue';

import Router from 'vue-router';
Vue.use(Router);

import Vuex from 'vuex';
Vue.use(Vuex);

const store = new Vuex.Store({
  state: {}
});

import vuexI18n from 'vuex-i18n';
Vue.use(vuexI18n.plugin, store);

const i18n = Vue.i18n;
// endregion

// region languages
import en from '../locales/en.json';
import bg from '../locales/bg.json';
const defaultLanguage = 'bg';

let languages = {bg, en};
let langString = Object.keys(languages);
export { langString as languages };

for (let lang in languages){
  if (Object.prototype.hasOwnProperty.call(languages, lang)) {
    i18n.add(lang, languages[lang]);
  }
}
i18n.set(defaultLanguage);
i18n.fallback(defaultLanguage);
// endregion

// region Components and default routes
import Home from '../components/Home';
import NotFound from '../components/NotFound';
import EventList from '../components/EventList';
import Contacts from '../components/Contacts';
import About from '../components/About';
import Guide from '../components/Guide';

const defaultRoutes = [
  {
    path: '',
    component: Home
  },
  {
    path: 'contacts',
    component: Contacts,
    meta: {
    }
  },
  {
    path: 'about',
    component: About,
    meta: {
    }
  },
  {
    path: 'guide',
    component: Guide,
    meta: {
    }
  },
  {
    path: 'eventList',
    component: EventList,
    meta: {
      auth: true
    }
  }
];
// endregion

const routes = [];
// region construct routes
{
  let langs = i18n.locales();
  langs.forEach(lang => {
    defaultRoutes.forEach(route => {
      let data = {
        path: '/'+lang+'/'+i18n.translate(route.path),
        component: route.component,
        name: lang + '.' + route.path,
        meta: {
          title: i18n.translate('page.' + route.path),
          auth: false,
          admin: false
        }
      };
      if (route.meta) {
        if (route.meta.auth) {
          data.meta.auth = route.meta.auth;
        }
        if (route.meta.admin) {
          data.meta.admin = route.meta.admin;
        }
      }
      routes.push(data);
    });
  });
  routes.push({
    path: "/:lang/*",
    component: NotFound
  });
  routes.push({
    path: "/",
    redirect: to => {
      console.log(to);
      return i18n.locale() + to.path;
    }
  });
}
// endregion

const getLocaleFromUrl = function (url) {
  let localeUrlSegment = url.split('/');
  localeUrlSegment.shift();
  return localeUrlSegment[0];
};

export default new Router({
  routes,
  mode: 'history',
  methods: {
    beforeEach: (to, from, next) => {

      // check if auth and admin

      // region set new page title
      document.title = to.meta.title;
      // endregion
      // region set locale
      let locale = getLocaleFromUrl(to.path);

      // Locale fallback
      if (locale === '') locale = i18n.locale();

      // Set locale
      if (i18n.locale() != locale) i18n.set(locale);
      to.params.locale = locale;
      // endregion

      // Move on the next hook (render component view)
      next();
    },
    formatCustomLink (link) {
      return {
        name: i18n.locale() + '.' + link,
        params: {
          locale: i18n.locale()
        }
      };
    }
  }
});
