<template>
  <div>
    {{ activities }}
  </div>
</template>

<script>
export default {
  name: "Timeline",
  data() {
    return {
      activities: []
    };
  },
  methods: {
    getActivities() {
      const url = "users/current/activities";

      this.$http.get(url).then(res => {
        this.activities = res.data;
      });
    }
  },
  created() {
    this.getActivities();
  }
};
</script>
