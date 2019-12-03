<template>
    <div class="col col-md-8">
        <div v-if="problem.id">
            <div
                    @click="showProblem(problem.id)"
                    class="d-flex-column justify-content-start p-4"
            >

                <div class="alert alert-primary alert-dismissible fade show" role="alert">
                    <span class="alert-inner--icon"><i class="ni ni-like-2"></i></span>
                    <span class="alert-inner--text" v-if="session.pointsGained > 0"><strong>Congratulations</strong> You have gained {{session.pointsGained}} points!</span>
                    <span class="alert-inner--text" v-else> No points were gained for this session.</span>
                </div>

                <div class="d-flex justify-content-between align-items-center mt-4">
                    <h4>
                        {{ problem.title }}
                    </h4>
                    <a
                            @click.stop="getProlemsInCategory(problem.category.id)"
                            class="badge badge-primary text-right"
                            href="#"
                    >
                        {{ problem.category.name }}
                    </a>
                </div>
            </div>

            <div class="d-flex-column justify-content-start px-4">
                <div>
                    <h4 class="mb-4">Questions:</h4>

                    <div
                            :key="que.id"
                            :set="correct = session.answers[qi] === session.correctAnswers[qi]"
                            class="mb-4"
                            v-for="(que, qi) in problem.questions"
                    >
                        {{ qi + 1 }}. {{ que.content }}

                        <p class="mt-2 ml-4 text-primary font-weight-600" v-if="!correct">
                            Answer: {{que.options[session.correctAnswers[qi]]}}
                        </p>

                        <div
                                :key="i"
                                class="custom-control custom-radio my-3 ml-4"
                                v-for="(opt, i) in que.options"
                        >
                            <input
                                    :id="`category-radio-${qi}-${i}`"
                                    :name="`radio-${qi}`"
                                    :value="i"
                                    class="custom-control-input"
                                    disabled
                                    type="radio"
                                    v-model="session.answers[qi]"
                            />
                            <label
                                    :class="correct ? 'blue' : 'red'"
                                    :for="`category-radio-${qi}-${i}`"
                                    class="custom-control-label"
                            >
                                <span>{{ opt }}</span>
                            </label>
                        </div>
                    </div>
                </div>
            </div>

            <hr class="my-4"/>
            <div class="text-right mt-4">
                <button
                        @click="submit"
                        class="btn btn-primary"
                        type="button"
                >
                    Finish
                </button>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        props: {
            id: {
                type: String,
                required: true
            }
        },
        name: "SessionResult",
        data() {
            return {
                session: {
                    problem: {},
                    answers: [],
                    correctAnswers: [],
                }
            };
        },
        methods: {
            submit() {
                if (this.problem.id) {
                    return this.$router.push({
                        name: 'problem-detail',
                        params: {id: this.$hash.encode(this.problem.id)}
                    });
                } else {
                    return this.$router.push({name: 'home'});
                }
            },
            getSession(id) {
                const url = `sessions/${id}`;

                this.$http.get(url).then(res => {
                    this.session = Object.assign({}, this.session, res.data);
                    this.getProblem(this.session.problem)
                });
            },
            getProblem(id) {
                const url = `problems/${id}`;

                this.$http.get(url).then(res => {
                    this.session.problem = res.data;
                });
            },
        },
        computed: {
            problem() {
                return this.session.problem
            }
        },
        created() {
            this.getSession(this.$hash.decode(this.id)[0]);
        },
        asyncComputed: {
            moderator: {
                get() {
                    if (!this.problem.moderator) return "";
                    const url = `users/${this.problem.moderator}`;
                    return this.$http
                        .get(url)
                        .then(res => {
                            return res.data.fullName;
                        })
                        .catch(err => {
                            return err;
                        });
                },
                default: "Moderator"
            }
        },
    };
</script>

<style lang="scss" scoped>
    .custom-control-input:checked ~ .custom-control-label.red::before {
        background-color: red;
    }

    .custom-control-input:checked ~ .custom-control-label.green::before {
        background-color: blue;
    }
</style>