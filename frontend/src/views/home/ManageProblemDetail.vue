<template>
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

        <small class="text-muted text-left">Created by: {{ moderator }}</small>
      </div>

      <div class="d-flex-column justify-content-start px-4">
        <div>Knowledge Source Here</div>

        <hr class="my-4" />

        <div>
          <h4 class="mb-4">Questions:</h4>

          <div v-for="(que, i) in problem.questions" :key="que.id" class="mb-4">
            {{ i + 1 }}. {{ que.content }}

            <div
              class="custom-control custom-radio my-3 ml-4"
              v-for="(opt, i) in que.options"
              :key="i"
            >
              <input
                name="category"
                class="custom-control-input"
                :id="`category-radio-${i}`"
                type="radio"
                v-model="answers[i]"
                :value="i"
              />
              <label class="custom-control-label" :for="`category-radio-${i}`">
                {{ opt }}
              </label>
            </div>
          </div>
        </div>
      </div>

      <hr class="my-4" />
      <div class="row d-inline-flex justify-content-between w-100">
        <div class="text-left mt-4">
          <button
            type="button"
            class="btn btn-danger"
            @click="deleteProblem"
            :disabled="archLoading"
          >
            <span
              class="spinner-grow spinner-grow-sm"
              role="status"
              aria-hidden="true"
              v-if="archLoading"
            ></span>
            Archive Problem
          </button>
        </div>
        <div class="text-right mt-4">
          <button
            type="button"
            class="btn btn-primary"
            @click="edit"
            :disabled="editLoading"
          >
            <span
              class="spinner-grow spinner-grow-sm"
              role="status"
              aria-hidden="true"
              v-if="editLoading"
            ></span>
            Edit Problem
          </button>
        </div>
      </div>
    </div>
    <Delete ref="delete" :content="deleteContent" :action="deleteAction" />
  </div>
</template>

<script>
import Delete from "@/components/utils/Delete.vue";

export default {
  props: {
    id: {
      type: String,
      required: true
    }
  },
  name: "ManageProblemDetail",
  data() {
    return {
      archLoading: false,
      editLoading: false,
      answers: [],
      problem: {},
      dateStarted: "",
      dateSubmitted: "",
      deleteContent: {},
      deleteAction: null
    };
  },
  methods: {
    deleteProblem() {
      this.deleteContent = {
        name: this.problem.title
      };
      this.deleteAction = () => this.doDeleteProblem();
      this.$refs["delete"].show();
    },
    doDeleteProblem(params) {},
    edit() {
      if (this.editLoading) return false;
      this.editLoading = true;
      this.$router
        .push({
          name: "manage-problem-edit",
          params: { id: this.id }
        })
        .finally(() => (this.editLoading = false));
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
  components: {
    Delete
  }
};
</script>
