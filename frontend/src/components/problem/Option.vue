<template>
  <div class="form-group">
    <label>Option {{ id }}</label>
    <input
      id="problem-title"
      class="form-control form-control-alternative"
      placeholder="Enter option for Problem"
      v-model="content"
      @change="$emit('input', content)"
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
      content: this.value
    };
  }
};
</script>
