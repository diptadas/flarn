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
                  <a @click="importMode ? cancelUpload() : showUpload()" class="btn btn-info">
                    <span class="btn-inner--icon mr-1">
                      <i class="fas fa-upload" style="top: 0;"></i>
                    </span>
                    {{importMode ? 'Cancel import' : 'Import from file'}}
                  </a>
                </div>
              </div>
            </div>
            <div class="card-body">
              <form>

                <div class="pl-lg-4" v-if="importMode">
                  <div class="row">
                    <div class="col-md-12">
                      <div class="form-group">
                        <label class="form-control-label" for="prob-file">
                          Upload JSON File
                        </label>
                        <file-pond
                                name="prob-file"
                                id="prob-file"
                                ref="pond"
                                accepted-file-types="application/json"
                                :server="uploadImage"
                                :files="files"
                                @init="handleFilePondInit"
                                class="pointed"/>
                      </div>
                    </div>
                  </div>
                </div>

                <hr class="my-4" v-if="importMode" />

                <div class="pl-lg-4">
                  <div class="row">
                    <div class="col-md-12">
                      <div class="form-group">
                        <label class="form-control-label" for="problem-title">
                          Problem Title
                        </label>
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
                        <label class="form-control-label" for="difficulty"
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
                        <label class="form-control-label" for="category"
                          >Category</label
                        >
                        <select
                          class="form-control"
                          id="category"
                          v-model="problem.category.name"
                        >
                          <option value="">Select Category</option>
                          <option
                            v-for="opt in categories"
                            :key="opt.id"
                            :id="opt.id"
                            :value="opt.name"
                            >{{ opt.name }}</option
                          >
                        </select>
                      </div>
                    </div>
                  </div>
                  <hr class="my-4" />
                  <div class="mt-4 d-flex justify-content-between align-items-center">
                    <button
                            type="button"
                            class="btn btn-warning"
                            @click="cancelProblem"
                            :disabled="loading"
                    >
                      <span
                              class="spinner-grow spinner-grow-sm"
                              role="status"
                              aria-hidden="true"
                              v-if="loading"
                      ></span>
                      Cancel
                    </button>

                    <button
                            type="button"
                            class="btn btn-primary"
                            @click="createProblem"
                            :disabled="loading"
                    >
                      <span
                              class="spinner-grow spinner-grow-sm"
                              role="status"
                              aria-hidden="true"
                              v-if="loading"
                      ></span>
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
  import vueFilePond from 'vue-filepond';

  // Import image preview and file type validation plugins
  import FilePondPluginFileValidateType from 'filepond-plugin-file-validate-type';

  // Create component
  const FilePond = vueFilePond(FilePondPluginFileValidateType);

import Question from "@/components/problem/Question.vue";
export default {
  name: "CreateProblem",
  data() {
    return {
      loading: false,
      problem: {
        title: "",
        difficulty: "MEDIUM",
        knowledgeSource: {
          content: ""
        },
        questions: [
          {
            content: "",
            options: ["", "", ""],
            answer: ""
          },
          {
            content: "",
            options: ["", "", ""],
            answer: ""
          },
          {
            content: "",
            options: ["", "", ""],
            answer: ""
          }
        ],
        description: "",
        category: {
          name: ''
        }
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
      categories: [],
      files: [],
      uploadImage: {
        process: this.processFile
      },
      importMode: false,
    };
  },
  methods: {
    processFile (fieldName, file, metadata, load) {
      const reader = new FileReader();
      reader.onload = this.fileLoaded;
      reader.readAsText(file);
      load(true);
    },
    fileLoaded($event) {
      const problem = JSON.parse($event.target.result);
      this.problem.title =  problem.title;
      this.problem.difficulty =  problem.difficulty;
      this.problem.title =  problem.title;
      this.problem.knowledgeSource.content = problem.knowledgeSource.content;
      this.problem.description = problem.description;
      this.problem.category.name = problem.category.name;

      for (let i = 0; i < 3; i++) {
        this.problem.questions[i].content = problem.questions[i].content;
        this.problem.questions[i].answer = problem.questions[i].answer;

        for (let j = 0; j < 3; j++) {
          this.problem.questions[i].options.splice(j, 1, problem.questions[i].options[j]);
        }
      }

      this.importMode = false;
    },
    showUpload(){
      this.importMode = true
    },
    cancelUpload(){
      this.importMode = false
    },
    handleFilePondInit() {
      console.log('FilePond has initialized');

      // FilePond instance methods are available on `this.$refs.pond`
    },
    cancelProblem() {
      this.$router.replace({name: 'manage-problems'})
    },
    createProblem() {
      if (this.loading) return false;
      this.loading = true;
      const data = this.problem;
      const url = "problems";

      this.$http
        .post(url, data)
        .then(res => {
          this.$router.push({ name: "manage-problems" });
        })
        .finally(() => (this.loading = false));
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
