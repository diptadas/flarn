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
                    <small>Login with:</small>
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
                    <small>Or login in with credentials</small>
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
                    <div class="text-center">
                      <button
                        type="button"
                        class="btn btn-primary mt-4"
                        @click="loginUser"
                      >
                        Login To Account
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
  name: "Recover",
  props: {
    message: {
      type: String,
      required: false
    }
  },
  data() {
    return {
      email: "",
      password: "",
      error: {
        state: false,
        text: "",
        type: false
      }
    };
  },
  created() {
    this.$store.commit("SET_GLOBAL_BUTTON", "");
  },
  methods: {
    loginUser() {
      // validate data

      const url = "auth/login";
      const data = {
        username: this.email,
        password: this.password
      };

      console.log(data);

      this.$http
        .post(url, data)
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
          console.log(err);
          const mess = err.response.data.error || "Unknown error occured";
          this.error.text = mess;
          this.error.type = "error";
          this.error.state = true;
        });
    }
  }
};
</script>
