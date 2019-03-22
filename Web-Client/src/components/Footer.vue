<template>
  <div>
    <!-- Footer -->
    <mdb-footer color="#69f0ae green accent-2" class="pt-10 mt-10 fixed-bottom">
      <mdb-container class="text-center ">
        <mdb-row class="row d-flex justify-content-center">
          <mdb-col col="sm-4">
            <router-link :to="$root.formatLink('about')">{{'footer.about' | translate}}</router-link>
          </mdb-col>
          <mdb-col col="sm-4">
            <router-link :to="$root.formatLink('guide')">{{'footer.guide' | translate}}</router-link>
          </mdb-col>
          <mdb-col col="sm-4">
            <router-link :to="$root.formatLink('contacts')">{{'footer.contacts' | translate}}</router-link>
          </mdb-col>
        </mdb-row>
      </mdb-container>
      <div class="footer-copyright text-center py-3">
        <mdb-container fluid>
          &copy; 2019 Copyright: <a href=""> Batetata </a>
        </mdb-container>
      </div>
    </mdb-footer>
    <!-- Footer -->
  </div>
</template>

<script>
  import { mdbFooter, mdbContainer, mdbRow, mdbCol } from 'mdbvue';

  export default {
    name: 'Footer',
    components: {
      mdbFooter,
      mdbContainer,
      mdbRow,
      mdbCol
    }
  }
</script>

<style scoped>

</style>
