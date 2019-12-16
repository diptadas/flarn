<template>
  <div class="col-12 col-md-8">
    <div>
      <p :class="[{ 'text-warning': timeSmall }]">
        Time Remaining: {{ timeleft }}
      </p>
    </div>
    <hr class="my-2" />

    <div v-if="problem.id">
      <div
        @click="showProblem(problem.id)"
        class="d-flex-column justify-content-start p-lg-4"
      >
        <div class="d-flex justify-content-between align-items-center">
          <h4 class="text-primary">
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
        <p class="text-left">
          {{ problem.description }}
        </p>

        <small class="text-muted text-left">Created by: {{ moderator }}</small>
      </div>

      <hr class="my-2" />

      <div class="d-flex-column justify-content-start px-lg-4 mt-2">
        <p class="text-primary font-weight-500">Reading:</p>

        <p class="mt-2 pl-4">{{ problem.knowledgeSource.content }}</p>
      </div>

      <div class="d-flex-column justify-content-start p-lg-4 mt-4">
        <form>
          <h4 class="mb-4 text-primary">Questions:</h4>

          <div
            :key="que.id"
            class="mb-4"
            v-for="(que, qi) in problem.questions"
          >
            {{ qi + 1 }}. {{ que.content }}

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
                type="radio"
                v-model="session.answers[qi]"
              />
              <label
                :for="`category-radio-${qi}-${i}`"
                class="custom-control-label"
              >
                {{ opt }}
              </label>
            </div>
          </div>
        </form>
      </div>

      <hr class="my-4" />
      <div class="text-right mt-4">
        <button
          :disabled="submitting"
          @click="submit"
          class="btn btn-primary"
          type="submit"
        >
          <span
            aria-hidden="true"
            class="spinner-grow spinner-grow-sm"
            role="status"
            v-if="submitting"
          ></span>
          Submit Session
        </button>
      </div>
    </div>

    <div
      class="position-fixed d-flex flex-column"
      style="top: 5rem; right: 3rem;"
      v-if="updating"
    >
      <div
        class="spinner-grow text-primary"
        role="status"
        style="width: 2rem; height: 2rem;"
      >
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
          id: ""
        },
        user: {
          id: ""
        },
        dateStarted: "",
        dateSubmitted: ""
      },
      problem: {},
      timeleft: "_ _:_ _",
      timer: null,
      updating: false,
      timeSmall: false,
      editing: true,
      socket: null,
      submitting: false,
      submitted: false,
      sessionId: ""
    };
  },
  methods: {
    submit() {
      if (this.submitting || this.submitted) return false;
      this.submitting = true;

      this.update(res => {
        this.submitted = true;
        this.editing = false;

        this.$nextTick().then(res => {
          this.$router.replace({
            name: "session-result",
            params: { id: this.$hash.encode(this.sessionId) }
          });
        });
      });
    },
    update(cb) {
      if (this.updating || this.submitted) return false;
      this.updating = true;
      this.session.dateSubmitted = new Date().toISOString();

      if (this.socket.OPEN) {
        this.socket.send(JSON.stringify(this.session));
        cb(true);
      } else {
        this.updating = false;
        return cb(false);
      }
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
      this.authenticate(() => {
        this.session.dateStarted = new Date().toISOString();
        this.update(res => {
          this.$nextTick().then(res => this.startTimer(fiveMinutes));
        });
      });
    },
    connectionClosed($event) {
      this.$router.push({
        name: "problem-detail",
        params: { id: this.$hash.encode(this.problem.id) }
      });
    },
    connectionMessage($event) {
      this.sessionId = $event.data;
    },
    connectionError($event) {
      this.$router.push({
        name: "problem-detail",
        params: { id: this.$hash.encode(this.problem.id) }
      });
    },
    connect() {
      this.socket = new WebSocket(baseSocketURL);
      this.socket.addEventListener("open", this.connectionOpened);
      this.socket.addEventListener("close", this.connectionClosed);
      this.socket.addEventListener("message", this.connectionMessage);
      this.socket.addEventListener("error", this.connectionError);
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
    session: {
      handler: function(value) {
        if (!this.arrayEqual(previousAnswer, value.answers)) {
          previousAnswer = this.copyArray(value.answers);
          this.update(() => {});
        }
      },
      deep: true
    }
  }
};
</script>
