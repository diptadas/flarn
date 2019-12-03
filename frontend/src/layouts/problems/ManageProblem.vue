<template>
    <div>
        <h3 class="text-primary">Problems you created:</h3>
        <Problem
                :problems="problems"
                @category="getProblemsForCategory"
                @prob="showProblem"
                @search="problems = $event"
                v-if="problems.length"
        >
            <div class="text-right">
                <button
                        @click="$router.push({ name: 'create-problem' })"
                        class="btn btn-info"
                        type="button"
                >
          <span class="btn-inner--icon mr-1">
            <i class="fas fa-plus" style="top: 0;"></i>
          </span>
                    Create New Problem
                </button>
            </div>
        </Problem>
        <Empty mess="problems" v-else>
            <button
                    @click="$router.push({ name: 'create-problem' })"
                    class="btn btn-info"
                    type="button"
            >
        <span class="btn-inner--icon mr-1">
          <i class="fas fa-plus" style="top: 0;"></i>
        </span>
                Create New Problem
            </button>
        </Empty>
    </div>
</template>

<script>
    import Problem from "@/layouts/problems/Problem";

    export default {
        name: "ManageProblem",
        data() {
            return {
                problems: [],
            };
        },
        methods: {
            getProblemsForCategory(categoryId) {
                const url = `category/${categoryId}`;

                this.$http.get(url).then(res => {
                    this.problems = res.data.problems;
                });
            },
            showProblem(problemId) {
                this.$router.push({
                    name: "manage-problems-detail",
                    params: {id: this.$hash.encode(problemId)}
                });
            },
            getProblems() {
                const url = "problems/created";

                this.$http.get(url).then(res => {
                    this.problems = res.data;
                });
            },
        },
        created() {
            this.getProblems();
        },
        components: {
            Problem
        }
    };
</script>
