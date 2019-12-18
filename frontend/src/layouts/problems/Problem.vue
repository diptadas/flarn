<template>
    <div class="row mt-lg-4">
        <div class="col-12 d-lg-none">
            <p class="mt-lg-4 m-md-0"> 
                <slot>Button</slot>
            </p>

            <div class="form-group mt-lg-4 ">
                <div class="input-group input-group-alternative mb-4">
                    <input
                            @keyup.enter="searchProblem"
                            class="form-control"
                            placeholder="Search for Problem"
                            type="text"
                            v-model="title"
                    />
                    <div class="input-group-append">
            <span class="input-group-text"
            ><i class="ni ni-zoom-split-in"></i
            ></span>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-12 col-md-8" >
            <div v-if="problems.length">
                <prob
                    :key="prob.id"
                    :p="prob"
                    @cat="$emit('category', $event)"
                    @prob="$emit('prob', $event)"
                    v-for="prob in problems"

            />
            </div>
            <div v-else>
                <Empty mess="problems"></Empty>
            </div>
        </div>
     
        <div class="col col-md-4 d-none d-lg-block">
            <p class="mt-lg-4 m-md-0"> 
                <slot>Button</slot>
            </p>

            <hr/>

            <div class="form-group mt-lg-4 ">
                <div class="input-group input-group-alternative mb-4">
                    <input
                            @keyup.enter="searchProblem"
                            class="form-control"
                            placeholder="Search for Problem"
                            type="text"
                            v-model="title"
                    />
                    <div class="input-group-append">
            <span class="input-group-text"
            ><i class="ni ni-zoom-split-in"></i
            ></span>
                    </div>
                </div>
            </div>

            <small class="text-muted">Select Category:</small>

            <div class="mt-lg-4">
                <div
                        :key="cat.id"
                        class="custom-control custom-radio mb-3"
                        v-for="cat in categories"
                >
                    <input
                            :id="`category-radio-${cat.id}`"
                            :value="cat"
                            class="custom-control-input"
                            name="category"
                            type="radio"
                            v-model="selectedCategory"
                    />
                    <label :for="`category-radio-${cat.id}`" class="custom-control-label">
                        {{ cat.name }}
                    </label>
                </div>
            </div>

            <small class="text-muted">Select Difficulty:</small>

            <div class="mt-4">
                <div
                        :key="diff.id"
                        class="custom-control custom-radio mb-3"
                        v-for="diff in diffs"
                >
                    <input
                            :id="`difficulty-radio-${diff.id}`"
                            :value="diff.value"
                            class="custom-control-input"
                            name="difficulty"
                            type="radio"
                            v-model="selectedDiff"
                    />
                    <label
                            :for="`difficulty-radio-${diff.id}`"
                            class="custom-control-label"
                    >
                        {{ diff.text }}
                    </label>
                </div>
            </div>

            <div class="text-right mt-4">
                <button
                        :disabled="loadingSearch"
                        @click="searchProblem"
                        class="btn btn-primary"
                        type="button"
                >
          <span class="btn-inner--icon mr-1" v-if="!loadingSearch">
            <i class="fas fa-search" style="top: 0;"></i>
          </span>
                    <span
                            aria-hidden="true"
                            class="spinner-grow spinner-grow-sm"
                            role="status"
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

                this.$http
                    .post(url, data)
                    .then(res => {
                        this.$emit("search", res.data);
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
