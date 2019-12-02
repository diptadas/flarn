<template>
  <tr class="pointed hover-grey">
    <th scope="row" @click="goToProfile">
      <div class="media align-items-center">
        <a href="#" class="avatar rounded-circle mr-3">
          <img alt="Image placeholder" :src="user.avatarLink" />
        </a>
        <div class="media-body">
          <span class="mb-0 text-sm">{{ user.fullName }}</span>
        </div>
      </div>
    </th>
    <td @click="goToProfile">
      <span class="badge badge-dot d-inline-block pt-3">
        <i class="bg-warning"></i> {{ user.userType }}
      </span>
    </td>
    <td @click="goToProfile">
      <div class="d-flex align-items-center">
        <span class="">{{user.points}} points</span>
      </div>
    </td>
    <td>
      <div class="avatar-group">
        <router-link :to="{name: 'user-profile', params: { id: $hash.encode(user.id)}}" class="avatar avatar-sm" data-toggle="tooltip" data-placement="top" :title="user.fullName" :data-original-title="user.fullName" v-for="user in followers" :key="user.id">
          <img alt="Image placeholder" :src="user.avatarLink" class="rounded-circle">
        </router-link>
      </div>
    </td>
    <td>
      <div class="avatar-group">
        <router-link :to="{name: 'user-profile', params: { id: $hash.encode(user.id)}}" class="avatar avatar-sm" data-toggle="tooltip" data-placement="top" :title="user.fullName"  :data-original-title="user.fullName" v-for="user in following" :key="user.id">
          <img alt="Image placeholder" :src="user.avatarLink" class="rounded-circle">
        </router-link>
      </div>
    </td>
    <td class="text-right" v-if="action">
      <div class="dropdown">
        <button
          class="btn btn-sm btn-icon-only text-light"
          role="button"
          data-toggle="dropdown"
          aria-haspopup="true"
          aria-expanded="false"
        >
          <i class="fa fa-ellipsis-v"></i>
        </button>
        <div
          class="dropdown-menu dropdown-menu-right dropdown-menu-arrow"
          style="z-index: 2;"
        >
          <a class="dropdown-item" @click="$emit('promote')">Promote User</a>
          <a class="dropdown-item" @click="$emit('delete')">Deactivate User</a>
        </div>
      </div>
    </td>
  </tr>
</template>

<script>
export default {
  props: {
    action: {
      type: Boolean
    },
    user: {
      type: Object,
      required: true
    }
  },
  name: "Person",
  data(){
    return {
      followers: [],
      following: []
    }
  },
  methods: {
    goToProfile() {
      this.$router.push({
        name: "user-profile",
        params: { id: this.$hash.encode(this.user.id) }
      });
    },
    getFollowers(userId) {
      const url = `users/${userId}/subscribers`;

      this.$http
              .get(url)
              .then(res => {
                this.followers = res.data.slice(0, 5);
              })
    },
    getFollowing(userId) {
      const url = `users/${userId}/subscriptions`;

      this.$http
              .get(url)
              .then(res => {
                this.following = res.data.slice(0, 5);
              })
    }
  },
  created() {
    this.getFollowers(this.user.id);
    this.getFollowing(this.user.id);
  }
};
</script>
