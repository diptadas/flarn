<template>
  <Feed :activities="activities" v-if="activities.length" />
  <Empty v-else mess="stories" />
</template>

<script>
import Feed from "@/layouts/home/Feed.vue";
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
