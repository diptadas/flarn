<template>
    <div>
        <main>
            <section class="section section-shaped section-lg">
                <div class="shape shape-style-1 bg-gradient-default">
                    <span></span>
                    <span></span>
                    <span></span>
                    <span></span>
                    <span></span>
                    <span></span>
                    <span></span>
                    <span></span>
                </div>
                <div class="container pt-lg-md h-screen">
                    <div class="row justify-content-center">
                        <div class="col-lg-7">
                            <div class="card bg-secondary shadow border-0">
                                <div class="card-body px-lg-5 py-lg-5">
                                    <div class="text-center text-muted mb-4">
                                        <small>Change your password here</small>
                                    </div>

                                    <div
                                            :class="
                      error.type === 'error' ? 'alert-danger' : 'alert-default'
                    "
                                            class="alert alert-dismissible fade show"
                                            role="alert"
                                            v-if="error.state"
                                    >
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

                                    <ValidationObserver v-slot="{ handleSubmit }">
                                        <form @submit.prevent="handleSubmit(submit)">
                                            <ValidationProvider
                                                    name="Activation Code"
                                                    rules="required|digits:4"
                                                    v-slot="v"
                                            >
                                                <div class="form-group">
                                                    <div class="input-group input-group-alternative">
                                                        <div class="input-group-prepend">
                              <span class="input-group-text"
                              ><i class="fa fa-key"></i
                              ></span>
                                                        </div>
                                                        <input
                                                                class="form-control"
                                                                placeholder="Activation Code"
                                                                type="number"
                                                                v-model="recover.confirmationCode"
                                                        />
                                                    </div>
                                                    <small class="text-danger">
                                                        {{ v.errors[0] }}
                                                    </small>
                                                </div>
                                            </ValidationProvider>

                                            <ValidationProvider
                                                    name="Password"
                                                    rules="required|min:4"
                                                    v-slot="v"
                                                    vid="confirmation"
                                            >
                                                <div class="form-group">
                                                    <div class="input-group input-group-alternative">
                                                        <div class="input-group-prepend">
                              <span class="input-group-text"
                              ><i class="ni ni-lock-circle-open"></i
                              ></span>
                                                        </div>
                                                        <input
                                                                class="form-control"
                                                                placeholder="Password"
                                                                type="password"
                                                                v-model="recover.newPassword"
                                                        />
                                                    </div>
                                                    <small class="text-danger">
                                                        {{ v.errors[0] }}
                                                    </small>
                                                </div>
                                            </ValidationProvider>

                                            <ValidationProvider
                                                    name="Password Confirmation"
                                                    rules="confirmed:confirmation"
                                                    v-slot="v"
                                            >
                                                <div class="form-group">
                                                    <div class="input-group input-group-alternative">
                                                        <div class="input-group-prepend">
                              <span class="input-group-text"
                              ><i class="ni ni-lock-circle-open"></i
                              ></span>
                                                        </div>
                                                        <input
                                                                class="form-control"
                                                                placeholder="Confirm Password"
                                                                type="password"
                                                                v-model="confirmation"
                                                        />
                                                    </div>
                                                    <small class="text-danger">
                                                        {{ v.errors[0] }}
                                                    </small>
                                                </div>
                                            </ValidationProvider>

                                            <div class="text-center">
                                                <button class="btn btn-primary mt-4" type="submit">
                                                    Submit
                                                </button>
                                            </div>
                                            <div class="text-center text-muted mt-4 text-underline">
                                                <small>
                                                    <a @click="sendConfirmation">
                                                        Send me activation code again
                                                    </a>
                                                </small>
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
    </div>
</template>

<script>
    export default {
        name: "Forgot",
        props: {
            message: {
                type: String,
                required: false
            }
        },
        data() {
            return {
                confirmation: "",
                error: {
                    state: false,
                    text: "",
                    type: false
                },
                recover: {
                    username: '',
                    newPassword: '',
                    confirmationCode: ''
                }
            };
        },
        methods: {
            sendConfirmation() {
                // validate data

                if (!this.email) return;

                const url = `auth/sendConfirmationCode/?username=${this.email}`;

                this.$http
                    .get(url)
                    .then(res => {
                        this.error.text = "Email has been resent";
                        this.error.type = "info";
                        this.error.state = true;
                    })
                    .catch(err => {
                        console.log(err);
                        this.error.text = this.errorMessage(err);
                        this.error.type = "error";
                        this.error.state = true;
                    });
            },
            submit() {
                // validate data

                const url = "auth/updatePassword";

                this.$http
                    .post(url, this.recover)
                    .then(res => {
                        this.$router.push({
                            name: "login",
                            params: {message: "Password recovery successful."}
                        });
                    })
                    .catch(err => {
                        console.log(err);
                        this.error.text = this.errorMessage(err);
                        this.error.type = "error";
                        this.error.state = true;
                    });
            }
        },
        created() {
            this.recover.username = this.$store.state.username;
            if (!this.recover.username) return this.$router.replace({name: "forgot"});
        }
    };
</script>
