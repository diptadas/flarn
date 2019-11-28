<template>
  <Feed :activities="activities" />
</template>

<script>
import Feed from "@/views/home/Feed.vue";
export default {
  name: "Timeline",
  data() {
    return {
      activities: []
    };
  },
  methods: {
    getActivities() {
      const url = "/users/current/subscriptionsActivities";

      this.$http.get(url).then(res => {
        this.activities = res.data;
      });
    }
  },
  created() {
    this.getActivities();
  },
  components: {
    Feed
  }
};
</script>
