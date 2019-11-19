<template>
  <div class="row">
    <div class="col col-md-8">
      <div
        class="d-flex align-items-start profile-feed-item card my-2 p-2 pointed"
      >
        <div>
          <h6>
            {{ problem.title }}

            <a
              href="#"
              class="badge badge-default"
              @click.stop="
                getProlemsInCategory(
                  problem.category ? problem.category.id : ''
                )
              "
              >{{ problem.category ? problem.category.name : "" }}</a
            >
          </h6>
          <p>
            {{ problem.description }}
          </p>
          <p>
            <small class="text-muted">Moderator:</small>
            {{ moderator }}
          </p>
          <p>
            {{ problem.description }}
          </p>
          <!-- <p>
        {{ problem.description }}
      </p> -->
        </div>
      </div>

      <div class="text-right">
        <button
          type="button"
          class="btn btn-primary mt-4"
          @click="startProblem"
        >
          Start Problem
        </button>
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
  computed: {},
  methods: {
    startProblem() {},
    getProblem(id) {
      const url = `problemsets/${id}`;

      this.$http
        .get(url)
        .then(res => {
          this.problem = res.data;
        })
        .catch(err => {});
    }
  },
  created() {
    this.getProblem(this.$hash.decode(this.id)[0]);
  }
};
</script>

<style lang="scss" scoped></style>
