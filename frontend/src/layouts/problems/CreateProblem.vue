<template>
        <div class="container-fluid">
            <div class="row justify-content-center">
                <div class="col-12 col-lg-10 px-0">
                    <div class="card bg-secondary shadow">
                        <div class="card-header bg-white border-0">
                            <div class="row align-items-center">
                                <div class="col-12 col-lg-8">
                                    <h3 class="mb-0">Problem Details</h3>
                                </div>
                                <div class="col-12 col-lg-4 mt-4 mt-lg-0 text-center">
                                    <a
                                            @click="importMode ? cancelUpload() : showUpload()"
                                            class="btn btn-info"
                                    >
                    <span class="btn-inner--icon mr-1">
                      <i class="fas fa-upload" style="top: 0;"></i>
                    </span>
                                        {{ importMode ? "Cancel import" : "Import from file" }}
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
                                                        :files="files"
                                                        :server="uploadImage"
                                                        @init="handleFilePondInit"
                                                        accepted-file-types="application/json"
                                                        class="pointed"
                                                        id="prob-file"
                                                        name="prob-file"
                                                        ref="pond"
                                                />
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <hr class="my-4" v-if="importMode"/>

                                <div class="pl-lg-4">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <label class="form-control-label" for="problem-title">
                                                    Problem Title
                                                </label>
                                                <input
                                                        class="form-control form-control-alternative"
                                                        id="problem-title"
                                                        placeholder="Enter title for Problem"
                                                        type="text"
                                                        v-model="problem.title"
                                                />
                                            </div>

                                            <div class="form-group">
                                                <label>Problem Description (optional)</label>
                                                <textarea
                                                        class="form-control form-control-alternative"
                                                        placeholder="Provide a short description of the problem"
                                                        rows="2"
                                                        v-model="problem.description"
                                                ></textarea>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <hr class="my-4"/>
                                <div class="pl-lg-4">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <label>Knowledge Source</label>

                                                <textarea
                                                        class="form-control form-control-alternative"
                                                        placeholder="Provide the content of the problem"
                                                        rows="2"
                                                        v-model="problem.knowledgeSource.content"
                                                ></textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <hr class="my-4"/>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <!-- Question and Options Here -->
                                            <Question
                                                    :id="i"
                                                    :key="i"
                                                    v-for="i in 3"
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
                                                            :id="opt.id"
                                                            :key="opt.id"
                                                            :value="opt.value"
                                                            v-for="opt in difficulties"
                                                    >{{ opt.text }}
                                                    </option
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
                                                            :id="opt.id"
                                                            :key="opt.id"
                                                            :value="opt.name"
                                                            v-for="opt in categories"
                                                    >{{ opt.name }}
                                                    </option
                                                    >
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <hr class="my-4"/>
                                    <div
                                            class="mt-4 d-flex justify-content-between align-items-center"
                                    >
                                        <button
                                                :disabled="loading"
                                                @click="cancelProblem"
                                                class="btn btn-warning d-none d-lg-inline-block"
                                                type="button"
                                        >
                      <span
                              aria-hidden="true"
                              class="spinner-grow spinner-grow-sm"
                              role="status"
                              v-if="loading"
                      ></span>
                                            Cancel
                                        </button>

                                        <button
                                                :disabled="loading"
                                                @click="createProblem"
                                                class="btn btn-primary text-right"
                                                type="button"
                                        >
                      <span
                              aria-hidden="true"
                              class="spinner-grow spinner-grow-sm"
                              role="status"
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
</template>

<script>
    import vueFilePond from "vue-filepond";
    // Import image preview and file type validation plugins
    import FilePondPluginFileValidateType from "filepond-plugin-file-validate-type";
    import Question from "@/components/problem/Question.vue";

    // Create component
    const FilePond = vueFilePond(FilePondPluginFileValidateType);

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
                        name: ""
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
                importMode: false
            };
        },
        methods: {
            processFile(fieldName, file, metadata, load) {
                const reader = new FileReader();
                reader.onload = this.fileLoaded;
                reader.readAsText(file);
                load(true);
            },
            fileLoaded($event) {
                const problem = JSON.parse($event.target.result);
                this.problem.title = problem.title;
                this.problem.difficulty = problem.difficulty;
                this.problem.title = problem.title;
                this.problem.knowledgeSource.content = problem.knowledgeSource.content;
                this.problem.description = problem.description;
                this.problem.category.name = problem.category.name;

                for (let i = 0; i < 3; i++) {
                    this.problem.questions[i].content = problem.questions[i].content;
                    this.problem.questions[i].answer = problem.questions[i].answer;

                    for (let j = 0; j < 3; j++) {
                        this.problem.questions[i].options.splice(
                            j,
                            1,
                            problem.questions[i].options[j]
                        );
                    }
                }

                this.importMode = false;
            },
            showUpload() {
                this.importMode = true;
            },
            cancelUpload() {
                this.importMode = false;
            },
            handleFilePondInit() {
                console.log("FilePond has initialized");

                // FilePond instance methods are available on `this.$refs.pond`
            },
            cancelProblem() {
                this.$router.replace({name: "manage-problems"});
            },
            createProblem() {
                if (this.loading) return false;
                this.loading = true;
                const data = this.problem;
                const url = "problems";

                this.$http
                    .post(url, data)
                    .then(res => {
                        this.$router.push({name: "manage-problems"});
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
