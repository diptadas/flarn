<template>
    <div class="event">
        <div class="label">
            <img :src="activity.userAvatarLink"/>
        </div>
        <div class="content">
            <div class="summary">
                <router-link
                        :to="{
            name: 'user-profile',
            params: { id: $hash.encode(activity.userId) }
          }"
                >{{ activity.userFullName }}
                </router-link
                >
                commented on a problem
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
                >{{ activity.userFullName }}
                </router-link
                >
                has commented on
                <router-link
                        :to="{
            name: 'problem-detail',
            params: { id: $hash.encode(activity.problemId) }
          }"
                >{{ activity.problemTitle }}
                </router-link
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
        name: "Commented",
        mixins: [formateDate]
    };
</script>
