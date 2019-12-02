<template>
    <div class="col col-md-8">
        <div>
            <p :class="[{ 'text-warning': timeSmall }]">
                Time Remaining: {{ timeleft }}
            </p>
        </div>
        <hr class="my-2"/>

        <div v-if="problem.id">
            <div
                    class="d-flex-column justify-content-start p-4"
                    @click="showProblem(problem.id)"
            >
                <div class="d-flex justify-content-between align-items-center">
                    <h4 class="text-primary">
                        {{ problem.title }}
                    </h4>
                    <a
                            href="#"
                            class="badge badge-primary text-right"
                            @click.stop="getProlemsInCategory(problem.category.id)"
                    >
                        {{ problem.category.name }}
                    </a>
                </div>
                <p class="text-left">
                    {{ problem.description }}
                </p>

                <small class="text-muted text-left">Created by: {{ moderator }}</small>
            </div>

            <hr class="my-2"/>

            <div class="d-flex-column justify-content-start px-4 mt-2">
                <p class="text-primary font-weight-500">Reading:</p>

                <p class="mt-2 pl-4">{{problem.knowledgeSource.content}}</p>
            </div>

            <div class="d-flex-column justify-content-start p-4 mt-4  ">

                <form>
                    <h4 class="mb-4 text-primary">Questions:</h4>

                    <div
                            v-for="(que, qi) in problem.questions"
                            :key="que.id"
                            class="mb-4"
                    >
                        {{ qi + 1 }}. {{ que.content }}

                        <div
                                class="custom-control custom-radio my-3 ml-4"
                                v-for="(opt, i) in que.options"
                                :key="i"
                        >
                            <input
                                    :name="`radio-${qi}`"
                                    class="custom-control-input"
                                    :id="`category-radio-${qi}-${i}`"
                                    type="radio"
                                    v-model="session.answers[qi]"
                                    :value="i"
                            />
                            <label
                                    class="custom-control-label"
                                    :for="`category-radio-${qi}-${i}`"
                            >
                                {{ opt }}
                            </label>
                        </div>
                    </div>
                </form>
            </div>

            <hr class="my-4"/>
            <div class="text-right mt-4">
                <button
                        type="submit"
                        class="btn btn-primary"
                        @click="submit"
                        :disabled="submitting"
                >
          <span
                  class="spinner-grow spinner-grow-sm"
                  role="status"
                  aria-hidden="true"
                  v-if="submitting"
          ></span>
                    Submit Session
                </button>
            </div>
        </div>

        <div class="position-fixed d-flex flex-column" style="top: 5rem; right: 3rem;" v-if="submitting">
            <div class="spinner-grow text-primary" style="width: 2rem; height: 2rem;" role="status">
                <span class="sr-only">Loading...</span>
            </div>
            <p class="text-primary"><strong>Saving...</strong></p>
        </div>

    </div>
</template>

<script>
    const baseSocketURL = process.env.VUE_APP_WEBSOCKET_URL;
    const fiveMinutes = 60 * 5;
    let previousAnswer = [-1, -1, -1];

    export default {
        props: {
            id: {
                type: String,
                required: true
            }
        },
        name: "ProblemSession",
        data() {
            return {
                session: {
                    answers: [-1, -1, -1],
                    problem: {
                        id: ''
                    },
                    user: {
                        id: ''
                    },
                    dateStarted: "",
                    dateSubmitted: "",
                },
                problem: {},
                timeleft: "_ _:_ _",
                timer: null,
                submitting: false,
                timeSmall: false,
                editing: true,
                socket: null,
                sessionId: ''
            };
        },
        methods: {
            submit() {
                this.update((res) => {
                    this.socket.close();

                    this.editing = false;
                    if (this.sessionId) {
                        this.$router.replace({
                            name: "session-result",
                            params: {id: this.$hash.encode(this.sessionId)}
                        });
                    }
                });
            },
            update(cb) {
                if (this.submitting) return false;
                this.submitting = true;

                this.session.dateSubmitted = new Date().toISOString();

                if (this.socket.OPEN) {
                    this.socket.send(JSON.stringify(this.session));
                }
                this.submitting = false;
                cb(true);
            },
            getProblem(id) {
                const url = `problems/${id}`;

                this.$http.get(url).then(res => {
                    this.problem = res.data;

                    this.session.problem.id = id;
                    this.session.user.id = this.$store.state.userId;
                    this.connect();
                });
            },
            startTimer(duration) {
                this.timer = duration;
                let minutes, seconds;
                let timeout = setInterval(() => {
                    minutes = parseInt(this.timer / 60, 10);
                    seconds = parseInt(this.timer % 60, 10);

                    minutes = minutes < 10 ? "0" + minutes : minutes;
                    seconds = seconds < 10 ? "0" + seconds : seconds;

                    this.timeleft = minutes + ":" + seconds;

                    if (--this.timer < 0) {
                        clearTimeout(timeout);
                        this.submit();
                    }

                    if (!this.timeSmall && this.timer < 60) {
                        this.timeSmall = true;
                    }
                }, 1000);

                this.session.dateStarted = new Date().toISOString();
            },
            preventNav(event) {
                if (this.editing) {
                    event.preventDefault();
                    event.returnValue = "";
                }
            },
            authenticate(cb) {
                const token = localStorage.getItem("auth_token");
                const userId = this.$store.state.userId;

                const data = {
                    token,
                    userId
                };

                this.socket.send(JSON.stringify(data));
                cb(true);
            },
            connectionOpened($event) {
                console.log('connected');
                this.authenticate(() => this.startTimer(fiveMinutes));
            },
            connectionClosed($event) {
                console.log('closed')
            },
            connectionMessage($event) {
                this.sessionId = $event.data;
                this.socket.removeEventListener('message', this.connectionMessage);
            },
            connectionError($event) {
                console.log('error')
            },
            connect() {
                this.socket = new WebSocket(baseSocketURL);
                this.socket.addEventListener('open', this.connectionOpened);
                this.socket.addEventListener('close', this.connectionClosed);
                this.socket.addEventListener('message', this.connectionMessage);
                this.socket.addEventListener('error', this.connectionError);
            },
            arrayEqual(array1, array2) {
                for (let i = 0; i < 3; i++) {
                    if (array1[i] !== array2[i]) return false;
                }
                return true;
            },
            copyArray(array) {
                const res = [];
                for (let i = 0; i < 3; i++) {
                    res.push(array[i]);
                }
                return res;
            }
        },
        created() {
            this.getProblem(this.$hash.decode(this.id)[0]);
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
        beforeDestroy() {
            console.log("before destroy");
            this.socket.close();
            clearTimeout(this.timer);
            this.timer = null;

            window.removeEventListener("beforeunload", this.preventNav);
        },
        beforeMount() {
            window.addEventListener("beforeunload", this.preventNav);
        },
        beforeRouteLeave(to, from, next) {
            if (this.editing) {
                if (!window.confirm("You still have some time left.")) {
                    return;
                }
                this.submit();
            } else {
                next();
            }
        },
        watch: {
            'session': {
                handler: function (value) {
                    if (value.dateStarted && !this.arrayEqual(previousAnswer, value.answers)) {
                        previousAnswer = this.copyArray(value.answers);
                        this.update(() => {});
                    }
                },
                deep: true
            }
        }
    };
</script>
