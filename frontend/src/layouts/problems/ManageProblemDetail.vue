<template>
  <div class="col col-md-8">
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

      <hr class="my-4" />

      <div class="ml-4">
        <p class="text-primary font-weight-500">Reviews:</p>

        <div class="d-flex justify-content-start align-items-center mt-4">
          <div>
            <button
                    class="btn btn-icon btn-2 btn-outline-secondary p-0"
                    type="button"
            >
              <span class="btn-inner--icon"
              ><i class="fas fa-star" style="top: 0;"></i
              ></span>
            </button>
            <span style="color: #4385b1;">{{ stars.length }} stars</span>
          </div>

          <div class="ml-4">
            <button
                    class="btn btn-icon btn-2 btn-outline-secondary p-0"
                    type="button"
            >
              <span class="btn-inner--icon"
              ><i class="fas fa-comment" style="top: 0;"></i
              ></span>
            </button>
            <span style="color: #4385b1;">{{ comments.length }} comments</span>
          </div>

        </div>

        <div class="mt-4">
          <div>
            <div class="ui comments">
              <h3 class="ui dividing header">Comments</h3>
              <div
                      class="comment"
                      v-for="(comment, ci) in reviewComments"
                      :key="comment.id"
              >
                <a class="avatar">
                  <img :src="commentUsers[ci].avatarLink" />
                </a>
                <div class="content">
                  <a class="author">{{commentUsers[ci].fullName}}</a>
                  <div class="metadata">
                    <span class="date">Today at 5:42PM</span>
                  </div>
                  <div class="text">
                    {{ comment.commentContent }}
                  </div>
                </div>
              </div>

              <form class="text-right">
                <textarea
                        class="form-control mt-4"
                        rows="3"
                        placeholder="Add your comment here..."
                        v-model="commentContent"
                ></textarea>

                <button
                        type="button"
                        class="btn btn-primary mt-4 text-right"
                        @click="addComment(problem.id)"
                        :disabled="commentLoading"
                >
                  <span
                          class="spinner-grow spinner-grow-sm"
                          role="status"
                          aria-hidden="true"
                          v-if="commentLoading"
                  ></span>
                  Add Comment
                </button>
              </form>
            </div>
          </div>
        </div>
      </div>

      <hr class="my-4" />

      <div class="d-flex-column justify-content-start px-4 mt-2">
        <p class="text-primary font-weight-500">Reading:</p>

        <p class="mt-2 pl-4">{{problem.knowledgeSource.content}}</p>
      </div>

      <div class="d-flex-column justify-content-start p-4 mt-2">

        <div>
          <h4 class="mb-4 text-primary">Questions:</h4>

          <div v-for="(que, qi) in problem.questions" :key="que.id" class="mb-4">
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
                :value="i"
                disabled
              />
              <label class="custom-control-label"
                     :for="`category-radio-${qi}-${i}`">
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
      problem: {
        reviews: []
      },
      dateStarted: "",
      dateSubmitted: "",
      deleteContent: {},
      deleteAction: null,
      commentLoading: false,
      commentContent: "",
      reviewComments: [],
      commentUsers: [],
    };
  },
  methods: {
    deleteProblem() {
      this.deleteContent = {
        name: this.problem.title
      };
      const pId = this.$hash.decode(this.id)[0];
      this.deleteAction = () => this.doDeleteProblem(pId);
      this.$refs["delete"].show();
    },
    doDeleteProblem(pId) {
      const url = `problems/${pId}/archive`;

      this.$http.get(url).then(res => {
        this.$router.replace({ name: "manage-problems" });
      });
    },
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
    },
    getComments(pId) {
      const url = `reviews/comments?problemId=${pId}`;

      this.$http.get(url).then(res => {
        const comments = res.data;
        for (let i = 0; i < comments.length; i++) {
          this.commentUsers.push({});
          this.getUser(comments[i].user, i);
        }
        this.reviewComments = comments;
      });
    },
    getUser(userId, index) {
      const url = `users/${userId}`;
      return this.$http
              .get(url)
              .then(res => {
                this.commentUsers.splice(index, 1, res.data);
              })
              .catch(err => {
                return err;
              });
    },
  },
  created() {
    const pId = this.$hash.decode(this.id)[0];
    this.getProblem(pId);
    this.getComments(pId);
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
  computed: {
    comments() {
      return this.problem.reviews.filter(rev => rev.reviewType === "COMMENT");
    },
    stars() {
      return this.problem.reviews.filter(rev => rev.reviewType === "STAR");
    }
  },
  components: {
    Delete
  }
};
</script>
