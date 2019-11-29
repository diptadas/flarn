<template>
  <div class="row mt-4">
    <div class="col col-md-8">
      <prob
        :p="prob"
        v-for="prob in problems"
        :key="prob.id"
        @prob="$emit('prob', $event)"
        @cat="$emit('category', $event)"
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
            :value="cat"
          />
          <label class="custom-control-label" :for="`category-radio-${cat.id}`">
            {{ cat.name }}
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
        <button
          type="button"
          class="btn btn-primary"
          @click="searchProblem"
          :disabled="loadingSearch"
        >
          <span
            class="spinner-grow spinner-grow-sm"
            role="status"
            aria-hidden="true"
            v-if="loadingSearch"
          ></span>
          Search Problems
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import Prob from "@/components/problem/ProblemItem.vue";

const defaultCategory = {
  id: 0,
  name: "All Categories"
};

export default {
  name: "Problem",
  props: {
    problems: {
      type: Array
    }
  },
  data() {
    return {
      loadingSearch: false,
      title: "",
      selectedCategory: defaultCategory,
      categories: [],
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
          value: "EASY"
        },
        {
          id: 3,
          text: "Medium",
          value: "MEDIUM"
        },
        {
          id: 4,
          text: "Hard",
          value: "HARD"
        }
      ]
    };
  },
  methods: {
    searchProblem() {
      if (this.loadingSearch) return;
      this.loadingSearch = true;
      const url = "problems/search";

      const data = {};

      if (this.title) {
        data["title"] = this.title;
      }

      if (this.selectedCategory) {
        data["category"] = this.selectedCategory.name;
      }

      if (this.selectedDiff) {
        data["difficulty"] = this.selectedDiff;
      }

      this.$http.post(url, data).then(res => {
        this.$emit('search', res.data)
      })
      .finally(() => (this.loadingSearch = false));
    },
    getCategories() {
      const url = "category";

      this.$http.get(url).then(res => {
        this.categories = res.data;
        this.categories.unshift(defaultCategory);
      });
    }
  },
  created() {
    this.getCategories();
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
</style>
