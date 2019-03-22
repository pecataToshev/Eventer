<template>
  <div id="modal-login">
    <mdb-container class="login-form">
      <mdb-nav-item waves-fixed @click.native="show=true">{{ 'login.' | translate}}</mdb-nav-item>

      <mdb-modal tag="form" v-if="show" @close="show=false" class="needs-validation" novalidate @submit.native="checkForm">
        <mdb-modal-header class="text-center">
          <mdb-modal-title tag="h4" bold class="w-100">{{ 'login.' | translate}}</mdb-modal-title>
        </mdb-modal-header>
        <mdb-modal-body class="mx-3 grey-text">
          <div class="red-text" v-show="invalidCredentials">{{ 'login.invalidCredentials' | translate}}</div>
          <mdb-input :label="$t('login.username')" v-model="credentials.username" icon="envelope" type="text" class="mb-2" required>
            <div class="invalid-feedback">{{ 'login.error.emptyUsername' | translate}}</div>
          </mdb-input>
          <mdb-input :label="$t('login.password')" v-model="credentials.password" icon="lock" type="password" class="mb-2" required>
            <div class="invalid-feedback">{{ 'login.error.emptyPassword' | translate}}</div>
          </mdb-input>
          <div class="row d-flex justify-content-center">
          <mdb-btn gradient="aqua">
            <span v-show="loading" class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
            {{ 'login.' | translate}}
          </mdb-btn>
          </div>
        </mdb-modal-body>
        <!--<mdb-modal-footer center>
          <mdb-btn gradient="aqua">
            <span v-show="loading" class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
            {{ 'login.' | translate}}
          </mdb-btn>
          <p>
            {{'register.noRegistration' | translate}}
            <a @click="openRegister">{{'register.invite' | translate}}</a>
          </p>
        </mdb-modal-footer>-->
        <mdb-modal-footer class="mx-5 pt-3 mb-1" center>
          <p class="font-small grey-text d-flex justify-content-end">
            {{'register.noRegistration' | translate}}
            <a @click="openRegister" class="blue-text ml-1">{{'register.invite' | translate}}</a>
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
        show: false,
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
      open() {
        this.show = true;
      },
      openRegister(){

        /*console.log(window.vueApp.$refs);//.register.open();
        console.log((this.$parent));
        console.log((this.$parent.$parent));
        console.log((this.$parent.$parent.$parent));
        console.log((this.$parent.$parent.$parent.$parent));*/
        this.show = false;
        this.$parent.$parent.$parent.$parent.$refs.register.open();
      }
    }

  }
</script>
