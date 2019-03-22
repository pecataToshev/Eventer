<template>
  <div class="row d-flex justify-content-center">
    <mdb-jumbotron class="mb-0 col-lg-8">
      <h1 class="display-4">{{ 'jumbotron.welcome' | translate }}</h1>
      <p class="lead">This is a simple hero unit, a simple jumbotron-style component for calling extra attention to featured content or information.</p>
      <hr class="my-4" />
      <p>It uses utility classes for typgraphy and spacing to space content out within the larger container.</p>
      <mdb-btn @click="hj" color="primary" size="lg">Learn more</mdb-btn>
    </mdb-jumbotron>
  </div>
</template>

<script>
  import {mdbJumbotron, mdbBtn} from "mdbvue";
    export default {
        name: "Home",
        components: {
          mdbJumbotron,
          mdbBtn
        },
      methods: {
          hj(e){
            console.log(e);
          }
      }
    }
</script>

<style scoped>
</style>
