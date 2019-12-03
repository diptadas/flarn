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
                                    <small>Login in with credentials</small>
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
                                    <form @submit.prevent="handleSubmit(loginUser)">
                                        <ValidationProvider
                                                name="Email Address"
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
                                                            placeholder="Email"
                                                            required
                                                            type="email"
                                                            v-model="user.username"
                                                    />
                                                </div>
                                                <small class="text-danger">
                                                    {{ v.errors[0] }}
                                                </small>
                                            </div>
                                        </ValidationProvider>

                                        <ValidationProvider
                                                name="Password"
                                                rules="required"
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
                                                            placeholder="Password"
                                                            required
                                                            type="password"
                                                            v-model="user.password"
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
                                                Login To Account
                                            </button>
                                        </div>
                                        <div class="text-center text-muted mt-4 text-underline">
                                            <small
                                            >
                                                <router-link :to="{ name: 'forgot' }"
                                                >Forgot my password
                                                </router-link
                                                >
                                            </small
                                            >
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
        name: "Login",
        props: {
            message: {
                type: String,
                required: false
            }
        },
        data() {
            return {
                user: {
                    username: "",
                    password: "",
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
            loginUser() {
                if (this.loading) return false;
                this.loading = true;
                // validate data

                const url = "auth/login";

                this.$http
                    .post(url, this.user)
                    .then(res => {
                        const data = res.data;

                        this.$store.commit("SET_AUTH", {
                            username: this.email,
                            token: data.token,
                            userId: data.userId
                        });

                        localStorage.setItem("auth_token", data.token);
                        localStorage.setItem("username", data.email);
                        localStorage.setItem("user_id", data.userId);

                        this.$router.push({
                            name: "home"
                        });
                    })
                    .catch(err => {
                        if (err.error) {
                            console.log(err.error);
                        }
                        const msg = this.errorMessage(err);
                        if (msg === "Bad credentials") {
                            this.error.text = "Invalid username and password";
                        } else if (msg === "User is disabled") {
                            this.$store.commit("SET_USERNAME", this.email);
                            return this.$router.replace({name: "confirm"});
                        } else {
                            this.error.text = msg;
                        }
                        this.error.type = "error";
                        this.error.state = true;
                    })
                    .finally(() => (this.loading = false));
            }
        },
        created() {
            if (this.message) {
                this.error.text = this.message;
                this.error.type = "info";
                this.error.state = true;
            }
        }
    };
</script>
