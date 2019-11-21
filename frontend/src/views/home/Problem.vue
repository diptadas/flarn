<template>
  <div class="row mt-4">
    <div class="col col-md-8">
      <prob
        :p="prob"
        v-for="prob in problems"
        :key="prob.id"
        @prob="showProblem"
        @cat="showCategory"
      />
    </div>
    <div class="col col-md-4">
      <slot>Button</slot>

      <hr />

      <div class="form-group mt-4">
        <div class="input-group input-group-alternative mb-4">
          <input
            class="form-control"
            placeholder="Search for Problem"
            type="text"
            v-model="title"
            @keyup.enter="searchProblem"
          />
          <div class="input-group-append">
            <span class="input-group-text"
              ><i class="ni ni-zoom-split-in"></i
            ></span>
          </div>
        </div>
      </div>

      <small class="text-muted">Select Category:</small>

      <div class="mt-4">
        <div
          class="custom-control custom-radio mb-3"
          v-for="cat in categories"
          :key="cat.id"
        >
          <input
            name="category"
            class="custom-control-input"
            :id="`category-radio-${cat.id}`"
            type="radio"
            v-model="selectedCategory"
            :value="cat.value"
          />
          <label class="custom-control-label" :for="`category-radio-${cat.id}`">
            {{ cat.text }}
          </label>
        </div>
      </div>

      <small class="text-muted">Select Difficulty:</small>

      <div class="mt-4">
        <div
          class="custom-control custom-radio mb-3"
          v-for="diff in diffs"
          :key="diff.id"
        >
          <input
            name="difficulty"
            class="custom-control-input"
            :id="`difficulty-radio-${diff.id}`"
            type="radio"
            v-model="selectedDiff"
            :value="diff.value"
          />
          <label
            class="custom-control-label"
            :for="`difficulty-radio-${diff.id}`"
          >
            {{ diff.text }}
          </label>
        </div>
      </div>

      <div class="text-right mt-4">
        <button type="button" class="btn btn-primary" @click="searchProblem">
          Search Problems
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import Prob from "@/components/problem/ProblemItem.vue";

export default {
  name: "Problem",
  props: {
    problems: {
      type: Array
    }
  },
  data() {
    return {
      title: "",
      selectedCategory: "",
      categories: [
        {
          id: 1,
          text: "All Categories",
          value: ""
        },
        {
          id: 2,
          text: "Category 1",
          value: "category-1"
        },
        {
          id: 3,
          text: "Category 3",
          value: "category-3"
        }
      ],
      selectedDiff: "",
      diffs: [
        {
          id: 1,
          text: "All Levels",
          value: ""
        },
        {
          id: 2,
          text: "Easy",
          value: "easy"
        },
        {
          id: 3,
          text: "Medium",
          value: "medium"
        },
        {
          id: 4,
          text: "Hard",
          value: "hard"
        }
      ]
    };
  },
  methods: {
    searchProblem() {
      const url = "problemsets/search";

      const data = {};

      if (this.title) {
        data["title"] = this.title;
      }

      if (this.selectedCategory) {
        data["category"] = this.selectedCategory;
      }

      if (this.selectedDiff) {
        data["difficulty"] = this.selectedDiff;
      }

      this.$http
        .post(url, data)
        .then(res => {
          this.problems = res.data;
        })
    },
    showProblem(problemId) {
      this.$router.push({
        name: "problem-detail",
        params: { id: this.$hash.encode(problemId) }
      });
    },
    showCategory(categoryId) {
      console.log(categoryId);
    }
  },
  created() {
    // this.getProblems();
  },
  components: {
    Prob
  }
};
</script>

<style lang="scss" scoped>
.profile-navbar .nav-item .nav-link {
  color: #6c7293;
}

.profile-navbar .nav-item .nav-link.active {
  color: #464dee;
}

.profile-navbar .nav-item .nav-link i {
  font-size: 1.25rem;
}

.profile-feed-item {
  padding: 1.5rem 0;
  // border-bottom: 1px solid #e9e9e9;
}
.img-sm {
  width: 43px;
  height: 43px;
}

// .m-card {
//   border-radius: 2px;
//   display: inline-block;
//   position: relative;
// }

// .card-shadow {
//   box-shadow: 0 1px 3px rgba(0, 0, 0, 0.12), 0 1px 2px rgba(0, 0, 0, 0.24);
//   transition: all 0.3s cubic-bezier(0.25, 0.8, 0.25, 1);
// }

// .card-shadow:hover {
//   box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
// }
</style>
