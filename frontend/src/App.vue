<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: "App"
};
</script>

<style lang="scss">
@import "./assets/vendor/nucleo/css/nucleo.css";
@import "./assets/scss/argon";
@import "./assets/scss/styles";
@import "./assets/scss/fontawesome/fontawesome";
@import "./assets/scss/fontawesome/solid";
@import "./assets/scss/fontawesome/brands";
@import "./assets/scss/semantic-feed";

/* Filepond */
@import "~filepond/dist/filepond.min.css";
@import "~filepond-plugin-image-preview/dist/filepond-plugin-image-preview.min.css";
</style>
