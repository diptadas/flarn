<template>
  <div>
    <h3 class="text-primary">Problems you created:</h3>
    <Problem :problems="problems">
      <div class="text-right">
        <button
          type="button"
          class="btn btn-info"
          @click="$router.push({ name: 'create-problem' })"
        >
          Create New Problem
        </button>
      </div>
    </Problem>
  </div>
</template>

<script>
import Problem from "@/views/home/Problem";
export default {
  name: "ManageProblem",
  data() {
    return {
      problems: []
    };
  },
  methods: {
    getProblems() {
      const url = "problemsets";

      this.$http.get(url).then(res => {
        this.problems = res.data;
      });
    }
  },
  created() {
    this.getProblems();
  },
  components: {
    Problem
  }
};
</script>

<style lang="scss" scoped></style>
