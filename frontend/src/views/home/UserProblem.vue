<template>
  <div>
    <div class="mt-2">
      <ul
        class="nav nav-pills nav-fill flex-column flex-sm-row"
        id="tabs-text"
        role="tablist"
      >
        <li class="nav-item" v-for="tab in tabs" :key="tab.id">
          <a
            class="nav-link mb-sm-3 mb-md-0"
            id="tabs-text-1-tab"
            data-toggle="tab"
            href="#tabs-text-1"
            role="tab"
            aria-controls="tabs-text-1"
            aria-selected="true"
            :class="activeTab === tab.id ? ' active' : ''"
            @click="changeTabView(tab.id)"
            >{{ tab.text }}</a
          >
        </li>
      </ul>
    </div>
    <Problem
      :problems="problems"
      @category="getProblemsForCategory"
      @prob="showProblem"
      @search="problems = $event"
      v-if="problems.length"
    >
      <div class="text-right">
        <button type="button" class="btn btn-info" @click="getRandomProblem">
          Random Problem
        </button>
      </div>
    </Problem>

    <Empty v-else mess="problems" >
    </Empty>
  </div>
</template>

<script>
import Problem from "@/views/home/Problem";
export default {
  name: "UserProblem",
  data() {
    return {
      randLoading: false,
      problems: [],
      activeTab: 1,
      tabs: [
        {
          id: 1,
          text: "All Problems",
          value: "all"
        },
        {
          id: 2,
          text: "Attempted Problems",
          value: "attempted"
        }
      ]
    };
  },
  methods: {
    showProblem(problemId) {
      this.$router.push({
        name: "problem-detail",
        params: { id: this.$hash.encode(problemId) }
      });
    },
    getProblems() {
      const url = "problems";

      this.$http.get(url).then(res => {
        this.problems = res.data;
      });
    },
    getAttemptedProblems() {
      const url = "users/current/attemptedProblems";

      this.$http.get(url).then(res => {
        this.problems = res.data;
      });
    },
    changeTabView(tabId) {
      if (this.activeTab === tabId) return;

      switch (tabId) {
        case 1:
          this.getProblems();
          break;
        case 2:
          this.getAttemptedProblems();
          break;
      }

      this.activeTab = tabId;
    },
    getRandomProblem() {
      if (this.randLoading) return;
      this.randLoading = true;

      const url = "problems/random";

      this.$http
        .get(url)
        .then(res => {
          this.$router.push({
            name: "problem-detail",
            params: { id: this.$hash.encode(res.data.id) }
          });
        })
        .finally(() => (this.randLoading = false));
    },
    getProblemsForCategory(categoryId) {
      const url = `category/${categoryId}`;

      this.$http.get(url).then(res => {
        this.problems = res.data.problems;
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
