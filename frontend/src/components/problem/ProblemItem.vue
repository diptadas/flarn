<template>
  <div
    class="d-flex align-items-start profile-feed-item card shadow my-2 p-4 pointed"
    @click="showProblem(p.id)"
  >
    <div class="">
      <h4>
        {{ p.title }}
      </h4>
      <p>
        {{ p.description }}
      </p>
      <a
        href="#"
        class="badge badge-primary"
        @click.stop="getProlemsInCategory(p.category.id)"
        >{{ p.category.name }}</a
      >
    </div>
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
