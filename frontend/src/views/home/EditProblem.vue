<template>
  <div>
    <div v-if="problem">
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
              <h1 class="display-4 text-white">Update Problem</h1>
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
                    <a href="#!" class="btn btn-sm btn-danger">Delete</a>
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
                        Update Problem
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
  </div>
</template>

<script>
import Question from "@/components/problem/Question.vue";
export default {
  props: {
    id: {
      type: String,
      required: true
    }
  },
  name: "EditProblem",
  data() {
    return {
      problem: null,
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
    getProblem(id) {
      const url = `problems/${id}`;

      this.$http.get(url).then(res => {
        this.problem = res.data;
      });
    },
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
    this.getProblem(this.$hash.decode(this.id)[0]);
  },
  components: {
    Question
  }
};
</script>
