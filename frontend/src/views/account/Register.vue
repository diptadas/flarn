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
                      <div class="input-group input-group-alternative mb-3">
                        <div class="input-group-prepend">
                          <span class="input-group-text"
                            ><i class="ni ni-hat-3"></i
                          ></span>
                        </div>
                        <input
                          class="form-control"
                          placeholder="Name"
                          type="text"
                          v-model="name"
                        />
                      </div>
                    </div>
                    <div class="form-group">
                      <div class="input-group input-group-alternative mb-3">
                        <div class="input-group-prepend">
                          <span class="input-group-text"
                            ><i class="ni ni-email-83"></i
                          ></span>
                        </div>
                        <input
                          class="form-control"
                          placeholder="Email"
                          type="email"
                          v-model="email"
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
                    <div class="text-muted font-italic">
                      <small
                        >password strength:
                        <span class="text-success font-weight-700">{{
                          passStrength
                        }}</span></small
                      >
                    </div>
                    <div class="row my-4">
                      <div class="col-12">
                        <div
                          class="custom-control custom-control-alternative custom-checkbox"
                        >
                          <input
                            class="custom-control-input"
                            id="customCheckRegister"
                            type="checkbox"
                            v-model="terms"
                          />
                          <label
                            class="custom-control-label"
                            for="customCheckRegister"
                            ><span
                              >I agree with the
                              <a href="#">Privacy Policy</a></span
                            ></label
                          >
                        </div>
                      </div>
                    </div>
                    <div class="text-center">
                      <button
                        type="button"
                        class="btn btn-primary mt-4"
                        @click="registerUser"
                      >
                        Create account
                      </button>
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
  name: "Register",
  data() {
    return {
      name: "",
      email: "",
      password: "",
      cpassword: "",
      terms: false,
      error: {
        state: false,
        text: "",
        type: false
      },
      passStrength: ""
    };
  },
  watch: {
    password(old, value) {
      this.checkPassStrength(value);
    }
  },
  created() {
    this.$store.commit("SET_GLOBAL_BUTTON", "register");
  },
  methods: {
    registerUser() {
      // validate data

      const url = "auth/register";
      const data = {
        username: this.email,
        fullName: this.name,
        password: this.password
      };

      this.$http
        .post(url, data)
        .then(res => {
          this.$router.push({
            name: "login",
            params: { message: "Account registration success" }
          });
        })
        .catch(err => {
          console.log(err);
          const mess = err.response.data.message || "Unknown error occured";
          this.error.text = mess;
          this.error.type = "error";
          this.error.state = true;
        });
    },
    scorePassword(pass) {
      let score = 0;
      if (!pass) return score;

      // award every unique letter until 5 repetitions
      let letters = new Object();
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
        variationCount += variations[check] == true ? 1 : 0;
      }
      score += (variationCount - 1) * 10;

      return parseInt(score);
    },
    checkPassStrength(pass) {
      let score = this.scorePassword(pass);
      if (score > 80) this.passStrength = "strong";
      else if (score > 60) this.passStrength = "good";
      else if (score >= 30) this.passStrength = "weak";
      else this.passStrength = "bad";
    }
  }
};
</script>
