<template>
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
                                    <p>Confirm your account</p>
                                    <small>We've sent you an activation code to your email</small>
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
                  ><i class="ni ni-bell-55"></i
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
                                    <form @submit.prevent="handleSubmit(confirm)">
                                        <ValidationProvider
                                                name="Email Address"
                                                rules="required|email"
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
                                                            placeholder="Email"
                                                            required
                                                            type="email"
                                                            v-model="conf.username"
                                                    />
                                                </div>
                                                <small class="text-danger">
                                                    {{ v.errors[0] }}
                                                </small>
                                            </div>
                                        </ValidationProvider>

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
                                                            required
                                                            type="number"
                                                            v-model="conf.confirmationCode"
                                                    />
                                                </div>
                                                <small class="text-danger">
                                                    {{ v.errors[0] }}
                                                </small>
                                            </div>
                                        </ValidationProvider>
                                        <div class="text-center">
                                            <button
                                                    :disabled="loading"
                                                    class="btn btn-primary mt-4"
                                                    type="submit"
                                            >
                        <span
                                aria-hidden="true"
                                class="spinner-grow spinner-grow-sm"
                                role="status"
                                v-if="loading"
                        ></span>
                                                Confirm Account
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
</template>

<script>
    export default {
        name: "Confirm",
        props: {
            message: {
                type: String,
                required: false
            }
        },
        data() {
            return {
                conf: {
                    username: '',
                    confirmationCode: ''

                },
                loading: false,
                error: {
                    state: false,
                    text: "",
                    type: false
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
                        this.error.text = "Activation code has been resent to your email";
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
            confirm() {
                // validate data

                const url = "auth/confirm";

                this.$http
                    .post(url, this.conf)
                    .then(res => {
                        const data = res.data;

                        this.$router.push({
                            name: "login",
                            params: {message: "Account registration success"}
                        });
                    })
                    .catch(err => {
                        console.log(err);
                        const mess = this.errorMessage(err);
                        this.error.text = mess;
                        this.error.type = "error";
                        this.error.state = true;
                    });
            }
        },
        created() {
            this.conf.username = this.$store.state.username;
        }
    };
</script>
