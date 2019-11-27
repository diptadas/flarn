<template>
  <div class="row">
    <div class="col col-md-8">
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
          <small class="text-muted text-left"
            >Created by: {{ moderator }}</small
          >
        </div>

        <div class="text-right mt-4">
          <button
            type="button"
            class="btn btn-primary"
            @click="startProblem"
            :disabled="loading"
          >
            <span
              class="spinner-grow spinner-grow-sm"
              role="status"
              aria-hidden="true"
              v-if="loading"
            ></span>
            Start Problem
          </button>
        </div>
      </div>
    </div>
    <div class="col col-md-4"></div>
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
  name: "ProblemDetail",
  data() {
    return {
      loading: false,
      problem: {}
    };
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
  methods: {
    startProblem() {
      if (this.loading) return;
      this.loading = true;
      this.$router.push({ name: "problem-session", params: { id: this.id } });
    },
    getProblem(id) {
      const url = `problems/${id}`;

      this.$http.get(url).then(res => {
        this.problem = res.data;
      });
    }
  },
  created() {
    this.getProblem(this.$hash.decode(this.id)[0]);
  }
};
</script>
