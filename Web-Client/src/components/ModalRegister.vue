<template>
  <div id="modal-login">
    <mdb-container class="login-form">

      <mdb-modal tag="form" v-if="register" @close="login=false" class="needs-validation" novalidate @submit.native="checkForm">
        <mdb-modal-header class="text-center">
          <mdb-modal-title tag="h4" bold class="w-100">{{ 'register.' | translate}}</mdb-modal-title>
        </mdb-modal-header>
        <mdb-modal-body class="mx-3 grey-text">
          <div class="red-text" v-show="error.takenUsername">{{ 'register.error.takenUsername' | translate}}</div>
          <div class="red-text" v-show="error.takenEmail">{{ 'register.error.takenEmail' | translate}}</div>
          <div class="red-text" v-show="error.invalidPassword">{{ 'register.error.invalidPassword' | translate}}</div>
          <div class="red-text" v-show="error.varyingPasswords">{{ 'register.error.varyingPasswords' | translate}}</div>

          <mdb-input :label="$t('register.username')" v-model="credentials.username" icon="user" type="text" required>
            <div class="invalid-feedback">{{ 'register.error.empty.username' | translate}}</div>
          </mdb-input>
          <mdb-input :label="$t('register.email')" v-model="credentials.email" icon="envelope" type="email" required>
            <div class="invalid-feedback">{{ 'login.error.empty.email' | translate}}</div>
          </mdb-input>
          <mdb-input :label="$t('register.firstName')" v-model="credentials.firstName" icon="lock" type="text" required>
            <div class="invalid-feedback">{{ 'register.error.empty.firstName' | translate}}</div>
          </mdb-input>
          <mdb-input :label="$t('register.lastName')" v-model="credentials.lastName" icon="lock" type="text" required>
            <div class="invalid-feedback">{{ 'register.error.empty.lastName' | translate}}</div>
          </mdb-input>
          <mdb-input :label="$t('register.password')" v-model="credentials.password" icon="lock" type="password" required>
            <div class="invalid-feedback">{{ 'login.error.empty.password' | translate}}</div>
          </mdb-input>
          <mdb-input :label="$t('register.rePassword')" v-model="credentials.rePassword" icon="lock" type="password" class="mb-5" required>
            <div class="invalid-feedback">{{ 'login.error.empty.rePassword' | translate}}</div>
          </mdb-input>

        </mdb-modal-body>
        <mdb-modal-footer center>
          <mdb-btn gradient="aqua">
            <span v-show="loading" class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
            {{ 'register.' | translate}}
          </mdb-btn>
        </mdb-modal-footer>
      </mdb-modal>
    </mdb-container>
  </div>
</template>

<script>
  // import auth from '../auth';
  import { mdbContainer, mdbBtn, mdbModal, mdbModalBody, mdbModalFooter,
    mdbModalHeader, mdbModalTitle, mdbInput, mdbNavItem } from 'mdbvue';
  import request from '../request';
  import auth from '../auth';

  export default {
    name: "Register",
    components: {
      mdbBtn,
      mdbContainer,
      mdbNavItem,
      mdbModal,
      mdbModalBody,
      mdbModalFooter,
      mdbModalHeader,
      mdbModalTitle,
      mdbInput
    },
    data() {
      return {
        register:false,
        loading: false,
        error:{
          takenUsername: false,
          takenEmail: false,
          invalidPassword: false,
          varyingPasswords: false,

        },
        credentials: {
          username: '',
          firstName: '',
          lastName: '',
          password: '',
          rePassword: '',
          email: ''
        }
      };
    },
    methods: {
      checkForm(event) {
        event.preventDefault();
        event.target.classList.add('was-validated');

        let data = this.credentials;
        if(document.getElementById("modal-login").querySelectorAll("form:valid").length > 0) {
          this.loading = true;
          request.requestToBackend(
            "login/Login",
            'POST',
            data,
            response => {
              auth.login(response.data);
              this.loading = false;
              this.invalidCredentials = true;
            },
            response => {
              this.loading = false;
              this.invalidCredentials = true;
            }
          );
        }
      }
    }

  }
</script>

<style scoped>

</style>
