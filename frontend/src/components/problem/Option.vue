<template>
  <div class="form-group">
    <label>Option {{ id }}</label>
    <input
      id="problem-title"
      class="form-control form-control-alternative"
      placeholder="Enter option for Problem"
      :value="value"
      @change="$emit('input', $event.target.value)"
      type="text"
    />
  </div>
</template>

<script>
export default {
  props: ["id", "value"],
  name: "Option"
};
</script>
