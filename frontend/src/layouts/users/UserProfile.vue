<template>
    <div>
        <!-- Header -->
        <div
                :style="styles"
                class="header pb-8 pt-5 pt-lg-8 d-flex align-items-center header-back rounded mt--7"
        >
            <!-- Mask -->
            <span class="mask bg-gradient-default opacity-8 rounded"></span>
        </div>
        <div class="container-fluid mt--7">
            <div class="row">
                <div class="col col-12 col-md-8 mb-5 mb-xl-0 mx-auto">
                    <div class="card card-profile shadow">
                        <div class="row justify-content-center">
                            <div class="col-12 col-lg-3  d-flex justify-content-center">
                                <div class="card-profile-image" style="width: 10rem; height: 10rem;">
                                        <img
                                                :src="user.dpLink"
                                                class="rounded-circle img-fluid img-thumbnail h-100"
                                                style="height: 100%;"
                                        />
                                </div>
                            </div>
                        </div>
                        <div
                                class="card-header text-center border-0  mt--5 pb-0"
                        >
                             <a
                                        @click="followsUser ? unSubscribeToUser() : subscribeToUser()"
                                        class="btn btn-sm btn-info pointed"
                                >{{ followsUser ? "Unsuscribe" : "Subscribe" }}</a
                                >
                        </div>
                        <div class="card-body mt--2">
                            <div class="row">
                                <div class="col col-12">
                                    <div class="text-center mt-md-5" v-if="userFollowsMe">
                                        <small class="font-weight-light text-muted text-monospace">
                                            This user subscribes to you
                                        </small>
                                    </div>

                                    <div
                                            class="card-profile-stats row justify-content-center"
                                    >
                                        <div class="col col-6 col-md-3 mr-0">
                      <span class="heading">
                        {{ user.subscriptions.length }}
                      </span>
                                            <span class="description">Subscriptions</span>
                                        </div>
                                        <div class="col col-6 col-md-3 mr-0">
                      <span class="heading">
                        {{ user.subscribers.length }}
                      </span>
                                            <span class="description">Subscribers</span>
                                        </div>
                                        <div class="col col-6 col-md-3 mr-0">
                      <span class="heading">
                        {{ user.points }}
                      </span>
                                            <span class="description">User Points</span>
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
                                <hr class="my-4"/>
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
                return next({name: "profile"});
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
