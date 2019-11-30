<template>
  <div class="ui feed">
    <component
      v-for="act in activities"
      v-bind:is="act.activityType"
      :key="act.id"
      :activity="act"
    />
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
import RolesUpdated from "../../components/activities/RolesUpdated";

export default {
  name: "Feed",
  props: {
    activities: {
      type: Array
    }
  },
  components: {
    FOLLOWED: Followed,
    STARED: Stared,
    COMMENTED: Commented,
    CREATED_PROBLEM: CreatedProblem,
    ATTEMPTED_PROBLEM: AttemptedProblem,
    JOINED: Joined,
    UPDATED_PROFILE: UpdatedProfile,
    ROLES_UPDATED: RolesUpdated
  }
};
</script>
