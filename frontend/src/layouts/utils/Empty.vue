<template>
  <div
    class="d-flex justify-content-center align-items-center align-self-center mt-4"
  >
    <p class="text-muted font-italic">
      No {{ mess }} yet, your {{ mess }} will appear here...
    </p>

    <div class="d-flex justify-content-center align-content-center">
      <slot></slot>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    mess: {
      type: String
    }
  },
  name: "Empty"
};
</script>
