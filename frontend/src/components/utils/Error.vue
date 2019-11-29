<template>
  <div
    class="toast"
    role="alert"
    id="error-toast"
    aria-live="assertive"
    aria-atomic="true"
    style="position: absolute; top: 5rem; right: 2rem;"
    v-if="shown"
  >
    <div class="toast-header">
      <div class="pulse red mr-4"></div>
      <strong class="mr-auto">Attention</strong>
      <button
        type="button"
        class="ml-2 mb-1 close"
        data-dismiss="toast"
        aria-label="Close"
        @click="hide"
      >
        <span aria-hidden="true">&times;</span>
      </button>
    </div>
    <div class="toast-body">
      {{ error || "There was an error during handling your request..." }}
    </div>
  </div>
</template>

<script>
export default {
  name: "Error",
  data() {
    return {
      shown: false
    }
  },
  methods: {
    hide() {
      $("#element").toast("hide");
    },
    show() {
      this.shown = true;
      $("#error-toast").toast("show");
    }
  },
  computed: {
    error() {
      const mess = this.$store.state.error.text;
      if (mess) this.show();
      return mess;
    }
  },
  mounted() {
    $(".toast").toast({
      delay: 10000
    });

    $('#error-toast').on('hide.bs.toast', function () {
      this.shown = false;
    })
  }
};
</script>
