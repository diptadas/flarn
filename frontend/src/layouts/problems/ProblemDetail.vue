<template>
  <div class="row mt-4">
    <div class="col col-md-8">
      <div v-if="problem.id">
        <div
          class="d-flex-column justify-content-start"
          @click="showProblem(problem.id)"
        >
          <div class="d-flex justify-content-between align-items-center">
            <h4>
              {{ problem.title }}
            </h4>
            <a
              href="#"
              class="badge badge-info text-right"
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

          <div class="ml-4" v-if="attempted === true">
            <router-link style="color: #4385b1;" :to="{name: 'session-result', params: {id: $hash.encode(sessionId)}}" class="text-underline">View submission</router-link>
          </div>

        </div>

        <div class="text-right mt-4" v-if="attempted === false">
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

        <div class="mt-4" v-if="attempted === true">
          <button
            type="button"
            class="btn btn-info btn-sm"
            @click="
              hasStarred === true
                ? unStarProblem(problem.id)
                : starProblem(problem.id)
            "
          >
            <i class="fas fa-star" style="top: 0;"></i>
            {{ hasStarred ? "Remove Star" : "Star Problem" }}
          </button>

          <div class="mt-4">
            <div class="ui comments">
              <h3 class="ui dividing header text-primary">Comments</h3>
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
      commentLoading: false,
      problem: {
        reviews: []
      },
      attempted: null,
      hasStarred: null,
      commentContent: "",
      sessionId: -1,
      reviewComments: [],
      commentUsers: []
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
    addComment(pId) {
      if (this.commentLoading) return;
      this.commentLoading = true;

      const url = `reviews/comment`;
      const data = {
        problem: {
          id: pId
        },
        commentContent: this.commentContent
      };

      this.$http
        .post(url, data)
        .then(res => {
          this.commentContent = "";
          this.problem.reviews.push(res.data);

          this.commentUsers.push({});
          const index = this.commentUsers.length - 1;
          this.getUser(res.data.user, index);
          this.reviewComments.push(res.data);
        })
        .finally(() => (this.commentLoading = false));
    },
    starProblem(pId) {
      const url = `reviews/star?problemId=${pId}`;

      this.$http.get(url).then(res => {
        this.problem.reviews.push(res.data);
        this.hasStarred = true;
      });
    },
    unStarProblem(pId) {
      const url = `reviews/unstar?problemId=${pId}`;

      this.$http.get(url).then(res => {
        // TODO: find review for star and pId and remove
        const userId = this.$store.state.userId;
        const revIndex = this.problem.reviews.findIndex(
          rev => rev.problem.id === pId && rev.user.id === userId
        );
        this.problem.reviews.splice(revIndex, 1);
        this.hasStarred = false;
      });
    },
    startProblem() {
      if (this.loading) return;
      this.loading = true;
      this.$router.push({ name: "problem-session", params: { id: this.id } });
    },
    getProblem(id) {
      const url = `problems/${id}`;

      this.$http.get(url).then(res => {
        this.problem = res.data;
        this.hasAttemptedProblem(this.problem.id);
        this.hasStaredProblem(this.problem.id);
      });
    },
    hasAttemptedProblem(pId) {
      const url = `users/current/hasAttempted?problemId=${pId}`;

      this.$http.get(url).then(res => {
        this.attempted = res.data;

        if (this.attempted === true) {
          this.getComments(pId);
          this.getSession(pId);
        }
      });
    },
    getSession(pId){
      const url = `sessions/problems?problemId=${pId}`;

      this.$http.get(url).then(res => {
        this.sessionId = res.data;
      });
    },
    hasStaredProblem(pId) {
      const url = `reviews/hasStared?problemId=${pId}`;

      this.$http.get(url).then(res => {
        this.hasStarred = res.data;
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
    ownProblem() {
      const userId = this.$store.state.userId;
      const modId = this.problem.moderator;

      return Number(userId) === Number(modId);
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
  created() {
    if (this.ownProblem()) {
      return this.$router.replace({
        name: "manage-problems-detail",
        params: { id: this.$hash.encode(id) }
      });
    }
    this.getProblem(this.$hash.decode(this.id)[0]);
  }
};
</script>
