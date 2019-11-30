<template>
  <div class="event">
    <div class="label">
      <img :src="activity.userAvatarLink" />
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
        joined flarn
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
        has just joined flarn. You can subscribe to him.
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
  name: "Followed",
  mixins: [formateDate]
};
</script>
