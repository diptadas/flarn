<template>
  <div v-if="ranks.length">
    Ranks
  </div>
  <Empty v-else mess="ranks" />
</template>

<script>
export default {
  name: "Ranks",
  data() {
    return {
      ranks: []
    };
  }
};
</script>
