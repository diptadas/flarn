<template>
  <div class="col col-md-8">
    <div>
      <p :class="[{ 'text-warning': timeSmall }]">
        Time Remaining: {{ timeleft }}
      </p>
    </div>
    <hr class="my-4" />

    <div v-if="problem.id">
      <div
        class="d-flex-column justify-content-start p-4"
        @click="showProblem(problem.id)"
      >
        <div class="d-flex justify-content-between align-items-center">
          <h4>
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

      <div class="d-flex-column justify-content-start px-4">
        <div>Knowledge Source Here</div>

        <hr class="my-4" />

        <div>
          <h4 class="mb-4">Questions:</h4>

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
                v-model="answers[qi]"
                :value="opt"
              />
              <label
                class="custom-control-label"
                :for="`category-radio-${qi}-${i}`"
              >
                {{ opt }}
              </label>
            </div>
          </div>
        </div>
      </div>

      <hr class="my-4" />
      <div class="text-right mt-4">
        <button type="button" class="btn btn-primary" @click="submit">
          Submit Session
        </button>
      </div>
    </div>
  </div>
</template>

<script>
// window.onbeforeunload = function() {
//   return "Are you sure you want to navigate away?";
// };

const fiveMinutes = 60 * 5;

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
      answers: ["", "", ""],
      problem: {},
      timeleft: "_ _:_ _",
      timer: null,
      submitting: false,
      dateStarted: "",
      dateSubmitted: "",
      timeSmall: false
    };
  },
  methods: {
    submit() {
      if (this.submitting) return false;
      this.submitting = true;

      const data = {
        answers: this.answers,
        problem: {
          id: this.problem.id
        },
        dateStarted: this.dateStarted,
        dateSubmitted: new Date().toISOString()
      };

      const url = `sessions`;

      this.$http.post(url, data).then(res => {
        this.$router.replace({ name: "problems" });
      });
    },
    getProblem(id) {
      const url = `problems/${id}`;

      this.$http.get(url).then(res => {
        this.problem = res.data;
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
      this.dateStarted = new Date().toISOString();
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
  mounted() {
    this.startTimer(fiveMinutes);
  },
  beforeDestroy() {
    console.log("before destroy");
    clearTimeout(this.timer);
    this.timer = null;
  }
};
</script>
