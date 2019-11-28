<template>
  <div
    class="d-flex-column justify-content-start btn btn-secondary btn-block my-1 p-4 pointed"
    @click="showProblem(p.id)"
  >
    <div class="d-flex justify-content-between align-items-center">
      <h4>
        {{ p.title }}
      </h4>
      <a
        href="#"
        class="badge badge-info text-right"
        @click.stop="getProlemsInCategory(p.category.id)"
      >
        {{ p.category.name }}
      </a>
    </div>
    <p class="text-left">
      {{ p.description }}
    </p>
    <div class="d-flex justify-content-between align-items-center">
      <div class="d-flex justify-content-between align-items-center">
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

      <div class="ml-4 text-right">
        <small style="color: #4385b1;">{{
          attempted === true ? "Attempted" : "Non Attempted"
        }}</small>
      </div>
    </div>
  </div>
</template>

<script>
import shave from "shave";

export default {
  props: {
    p: {
      type: Object,
      required: true
    }
  },
  name: "Problem",
  data() {
    return {
      attempted: null
    };
  },
  methods: {
    hasAttemptedProbelm(pId) {
      const url = `users/current/hasAttempted?problemId=${pId}`;

      this.$http.get(url).then(res => {
        this.attempted = res.data;
      });
    },
    showProblem(pId) {
      this.$emit("prob", pId);
    },
    getProlemsInCategory(catId) {
      this.$emit("cat", catId);
    },
    shorten() {
      shave(".desc", 3);
    }
  },
  computed: {
    comments() {
      return this.p.reviews.filter(rev => rev.reviewType === "COMMENT");
    },
    stars() {
      return this.p.reviews.filter(rev => rev.reviewType === "STAR");
    }
  },
  created() {
    this.shorten();
    this.hasAttemptedProbelm(this.p.id);
  }
};
</script>
