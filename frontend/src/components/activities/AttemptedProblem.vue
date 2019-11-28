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
        attempted a problem
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
        has attempted a new problem
        <router-link
          :to="{
            name: 'problem-detail',
            params: { id: $hash.encode(activity.problemId) }
          }"
          >{{ activity.problemTitle }}</router-link
        >
      </div>
      <div class="meta">
        <a class="like"> <i class="like icon"></i> 5 Likes </a>
      </div>
    </div>
  </div>
</template>

<script>
import formateDate from "./formateDate";

export default {
  props: {
    activity: {
      type: Object
    }
  },
  name: "AttemptedProblem",
  mixins: [formateDate]
};
</script>
