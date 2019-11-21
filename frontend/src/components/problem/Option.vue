<template>
  <div class="form-group">
    <label>Option {{ id }}</label>
    <input
      id="problem-title"
      class="form-control form-control-alternative"
      placeholder="Enter option for Problem"
      v-model="content"
      type="text"
    />
  </div>
</template>

<script>
export default {
  props: ["id", "value"],
  name: "Option",
  data() {
    return {
      content: ""
    };
  },
  watch: {
    content(old, value) {
      this.$emit("input", value);
    }
  }
};
</script>

<style lang="scss" scoped></style>
