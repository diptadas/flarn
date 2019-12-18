<template>
    <tr class="pointed hover-grey">
        <th @click="goToProfile" scope="row">
            <div class="media align-items-center">
                <a class="avatar rounded-circle mr-3" href="#">
                    <img :src="user.avatarLink" alt="Image placeholder"/>
                </a>
                <div class="media-body">
                    <span class="mb-0 text-sm">{{ user.fullName }}</span> <br>
                    <small class="d-lg-none">{{user.points}} points</small>
                </div>
            </div>
        </th>
        <td @click="goToProfile" class="d-none d-lg-table-cell">
      <span class="badge badge-dot d-inline-block pt-3">
        <i :class="userTypeColor"></i> {{ user.userType }}
      </span>
        </td>
        <td @click="goToProfile" class="d-none d-lg-table-cell"> 
            <div class="d-flex align-items-center">
                <span class="">{{user.points}} points</span>
            </div>
        </td>
        <td  class="d-none d-lg-table-cell">
            <small class="font-weight-500 text-info">{{followers.length}} followers</small>
            <div class="avatar-group">
                <router-link :data-original-title="user.fullName" :key="user.id"
                             :title="user.fullName" :to="{name: 'user-profile', params: { id: $hash.encode(user.id)}}"
                             class="avatar avatar-sm"
                             data-placement="top" data-toggle="tooltip" v-for="user in followers.slice(0, 5)">
                    <img :src="user.avatarLink" alt="Image placeholder" class="rounded-circle">
                </router-link>
            </div>
        </td>
        <td  class="d-none d-lg-table-cell">
            <small class="font-weight-500 text-info">{{following.length}} followers</small>
            <div class="avatar-group">
                <router-link :data-original-title="user.fullName" :key="user.id"
                             :title="user.fullName" :to="{name: 'user-profile', params: { id: $hash.encode(user.id)}}"
                             class="avatar avatar-sm"
                             data-placement="top" data-toggle="tooltip" v-for="user in following.slice(0, 5)">
                    <img :src="user.avatarLink" alt="Image placeholder" class="rounded-circle">
                </router-link>
            </div>
        </td>
        <td v-if="action">
            <div class="dropdown">
                <button
                        aria-expanded="false"
                        aria-haspopup="true"
                        class="btn btn-icon-only btn-info"
                        data-toggle="dropdown"
                        role="button"
                >
                    <i class="fa fa-user-edit"></i>
                </button>
                <div
                        class="dropdown-menu dropdown-menu-right"
                        style="z-index: 2;"
                >
                    <a @click="$emit('promote')" class="dropdown-item">Promote User</a>
                    <a @click="$emit('delete')" class="dropdown-item">Deactivate User</a>
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
        data() {
            return {
                followers: [],
                following: []
            }
        },
        methods: {
            goToProfile() {
                this.$router.push({
                    name: "user-profile",
                    params: {id: this.$hash.encode(this.user.id)}
                });
            },
            getFollowers(userId) {
                const url = `users/${userId}/subscribers`;

                this.$http
                    .get(url)
                    .then(res => {
                        this.followers = res.data;
                    })
            },
            getFollowing(userId) {
                const url = `users/${userId}/subscriptions`;

                this.$http
                    .get(url)
                    .then(res => {
                        this.following = res.data;
                    })
            }
        },
        computed: {
            userTypeColor() {
                const userType = this.user.userType;

                if(userType === 'LEARNER') {
                    return 'bg-primary';
                } else if(userType === 'MODERATOR') {
                    return 'bg-info';
                } else {
                    return 'bg-danger';
                }
            }
        },
        created() {
            this.getFollowers(this.user.id);
            this.getFollowing(this.user.id);
        }
    };
</script>
