<template>
  <div>
    <div class="d-flex justify-content-center">

      <mdb-carousel class="col-lg-12" :interval="8000" showControls showIndicators>
        <mdb-carousel-item img src="https://www.w3schools.com/w3css/img_lights.jpg" mask="black-light"
                           alt="First slide">
          <mdb-carousel-caption title="Light mask" text="First text"></mdb-carousel-caption>
        </mdb-carousel-item>
        <mdb-carousel-item img src="https://mdbootstrap.com/img/Photos/Slides/img%20(6).jpg" mask="black-strong"
                           alt="Second slide">
          <mdb-carousel-caption title="Strong mask" text="Second text"></mdb-carousel-caption>
        </mdb-carousel-item>
        <mdb-carousel-item img src="https://mdbootstrap.com/img/Photos/Slides/img%20(9).jpg" mask="black-slight"
                           alt="Third slide">
          <mdb-carousel-caption title="Super light mask" text="Third text"></mdb-carousel-caption>
        </mdb-carousel-item>
      </mdb-carousel>
    </div>
    <div style="margin-top: 2rem;">
      <mdb-btn size="lg" gradient="aqua">{{'home.jumbotron.begin' | translate }}</mdb-btn>
    </div>
    <div class="container d-flex justify-content-center" style="margin-top: 2rem;">

      <mdb-card class="card-body" style="width: 22rem; margin-top: 1rem; margin-right: 4rem;">
        <mdb-card-title>Panel Title</mdb-card-title>
        <mdb-card-text>Some quick example text to build on the panel title and make up the bulk of the panel's
          content.
        </mdb-card-text>
        <div class="flex-row">
          <a>Card link</a>
          <a style="margin-left: 1.25rem">Another link</a>
        </div>
      </mdb-card>

      <mdb-card class="card-body" style="width: 22rem; margin-top: 1rem; ">
        <mdb-card-title>Panel Title</mdb-card-title>
        <mdb-card-text>Some quick example text to build on the panel title and make up the bulk of the panel's
          content.
        </mdb-card-text>
        <div class="flex-row">
          <a>Card link</a>
          <a style="margin-left: 1.25rem">Another link</a>
        </div>
      </mdb-card>

      <mdb-card class="card-body" style="width: 22rem; margin-top: 1rem; margin-left: 4rem;">
        <mdb-card-title>Panel Title</mdb-card-title>
        <mdb-card-text>Some quick example text to build on the panel title and make up the bulk of the panel's
          content.
        </mdb-card-text>
        <div class="flex-row">
          <a>Card link</a>
          <a style="margin-left: 1.25rem">Another link</a>
        </div>
      </mdb-card>

    </div>
    <!--<mdb-jumbotron class="mb-0 col-lg-8">
      <h1 class="display-4">{{ 'home.jumbotron.welcome' | translate }}</h1>
      <p class="lead">This is a simple hero unit, a simple jumbotron-style component for calling extra attention to featured content or information.</p>
      <hr class="my-4" />
      <p>It uses utility classes for typgraphy and spacing to space content out within the larger container.</p>
      <mdb-btn @click="hj" gradient="aqua" size="lg">{{ 'home.jumbotron.begin' | translate }}</mdb-btn>
    </mdb-jumbotron>-->
  </div>
</template>

<script>
  import {
    mdbJumbotron,
    mdbBtn,
    mdbCarousel,
    mdbCarouselItem,
    mdbCarouselCaption,
    mdbCard,
    mdbCardTitle,
    mdbCardText
  } from "mdbvue";

  export default {
    name: "Home",
    components: {
      mdbJumbotron,
      mdbBtn,
      mdbCarousel,
      mdbCarouselItem,
      mdbCarouselCaption,
      mdbCard,
      mdbCardTitle,
      mdbCardText
    },
    methods: {
      hj(e) {
        console.log(e);
      }
    }
  };
</script>

<style scoped>
</style>
