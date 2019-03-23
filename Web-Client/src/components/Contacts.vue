<template>
  <div class="container">
    <div class="row d-flex border-bottom mb-3">
      <h1 class="display-4">{{'contacts.' | translate}}</h1>
    </div>
    <div class="row d-flex ">
      <!-- Google Maps -->
      <div id="map-container-google-2" class="z-depth-1-half map-container col-sm-5" style="height: 400px">
        <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2933.385636527135!2d23.328292915099055!3d42.674372879167066!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x40aa85ac6b04a067%3A0x1c03d81bc96a6a96!2sFaculty+of+Mathematics+and+Informatics!5e0!3m2!1sen!2sbg!4v1553347136044"
                width="600" height="450" frameborder="0" style="border:0" allowfullscreen></iframe>
      </div>
      <!-- Google Maps -->
      <div class="card col-sm-6 ml-lg-auto text-left align-content-center">
        <div class="card-body">
          <h3>{{'contacts.briefing' | translate}}</h3><br>
          <p>{{'contacts.phone' | translate}}<br><br>
          {{'contacts.email' | translate}}<br><br>
            {{'contacts.address' | translate}}<br><br></p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "Contacts"
  };
</script>

<style scoped>
  .map-container{
    overflow:hidden;
    padding-bottom:25.25%;
    position:relative;
    height:0;
  }
  .map-container iframe{
    left:0;
    top:0;
    height:100%;
    width:100%;
    position:absolute;
  }
</style>
