<template>
  <div class="home">
    <main>
      <section class="section section-shaped section-lg">
        <div class="container pt-lg-md h-screen">
          <div class="row justify-content-center"></div>
        </div>
      </section>
    </main>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from "@/components/HelloWorld.vue";

export default {
  name: "home",
  methods: {
    authenticate() {}
  },
  created() {
    this.$store.commit("SET_GLOBAL_BUTTON", "home");
    this.authenticate();
  },
  components: {
    HelloWorld
  }
};
</script>
