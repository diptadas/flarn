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
                                        <small>Or sign up with credentials</small>
                                    </div>

                                    <div
                                            :class="
                      error.type === 'error' ? 'alert-danger' : 'alert-default'
                    "
                                            class="alert alert-dismissible fade show"
                                            role="alert"
                                            v-if="error.state"
                                    >
                    <span class="alert-inner--icon">
                      <i class="ni ni-like-2"></i>
                    </span>
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
                                        <form @submit.prevent="handleSubmit(registerUser)">
                                            <ValidationProvider
                                                    name="Full Name"
                                                    rules="required|alpha_spaces"
                                                    v-slot="v"
                                            >
                                                <div class="form-group">
                                                    <div class="input-group input-group-alternative">
                                                        <div class="input-group-prepend">
                              <span class="input-group-text"
                              ><i class="ni ni-hat-3"></i
                              ></span>
                                                        </div>
                                                        <input
                                                                class="form-control"
                                                                placeholder="Full Name"
                                                                required
                                                                type="text"
                                                                v-model="reg.fullName"
                                                        />
                                                    </div>
                                                    <small class="text-danger">
                                                        {{ v.errors[0] }}
                                                    </small>
                                                </div>
                                            </ValidationProvider>

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
                                                                v-model="reg.username"
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
                                                                minlength="4"
                                                                placeholder="Password"
                                                                required
                                                                type="password"
                                                                v-model="reg.password"
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
                                                                required
                                                                type="password"
                                                                v-model="confirmation"
                                                        />
                                                    </div>
                                                    <small class="text-danger">
                                                        {{ v.errors[0] }}
                                                    </small>
                                                </div>
                                            </ValidationProvider>

                                            <div class="text-muted">
                                                <small
                                                >password strength:
                                                    <span :class="passColor" class="font-weight-700"
                                                    >{{ passStrength }}
                          </span></small
                                                >
                                            </div>
                                            <div class="row my-4">
                                                <ValidationProvider
                                                        name="Terms"
                                                        rules="required"
                                                        v-slot="v"
                                                >
                                                    <div class="col-12">
                                                        <div
                                                                class="custom-control custom-control-alternative custom-checkbox"
                                                        >
                                                            <input
                                                                    class="custom-control-input"
                                                                    id="customCheckRegister"
                                                                    required
                                                                    type="checkbox"
                                                                    v-model="terms"
                                                            />
                                                            <label
                                                                    class="custom-control-label"
                                                                    for="customCheckRegister"
                                                            ><span
                                                            >I agree with the
                                  <a href="https://github.com/diptadas/flarn/blob/master/docs/LICENSE" target="_blank">Privacy Policy</a></span
                                                            ></label
                                                            >
                                                        </div>
                                                        <small class="text-danger">
                                                            {{ v.errors[0] }}
                                                        </small>
                                                    </div>
                                                </ValidationProvider>
                                            </div>
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
                                                    Create account
                                                </button>
                                            </div>
                                            <div class="text-center text-muted mt-4 text-underline">
                                                <small>
                                                    <router-link :to="{ name: 'login' }">
                                                        Login instead
                                                    </router-link>
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
        name: "Register",
        data() {
            return {
                loading: false,
                confirmation: "",
                terms: false,
                error: {
                    state: false,
                    text: "",
                    type: false
                },
                passStrength: "",
                passColor: "danger",
                reg: {
                    username: this.email,
                    fullName: this.name,
                    password: this.password
                }
            }
        },
        watch: {
            reg: {
                handler: function (val, oldVal) {
                    if (val.password) {
                        this.checkPassStrength(val.password);
                    }
                },
                deep: true
            }
        },
        methods: {
            registerUser() {
                if (this.loading) return false;
                this.loading = true;
                // validate data

                const url = "auth/register";

                this.$http
                    .post(url, this.reg)
                    .then(res => {
                        this.$store.commit("SET_USERNAME", res.data.username);
                        this.$router.push({
                            name: "confirm",
                            params: {message: "Account registration success"}
                        });
                    })
                    .catch(err => {
                        const mess = this.errorMessage(err);
                        this.error.text = mess;
                        this.error.type = "error";
                        this.error.state = true;
                    })
                    .finally(() => (this.loading = false));
            },
            scorePassword(pass) {
                let score = 0;
                if (!pass) return score;

                // award every unique letter until 5 repetitions
                let letters = {};
                for (var i = 0; i < pass.length; i++) {
                    letters[pass[i]] = (letters[pass[i]] || 0) + 1;
                    score += 5.0 / letters[pass[i]];
                }

                // bonus points for mixing it up
                let variations = {
                    digits: /\d/.test(pass),
                    lower: /[a-z]/.test(pass),
                    upper: /[A-Z]/.test(pass),
                    nonWords: /\W/.test(pass)
                };

                let variationCount = 0;
                for (var check in variations) {
                    variationCount += variations[check] === true ? 1 : 0;
                }
                score += (variationCount - 1) * 10;

                return parseInt(score);
            },
            checkPassStrength(pass) {
                let score = this.scorePassword(pass);

                if (score > 80) {
                    this.passStrength = "strong";
                    this.passColor = "text-success";
                } else if (score > 60) {
                    this.passStrength = "good";
                    this.passColor = "text-info";
                } else if (score >= 30) {
                    this.passStrength = "weak";
                    this.passColor = "text-warning";
                } else {
                    this.passStrength = "bad";
                    this.passColor = "text-danger";
                }
            }
        }
    };
</script>
