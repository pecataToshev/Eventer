<template>
  <div id="modal-login">
    <mdb-container class="login-form">
      <mdb-nav-item waves-fixed @click.native="login=true">{{ 'login.' | translate}}</mdb-nav-item>

      <mdb-modal tag="form" v-if="login" @close="login=false" class="needs-validation" novalidate @submit.native="checkForm">
        <mdb-modal-header class="text-center">
          <mdb-modal-title tag="h4" bold class="w-100">{{ 'login.' | translate}}</mdb-modal-title>
        </mdb-modal-header>
        <mdb-modal-body class="mx-3 grey-text">
          <div class="red-text" v-show="invalidCredentials">{{ 'login.invalidCredentials' | translate}}</div>
          <mdb-input :label="$t('login.username')" v-model="credentials.username" icon="envelope" type="text" class="mb-5" required>
            <div class="invalid-feedback">{{ 'login.error.emptyUsername' | translate}}</div>
          </mdb-input>
          <mdb-input :label="$t('login.password')" v-model="credentials.password" icon="lock" type="password" required>
            <div class="invalid-feedback">{{ 'login.error.emptyPassword' | translate}}</div>
          </mdb-input>
        </mdb-modal-body>
        <mdb-modal-footer center>
          <mdb-btn gradient="aqua">
            <span v-show="loading" class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
            {{ 'login.' | translate}}
          </mdb-btn>
          <p>
            {{'register.noRegistration' | translate}}
            <a @click="openRegister">{{'register.invite' | translate}}</a>
          </p>

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
    name: "Login",
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
        login:false,
        loading: false,
        invalidCredentials: false,
        credentials: {
          username: '',
          password: ''
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
      },
      openRegister(event){
        console.log(event);
      }
    }

  }
</script>
