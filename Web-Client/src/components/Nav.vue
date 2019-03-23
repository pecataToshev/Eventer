<template>
  <!--Main Navigation-->
  <div>
    <!--Navbar-->
    <mdb-navbar dark class="aqua-gradient" position="top">
      <!--<mdb-navbar-brand href="#">Your Logo</mdb-navbar-brand>-->
      <mdb-navbar-toggler>
        <mdb-navbar-nav>
          <template v-if="$root.authenticated">
            <mdb-nav-item waves-fixed>
              <router-link :to="$root.formatLink('')">{{ 'nav.home' | translate }}</router-link>
            </mdb-nav-item>
            <mdb-nav-item waves-fixed>
              <router-link :to="$root.formatLink('eventList')">{{'nav.events' | translate}}</router-link>
            </mdb-nav-item>
          </template>
          <template v-else>
            <!-- Home -->
            <mdb-nav-item waves-fixed>
              <router-link :to="$root.formatLink('')">{{ 'nav.home' | translate }}</router-link>
            </mdb-nav-item>
            <!--/ Home -->
            <!-- Login -->
            <login ref="login"></login>
            <!--/ Login -->
            <!-- Register -->
            <register ref="register"></register>
            <!--/ Register -->
          </template>
        </mdb-navbar-nav>
        <mdb-navbar-nav right>
          <!-- Dropdown -->
          <mdb-dropdown tag="li" class="nav-item">
            <mdb-dropdown-toggle tag="a" navLink slot="toggle" waves-fixed>
              <i class="fas fa-language"></i>
            </mdb-dropdown-toggle>
            <mdb-dropdown-menu v-show="active[0]" right>
              <template v-for="lang in languages">
                <mdb-dropdown-item tag="a" :active='($i18n.locale() == lang.code)' :code="lang.code" @click.native="changeLanguage">
                  <span class="flag-icon" :class="'flag-icon-' + lang.flag"></span>
                  {{ lang.code.toUpperCase() }}
                </mdb-dropdown-item>
              </template>
            </mdb-dropdown-menu>
          </mdb-dropdown>
        </mdb-navbar-nav>
      </mdb-navbar-toggler>
    </mdb-navbar>
  </div>
  <!--/Main Navigation-->
</template>



<script>

import { mdbNavbar, mdbNavItem, mdbNavbarNav, mdbNavbarToggler, mdbDropdown, mdbDropdownItem, mdbDropdownMenu,
  mdbDropdownToggle, mdbNavbarBrand, mdbBtn } from 'mdbvue';
import Login from './ModalLogin';
import Register from './ModalRegister';

import { languages } from "../router";
import router from "../router";
import Home from "./Home";

let langs = [];
for(let langInd in languages) {
  let lang = languages[langInd];
  langs.push({
    flag: (lang == "en" ? "us" : lang),
    code: lang
  })
}

export default {
  name: 'Navigation',
  components: {
    Home,
    mdbNavbar,
    mdbNavItem,
    mdbNavbarNav,
    mdbNavbarToggler,
    mdbDropdown,
    mdbDropdownItem,
    mdbDropdownMenu,
    mdbDropdownToggle,
    mdbNavbarBrand,
    mdbBtn,
    Login,
    Register
  },
  data() {
    return {
      active: {
        0: true
      },
      languages: langs
    };
  },
  methods: {
    changeLanguage: (event) => {
      const separator = '/';
      let newLang = event.target.getAttribute("code");
      let path = router.history.current.path.substr(1) + separator;
      router.push(separator + newLang + path.substr(path.indexOf(separator)));
    }
  }
};
</script>

<style>
  .navbar .dropdown-menu a:hover {
    color: inherit !important;
  }

  .navbar .nav-link > a:not(:hover) {
    color: white;
  }

  .login-form form .md-form input {
    margin-left: 2.5rem;
    color: #424242 !important; /*grey darken-3*/
  }
</style>
