<template>
  <div class="ui feed">
    <template v-for="act in activities">
      <Followed
        :key="act.id"
        v-if="act.activityType === activityType.FOLLOWED"
        :activity="act"
      ></Followed>
      <Stared
        :key="act.id"
        v-else-if="act.activityType === activityType.STARED"
        :activity="act"
      ></Stared>
      <Commented
        :key="act.id"
        v-else-if="act.activityType === activityType.COMMENTED"
        :activity="act"
      ></Commented>
      <CreatedProblem
        :key="act.id"
        v-else-if="act.activityType === activityType.CREATED_PROBLEM"
        :activity="act"
      ></CreatedProblem>
      <AttemptedProblem
        :key="act.id"
        v-else-if="act.activityType === activityType.ATTEMPTED_PROBLEM"
        :activity="act"
      ></AttemptedProblem>
      <Joined
        :key="act.id"
        v-else-if="act.activityType === activityType.JOINED"
        :activity="act"
      ></Joined>
      <UpdatedProfile
        :key="act.id"
        v-else-if="act.activityType === activityType.UPDATED_PROFILE"
        :activity="act"
      ></UpdatedProfile>
    </template>
  </div>
</template>

<script>
import Followed from "@/components/activities/Followed";
import Stared from "@/components/activities/Stared";
import Commented from "@/components/activities/Commented";
import CreatedProblem from "@/components/activities/CreatedProblem";
import AttemptedProblem from "@/components/activities/AttemptedProblem";
import Joined from "@/components/activities/Joined";
import UpdatedProfile from "@/components/activities/UpdatedProfile";

const ActivityType = Object.freeze({
  JOINED: "JOINED",
  UPDATED_PROFILE: "UPDATED_PROFILE",
  FOLLOWED: "FOLLOWED",
  ATTEMPTED_PROBLEM: "ATTEMPTED_PROBLEM",
  CREATED_PROBLEM: "CREATED_PROBLEM",
  STARED: "STARED",
  COMMENTED: "COMMENTED"
});

export default {
  name: "Feed",
  props: {
    activities: {
      type: Array
    }
  },
  data() {
    return {
      activityType: ActivityType
    };
  },
  components: {
    Followed,
    Stared,
    Commented,
    CreatedProblem,
    AttemptedProblem,
    Joined,
    UpdatedProfile
  }
};
</script>
