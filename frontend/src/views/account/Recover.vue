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
                    <small
                    >Change your password here</small
                    >
                  </div>

                  <div
                          class="alert alert-dismissible fade show"
                          role="alert"
                          v-if="error.state"
                          :class="
                      error.type === 'error' ? 'alert-danger' : 'alert-default'
                    "
                  >
                    <span class="alert-inner--icon"
                    ><i class="ni ni-like-2"></i
                    ></span>
                    <span class="alert-inner--text">{{ error.text }}</span>
                    <button
                            type="button"
                            class="close"
                            data-dismiss="alert"
                            aria-label="Close"
                            @click="error.state = false"
                    >
                      <span aria-hidden="true">&times;</span>
                    </button>
                  </div>

                  <form>
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
                                  v-model="code"
                          />
                        </div>
                      </div>

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
                                  v-model="password"
                          />
                        </div>
                      </div>
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
                                  v-model="cpassword"
                          />
                        </div>
                      </div>
                    <div class="text-center">
                      <button
                              type="button"
                              class="btn btn-primary mt-4"
                              @click="submit"
                      >
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
        code: "",
        email: "",
        password: "",
        cpassword: "",
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

        if(!this.email) return;

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
                  this.error.text = err.response.data.message || "Unknown error occurred";
                  this.error.type = "error";
                  this.error.state = true;
                });
      },
      submit() {
        // validate data

        const url = 'auth/updatePassword';
        const data = {
          username: this.email,
          newPassword: this.password,
          confirmationCode: this.code
        };
        this.$http
                .post(url, data)
                .then(res => {
                  this.$router.push({
                    name: "login",
                    params: {message: "Password recovery successful."}
                  });

                })
                .catch(err => {
                  console.log(err);
                  this.error.text = err.response.data.message || "Unknown error occurred";
                  this.error.type = "error";
                  this.error.state = true;
                });
      }
    },
    created() {
      this.email = this.$store.state.username
    }
  };
</script>
