<template>
  <div>
    <!-- Header -->
    <div
      class="header pb-8 pt-5 pt-lg-8 d-flex align-items-center header-back rounded mt--7"
      :style="styles"
    >
      <!-- Mask -->
      <span class="mask bg-gradient-default opacity-8 rounded"></span>
    </div>
    <div class="container-fluid mt--7">
      <div class="row">
        <div class="col-xl-6 mb-5 mb-xl-0 mx-auto">
          <div class="card card-profile shadow">
            <div class="row justify-content-center">
              <div class="col-lg-3 order-lg-2">
                <div class="card-profile-image">
                  <a href="#">
                    <img
                      :src="user.dpLink"
                      class="rounded-circle img-fliud"
                      style="height: 12rem;"
                    />
                  </a>
                </div>
              </div>
            </div>
            <div
              class="card-header text-center border-0 pt-8 pt-md-4 pb-0 pb-md-4"
            >
              <div class="d-flex justify-content-between">
                <a
                  class="btn btn-sm btn-info pointed"
                  @click="followsUser ? unSubscribeToUser() : subscribeToUser()"
                  >{{ followsUser ? "Unsuscribe" : "Subscribe" }}</a
                >
                <a
                  href="#"
                  class="btn btn-sm btn-default float-right pointed"
                  v-show="followsUser"
                  >Message</a
                >
              </div>
            </div>
            <div class="card-body pt-0 pt-md-4">
              <div class="row">
                <div class="col">
                  <div class="text-center mt-md-5" v-if="userFollowsMe">
                    <small class="font-weight-light text-muted text-monospace">
                      This user subscribes to you
                    </small>
                  </div>

                  <div
                    class="card-profile-stats d-flex justify-content-center "
                  >
                    <div>
                      <span class="heading">
                        {{ user.subscriptions.length }}
                      </span>
                      <span class="description">Subscriptions</span>
                    </div>
                    <div>
                      <span class="heading">
                        {{ user.subscribers.length }}
                      </span>
                      <span class="description">Subscribers</span>
                    </div>
                    <div>
                      <span class="heading">
                        {{ user.subscriptions.length }}
                      </span>
                      <span class="description">User Rank</span>
                    </div>
                  </div>
                </div>
              </div>
              <div class="text-center">
                <h3>
                  {{ user.fullName }}
                  <span class="font-weight-light">, {{ user.userType }}</span>
                </h3>
                <div class="h5 font-weight-300">
                  <i class="ni location_pin mr-2"></i>{{ user.username }}
                </div>
                <div class="h5 mt-4">
                  <i class="ni business_briefcase-24 mr-2"></i
                  >{{ user.state }} - {{ user.city }}
                </div>
                <hr class="my-4" />
                <p>
                  {{ user.biography }}
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import store from "@/store";
import Hashids from "hashids";
export default {
  props: {
    id: {
      required: true,
      type: String
    }
  },
  name: "UserProfile",
  data() {
    return {
      user: {
        subscriptions: [],
        subscribers: []
      }
    };
  },
  methods: {
    unSubscribeToUser() {
      const userId = this.$hash.decode(this.id);
      const url = `users/current/unfollow/${userId}`;

      this.$http.post(url).then(res => {
        this.getUserProfile(userId);
      });
    },
    subscribeToUser() {
      const userId = this.$hash.decode(this.id);
      const url = `users/current/follow/${userId}`;

      this.$http.post(url).then(res => {
        this.getUserProfile(userId);
      });
    },
    getUserProfile(userId) {
      const url = `users/${userId}`;

      this.$http.get(url).then(res => {
        this.user = res.data;
      });
    }
  },
  beforeRouteEnter(to, from, next) {
    const hash = new Hashids();
    const userId = Number(hash.decode(to.params.id));
    const selfUserId = Number(store.state.userId);
    if (userId === selfUserId) {
      return next({ name: "profile" });
    }
    next();
  },
  created() {
    this.getUserProfile(this.$hash.decode(this.id));
  },
  computed: {
    followsUser() {
      const userId = Number(this.$store.state.userId);
      return this.user.subscribers.indexOf(userId) !== -1;
    },
    userFollowsMe() {
      const userId = Number(this.$store.state.userId);
      return this.user.subscriptions.indexOf(userId) !== -1;
    },
    styles() {
      return {
        backgroundImage:
          'url("https://homepages.cae.wisc.edu/~ece533/images/tulips.png")'
      };
    }
  }
};
</script>