<template>
    <div class="col col-md-8">
        <div v-if="problem.id">
            <div
                    @click="showProblem(problem.id)"
                    class="d-flex-column justify-content-start p-4"
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

            <hr class="my-4"/>

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
                                    :key="comment.id"
                                    class="comment"
                                    v-for="(comment, ci) in reviewComments"
                            >
                                <a class="avatar">
                                    <img :src="commentUsers[ci].avatarLink"/>
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
                        placeholder="Add your comment here..."
                        rows="3"
                        v-model="commentContent"
                ></textarea>

                                <button
                                        :disabled="commentLoading"
                                        @click="addComment(problem.id)"
                                        class="btn btn-primary mt-4 text-right"
                                        type="button"
                                >
                  <span
                          aria-hidden="true"
                          class="spinner-grow spinner-grow-sm"
                          role="status"
                          v-if="commentLoading"
                  ></span>
                                    Add Comment
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            <hr class="my-4"/>

            <div class="d-flex-column justify-content-start px-4 mt-2">
                <p class="text-primary font-weight-500">Reading:</p>

                <p class="mt-2 pl-4">{{problem.knowledgeSource.content}}</p>
            </div>

            <div class="d-flex-column justify-content-start p-4 mt-2">

                <div>
                    <h4 class="mb-4 text-primary">Questions:</h4>

                    <div :key="que.id" class="mb-4" v-for="(que, qi) in problem.questions">
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
                                    disabled
                                    type="radio"
                                    v-model="answers[qi]"
                            />
                            <label :for="`category-radio-${qi}-${i}`"
                                   class="custom-control-label">
                                {{ opt }}
                            </label>
                        </div>
                    </div>
                </div>
            </div>

            <hr class="my-4"/>
            <div class="row d-inline-flex justify-content-between w-100">
                <div class="text-left mt-4">
                    <button
                            :disabled="archLoading"
                            @click="deleteProblem"
                            class="btn btn-danger"
                            type="button"
                    >
            <span
                    aria-hidden="true"
                    class="spinner-grow spinner-grow-sm"
                    role="status"
                    v-if="archLoading"
            ></span>
                        Archive Problem
                    </button>
                </div>
                <div class="text-right mt-4">
                    <button
                            :disabled="editLoading"
                            @click="edit"
                            class="btn btn-primary"
                            type="button"
                    >
            <span
                    aria-hidden="true"
                    class="spinner-grow spinner-grow-sm"
                    role="status"
                    v-if="editLoading"
            ></span>
                        Edit Problem
                    </button>
                </div>
            </div>
        </div>
        <Delete :action="deleteAction" :content="deleteContent" ref="delete"/>
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
                    this.$router.replace({name: "manage-problems"});
                });
            },
            edit() {
                if (this.editLoading) return false;
                this.editLoading = true;
                this.$router
                    .push({
                        name: "manage-problem-edit",
                        params: {id: this.id}
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
