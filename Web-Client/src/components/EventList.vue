<template>
  <div class="list-group">
    <a href="#!" class="list-group-item list-group-item-action active">
      Cras justo odio
    </a>
    <a href="#!" @click="" class="list-group-item list-group-item-action">Dapibus ac facilisis in</a>
    <a href="#!" class="list-group-item list-group-item-action">Morbi leo risus</a>
    <a href="#!" class="list-group-item list-group-item-action">Porta ac consectetur ac</a>
    <a href="#!" class="list-group-item list-group-item-action disabled">Vestibulum at eros</a>
  </div>
</template>

<script>
  import CustomEvent from './CustomEvent';

  export default {
    name: "EventList",
    components:{
      CustomEvent
    }
  };

</script>

<style scoped>

</style>
