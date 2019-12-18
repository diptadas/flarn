<template>
    <div>
        <div class="mt-2">
            <ul
                    class="nav nav-pills nav-fill"
                    id="tabs-text"
                    role="tablist"
            >
                <li :key="tab.id" class="nav-item" v-for="tab in tabs">
                    <a
                            :class="activeTab === tab.id ? ' active' : ''"
                            @click="changeTabView(tab.id)"
                            aria-controls="tabs-text-1"
                            aria-selected="true"
                            class="nav-link mb-sm-3 mb-md-0"
                            data-toggle="tab"
                            href="#tabs-text-1"
                            id="tabs-text-1-tab"
                            role="tab"
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
        >
            <div class="text-right">
                <button @click="getRandomProblem" class="btn btn-info" type="button">
                    Random Problem
                </button>
            </div>
        </Problem>

    </div>
</template>

<script>
    import Problem from "@/layouts/problems/Problem";

    export default {
        name: "UserProblem",
        data() {
            return {
                randLoading: false,
                problems: [],
                activeTab: 1,
                init: false,
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
                    params: {id: this.$hash.encode(problemId)}
                });
            },
            getProblems() {
                const url = "problems";

                this.$http.get(url).then(res => {
                    this.problems = res.data;
                    this.init = true;
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
                            params: {id: this.$hash.encode(res.data.id)}
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
