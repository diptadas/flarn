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
                <div class="card-header bg-white pb-5">
                  <div class="text-muted text-center mb-3">
                    <small>Sign up with</small>
                  </div>
                  <div class="text-center">
                    <a href="#" class="btn btn-neutral btn-icon mr-4">
                      <span class="btn-inner--icon">
                        <img
                          src="../../assets/img/icons/common/github.svg"
                          alt="image"
                        />
                      </span>
                      <span class="btn-inner--text">Github</span>
                    </a>
                    <a href="#" class="btn btn-neutral btn-icon">
                      <span class="btn-inner--icon">
                        <img
                          src="../../assets/img/icons/common/google.svg"
                          alt="image"
                        />
                      </span>
                      <span class="btn-inner--text">Google</span>
                    </a>
                  </div>
                </div>
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
                        <span class="text-success font-weight-700"
                          >strong</span
                        ></small
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
    <footer class="footer">
      <div class="container">
        <div class="row row-grid align-items-center mb-5">
          <div class="col-lg-6">
            <h3 class="text-primary font-weight-light mb-2">
              Thank you for supporting us!
            </h3>
            <h4 class="mb-0 font-weight-light">
              Let's get in touch on any of these platforms.
            </h4>
          </div>
          <div class="col-lg-6 text-lg-center btn-wrapper">
            <a
              target="_blank"
              href="https://twitter.com/creativetim"
              class="btn btn-neutral btn-icon-only btn-twitter btn-round btn-lg"
              data-toggle="tooltip"
              data-original-title="Follow us"
            >
              <i class="fa fa-twitter"></i>
            </a>
            <a
              target="_blank"
              href="https://www.facebook.com/creativetim"
              class="btn btn-neutral btn-icon-only btn-facebook btn-round btn-lg"
              data-toggle="tooltip"
              data-original-title="Like us"
            >
              <i class="fa fa-facebook-square"></i>
            </a>
            <a
              target="_blank"
              href="https://dribbble.com/creativetim"
              class="btn btn-neutral btn-icon-only btn-dribbble btn-lg btn-round"
              data-toggle="tooltip"
              data-original-title="Follow us"
            >
              <i class="fa fa-dribbble"></i>
            </a>
            <a
              target="_blank"
              href="https://github.com/creativetimofficial"
              class="btn btn-neutral btn-icon-only btn-github btn-round btn-lg"
              data-toggle="tooltip"
              data-original-title="Star on Github"
            >
              <i class="fa fa-github"></i>
            </a>
          </div>
        </div>
        <hr />
        <div class="row align-items-center justify-content-md-between">
          <div class="col-md-6">
            <div class="copyright">
              &copy; 2019
              <a href="https://www.creative-tim.com" target="_blank"
                >Creative Tim</a
              >.
            </div>
          </div>
          <div class="col-md-6">
            <ul class="nav nav-footer justify-content-end">
              <li class="nav-item">
                <a
                  href="https://www.creative-tim.com"
                  class="nav-link"
                  target="_blank"
                  >Creative Tim</a
                >
              </li>
              <li class="nav-item">
                <a
                  href="https://www.creative-tim.com/presentation"
                  class="nav-link"
                  target="_blank"
                  >About Us</a
                >
              </li>
              <li class="nav-item">
                <a
                  href="http://blog.creative-tim.com"
                  class="nav-link"
                  target="_blank"
                  >Blog</a
                >
              </li>
              <li class="nav-item">
                <a
                  href="https://github.com/creativetimofficial/argon-design-system/blob/master/LICENSE.md"
                  class="nav-link"
                  target="_blank"
                  >MIT License</a
                >
              </li>
            </ul>
          </div>
        </div>
      </div>
    </footer>
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
      }
    };
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
    }
  }
};
</script>

<style lang="scss" scoped></style>
