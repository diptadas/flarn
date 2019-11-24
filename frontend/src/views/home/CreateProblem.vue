<template>
  <div>
    <!-- Header -->
    <div
      class="header d-flex align-items-center header-back header-create rounded mt--3"
      style="z-index: 1;"
    >
      <!-- Mask -->
      <span class="mask bg-gradient-default opacity-8"></span>
      <!-- Header container -->
      <div class="container-fluid d-flex align-items-center">
        <div class="row w-100">
          <div class="col-lg-7 col-md-10">
            <h1 class="display-4 text-white">Create New Problems here</h1>
          </div>
        </div>
      </div>
    </div>
    <div class="container-fluid mt--5">
      <div class="row justify-content-center">
        <div class="col-xl-8 order-xl-1">
          <div class="card bg-secondary shadow">
            <div class="card-header bg-white border-0">
              <div class="row align-items-center">
                <div class="col-8">
                  <h3 class="mb-0">Problem Details</h3>
                </div>
                <div class="col-4 text-right">
                  <a href="#!" class="btn btn-sm btn-primary">Clear</a>
                </div>
              </div>
            </div>
            <div class="card-body">
              <form>
                <div class="pl-lg-4">
                  <div class="row">
                    <div class="col-md-12">
                      <div class="form-group">
                        <label class="form-control-label" for="input-address"
                          >Proble Title</label
                        >
                        <input
                          id="problem-title"
                          class="form-control form-control-alternative"
                          placeholder="Enter title for Problem"
                          v-model="problem.title"
                          type="text"
                        />
                      </div>

                      <div class="form-group">
                        <label>Problem Description (optional)</label>
                        <textarea
                          rows="2"
                          v-model="problem.description"
                          class="form-control form-control-alternative"
                          placeholder="Provide a short description of the problem"
                        ></textarea>
                      </div>
                    </div>
                  </div>
                </div>
                <hr class="my-4" />
                <div class="pl-lg-4">
                  <div class="row">
                    <div class="col-md-12">
                      <div class="form-group">
                        <label>Knowledge Source</label>

                        <textarea
                          rows="2"
                          v-model="problem.knowledgeSource.content"
                          class="form-control form-control-alternative"
                          placeholder="Provide the content of the problem"
                        ></textarea>
                      </div>
                    </div>
                  </div>
                  <hr class="my-4" />
                  <div class="row">
                    <div class="col-md-12">
                      <!-- Question and Options Here -->
                      <Question
                        v-for="i in 3"
                        :key="i"
                        :id="i"
                        v-model="problem.questions[i - 1]"
                      />
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-lg-4">
                      <div class="form-group">
                        <label class="form-control-label" for="input-city"
                          >Difficulty</label
                        >
                        <select
                          class="form-control"
                          id="difficulty"
                          v-model="problem.difficulty"
                        >
                          <option
                            v-for="opt in difficulties"
                            :key="opt.id"
                            :id="opt.id"
                            :value="opt.value"
                            >{{ opt.text }}</option
                          >
                        </select>
                      </div>
                    </div>
                    <div class="col-lg-4">
                      <div class="form-group">
                        <label class="form-control-label" for="input-country"
                          >Category</label
                        >
                        <select
                          class="form-control"
                          id="category"
                          v-model="problem.category.id"
                        >
                          <option value="">Select Category</option>
                          <option
                            v-for="opt in categories"
                            :key="opt.id"
                            :id="opt.id"
                            :value="opt.id"
                            >{{ opt.name }}</option
                          >
                        </select>
                      </div>
                    </div>
                  </div>
                  <hr class="my-4" />
                  <div class="text-right mt-4">
                    <button
                      type="button"
                      class="btn btn-primary"
                      @click="createProblem"
                    >
                      Add Problem
                    </button>
                  </div>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Question from "@/components/problem/Question.vue";
export default {
  name: "CreateProblem",
  data() {
    return {
      problem: {
        title: "",
        difficulty: "MEDIUM",
        knowledgeSource: {
          content: ""
        },
        questions: [
          {
            options: ["", "", ""],
            answer: ""
          },
          {
            options: ["", "", ""],
            answer: ""
          },
          {
            options: ["", "", ""],
            answer: ""
          }
        ],
        description: "",
        category: ""
      },
      difficulties: [
        {
          id: 0,
          text: "Hard",
          value: "HARD"
        },
        {
          id: 1,
          text: "Medium",
          value: "MEDIUM"
        },
        {
          id: 2,
          text: "Easy",
          value: "EASY"
        }
      ],
      categories: []
    };
  },
  methods: {
    createProblem() {
      const data = this.problem;
      const url = "problems";

      this.$http.post(url, data).then(res => {
        this.$router.push({ name: "manage-problems" });
      });
    },
    getCategories() {
      const url = "category";

      this.$http.get(url).then(res => {
        this.categories = res.data;
      });
    }
  },
  created() {
    this.getCategories();
  },
  components: {
    Question
  }
};
</script>
