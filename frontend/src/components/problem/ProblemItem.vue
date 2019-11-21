<template>
  <div
    class="d-flex-column justify-content-start btn btn-secondary btn-block my-1 p-4 pointed"
    @click="showProblem(p.id)"
  >
    <div class="d-flex justify-content-between align-items-center">
      <h4>
        {{ p.title }}
      </h4>
      <a
        href="#"
        class="badge badge-primary text-right"
        @click.stop="getProlemsInCategory(p.category.id)"
      >
        {{ p.category.name }}
      </a>
    </div>
    <p class="text-left">
      {{ p.description }}
    </p>
  </div>
</template>

<script>
export default {
  props: {
    p: {
      type: Object,
      required: true
    }
  },
  name: "Problem",
  methods: {
    showProblem(pId) {
      this.$emit("prob", pId);
    },
    getProlemsInCategory(catId) {
      this.$emit("cat", catId);
    }
  }
};
</script>

<style lang="scss" scoped></style>
