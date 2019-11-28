<template>
  <div class="event">
    <div class="label">
      <img src="../../assets/img/theme/team-4-800x800.jpg" />
    </div>
    <div class="content">
      <div class="summary">
        <router-link
          :to="{
            name: 'user-profile',
            params: { id: $hash.encode(activity.userId) }
          }"
          >{{ activity.userFullName }}</router-link
        >
        commented created a problem
        <div class="date">
          {{ activity.date | formatDate }}
        </div>
      </div>
      <div class="extra text">
        <router-link
          :to="{
            name: 'user-profile',
            params: { id: $hash.encode(activity.userId) }
          }"
          >{{ activity.userFullName }}</router-link
        >
        has created a new problem
        <router-link
          :to="{
            name: 'problem-detail',
            params: { id: $hash.encode(activity.problemId) }
          }"
          >{{ sactivity.problemTitle }}</router-link
        >
      </div>
      <div class="meta">
        <a class="like"> <i class="like icon"></i> 5 Likes </a>
      </div>
    </div>
  </div>
</template>

<script>
import * as timeago from "timeago.js";

export default {
  props: {
    activity: {
      type: Object
    }
  },
  data() {
    return {
      user: {
        id: -1
      },
      staredProblem: {
        id: -1
      }
    };
  },
  name: "CreatedProblem",
  methods: {
    getUser(id) {
      const url = `users/${id}`;

      this.$http.get(url).then(res => {
        this.user = res.data;
      });
    },
    getStaredProblem(id) {
      const url = `problems/${id}`;

      this.$http.get(url).then(res => {
        this.staredProblem = res.data;
      });
    }
  },
  filters: {
    formatDate(date) {
      return timeago.format(date);
    }
  },
  created() {
    this.getUser(this.activity.userId);
    this.getStaredProblem(this.activity.problemId);
  }
};
</script>
