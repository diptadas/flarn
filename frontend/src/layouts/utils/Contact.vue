<template>
    <main>
        <div class="position-relative">
            <!-- shape Hero -->
            <section class="section section-lg section-shaped pb-250">
                <div class="shape shape-style-1 shape-default">
                    <span></span>
                    <span></span>
                    <span></span>
                    <span></span>
                    <span></span>
                    <span></span>
                    <span></span>
                    <span></span>
                    <span></span>
                </div>
                <div class="container py-lg-md d-flex">
                    <div class="col px-0">
                        <div class="row">
                            <div class="col-lg-6">
                                <h1 class="display-3  text-white">
                                    Got A Question?<span>Contact Flarn</span>
                                </h1>
                                <p class="lead  text-white">
                                    We're here to help and answer any questions you might have. We look forward to
                                    hearing from you &#128539;
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- SVG separator -->
                <div class="separator separator-bottom separator-skew">
                    <svg
                            preserveAspectRatio="none"
                            version="1.1"
                            viewBox="0 0 2560 100"
                            x="0"
                            xmlns="http://www.w3.org/2000/svg"
                            y="0"
                    >
                        <polygon
                                class="fill-white"
                                points="2560 0 2560 100 0 100"
                        ></polygon>
                    </svg>
                </div>
            </section>
            <!-- 1st Hero Variation -->
        </div>
        <section class="section section-lg pt-lg-0 section-contact-us">
            <div class="container">
                <div class="row justify-content-center mt--300">
                    <div class="col-lg-8">
                        <div class="card bg-gradient-secondary shadow">
                            <div class="card-header bg-white border-0" v-if="error.state">
                                <div
                                        :class="error.type === 'error' ? 'alert-danger' : 'alert-default'"
                                        class="alert alert-dismissible fade show"
                                        role="alert">
                    <span class="alert-inner--icon"
                    ><i class="ni ni-like-2"></i
                    ></span>
                                    <span class="alert-inner--text">{{ error.text }}</span>
                                    <button
                                            @click="error.state = false"
                                            aria-label="Close"
                                            class="close"
                                            data-dismiss="alert"
                                            type="button"
                                    >
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                            </div>
                            <div class="card-body p-lg-5">
                                <h4 class="mb-1">Want to work with us?</h4>
                                <p class="mt-0">Your project is very important to us.</p>

                                <ValidationObserver v-slot="{ handleSubmit }">
                                    <form @submit.prevent="handleSubmit(submit)">

                                        <ValidationProvider
                                                name="Your name"
                                                rules="required"
                                                v-slot="v"
                                        >
                                            <div class="form-group mt-5">
                                                <div class="input-group input-group-alternative">
                                                    <div class="input-group-prepend">
                      <span class="input-group-text"
                      ><i class="ni ni-user-run"></i
                      ></span>
                                                    </div>
                                                    <input
                                                            class="form-control"
                                                            placeholder="Your name"
                                                            required
                                                            type="text"
                                                            v-model="payload.name"
                                                    />
                                                </div>
                                                <small class="text-danger">
                                                    {{ v.errors[0] }}
                                                </small>
                                            </div>
                                        </ValidationProvider>

                                        <ValidationProvider
                                                name="Email address"
                                                rules="required|email"
                                                v-slot="v"
                                        >
                                            <div class="form-group">
                                                <div class="input-group input-group-alternative">
                                                    <div class="input-group-prepend">
                      <span class="input-group-text"
                      ><i class="ni ni-email-83"></i
                      ></span>
                                                    </div>
                                                    <input
                                                            class="form-control"
                                                            placeholder="Email address"
                                                            required
                                                            type="email"
                                                            v-model="payload.email"
                                                    />
                                                </div>
                                                <small class="text-danger">
                                                    {{ v.errors[0] }}
                                                </small>
                                            </div>
                                        </ValidationProvider>

                                        <ValidationProvider
                                                name="Message"
                                                rules="required"
                                                v-slot="v"
                                        >
                                            <div class="form-group mb-4">
                  <textarea
                          class="form-control form-control-alternative"
                          cols="80"
                          name="name"
                          placeholder="Type a message..."
                          required
                          rows="4"
                          v-model="payload.message"
                  ></textarea>
                                                <small class="text-danger">
                                                    {{ v.errors[0] }}
                                                </small>
                                            </div>
                                        </ValidationProvider>

                                        <div>
                                            <button
                                                    class="btn btn-default btn-round btn-block btn-lg"
                                                    type="submit"
                                            >
                       <span
                               aria-hidden="true"
                               class="spinner-grow spinner-grow-sm"
                               role="status"
                               v-if="loading"
                       ></span>
                                                Send Message
                                            </button>
                                        </div>
                                    </form>
                                </ValidationObserver>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </main>
</template>

<script>
    export default {
        name: "Contact",
        data() {
            return {
                loading: false,
                payload: {
                    name: '',
                    email: '',
                    message: ''
                },
                error: {
                    state: false,
                    text: "",
                    type: false
                },
            }
        },
        methods: {
            submit() {
                if (this.loading) return;
                this.loading = true;

                const url = `auth/contact`;

                this.$http
                    .post(url, this.payload)
                    .then(res => {
                        this.error.text = "We've received your message. Thank you.";
                        this.error.type = "info";
                        this.error.state = true;
                    })
                    .catch(err => {
                        console.log(err);
                        this.error.text = this.errorMessage(err);
                        this.error.type = "error";
                        this.error.state = true;
                    })
                    .finally(() => this.loading = false);
            }
        }
    };
</script>
