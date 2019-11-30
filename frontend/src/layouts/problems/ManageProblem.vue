<template>
  <div>
    <h3 class="text-primary">Problems you created:</h3>
    <Problem
      :problems="problems"
      @prob="showProblem"
      @search="problems = $event"
      @category="getProblemsForCategory"
      v-if="problems.length"
    >
      <div class="text-right">
        <button
          type="button"
          class="btn btn-info"
          @click="$router.push({ name: 'create-problem' })"
        >
          <span class="btn-inner--icon mr-1">
            <i class="fas fa-plus" style="top: 0;"></i>
          </span>
          Create New Problem
        </button>
      </div>
    </Problem>
    <Empty v-else mess="problems">
      <button
        type="button"
        class="btn btn-info"
        @click="$router.push({ name: 'create-problem' })"
      >
        <span class="btn-inner--icon mr-1">
          <i class="fas fa-plus" style="top: 0;"></i>
        </span>
        Create New Problem
      </button>
    </Empty>
  </div>
</template>

<script>
import Problem from "@/layouts/problems/Problem";
export default {
  name: "ManageProblem",
  data() {
    return {
      problems: []
    };
  },
  methods: {
    getProblemsForCategory(categoryId) {
      const url = `category/${categoryId}`;

      this.$http.get(url).then(res => {
        this.problems = res.data.problems;
      });
    },
    showProblem(problemId) {
      this.$router.push({
        name: "manage-problems-detail",
        params: { id: this.$hash.encode(problemId) }
      });
    },
    getProblems() {
      const url = "problems";

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
