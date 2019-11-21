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
    <Problem :problems="problems">
      <div class="text-right">
        <button type="button" class="btn btn-info" @click="getRandomProblem">
          Random Problem
        </button>
      </div>
    </Problem>
  </div>
</template>

<script>
import Problem from "@/views/home/Problem";
export default {
  name: "UserProblem",
  data() {
    return {
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
    getProblems() {
      const url = "problemsets";

      this.$http.get(url).then(res => {
        this.problems = res.data;
      });
    },
    changeTabView(tabId) {
      if (this.activeTab === tabId) return;

      // switch (tabId) {
      //   case 1:
      //     this.getSubscribedUsers();
      //     break;
      //   case 2:
      //     this.getSubscriptions();
      //     break;
      //   case 3:
      //     this.getUsers();
      //     break;
      // }

      this.activeTab = tabId;
    },
    getRandomProblem() {
      const url = "problemsets/random";

      this.$http.get(url).then(res => {
        this.$router.push({
          name: "problem-detail",
          params: { id: this.$hash.encode(res.data.id) }
        });
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
