<template>
    <Feed :activities="activities" v-if="activities.length"/>
    <Empty mess="stories" v-else/>
</template>

<script>
    import Feed from "@/layouts/feeds/Feed.vue";

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
