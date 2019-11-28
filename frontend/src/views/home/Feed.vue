<template>
  <div class="ui feed">
    <template v-for="act in activities">
      <Followed
        :key="act.id"
        v-if="act.activityType === 'FOLLOWED'"
        :activity="act"
      ></Followed>
      <Stared
        :key="act.id"
        v-else-if="act.activityType === 'STARED'"
        :activity="act"
      ></Stared>
      <Commented
        :key="act.id"
        v-else-if="act.activityType === 'COMMENTED'"
        :activity="act"
      ></Commented>
      <CreatedProblem
        :key="act.id"
        v-else-if="act.activityType === 'CREATED_PROBLEM'"
        :activity="act"
      ></CreatedProblem>
    </template>
  </div>
</template>

<script>
import Followed from "@/components/activities/Followed";
import Stared from "@/components/activities/Stared";
import Commented from "@/components/activities/Commented";
import CreatedProblem from "@/components/activities/CreatedProblem";

export default {
  name: "Feed",
  props: {
    activities: {
      type: Array
    }
  },
  components: {
    Followed,
    Stared,
    Commented,
    CreatedProblem
  }
};
</script>
