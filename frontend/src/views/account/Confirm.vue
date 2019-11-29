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
                        class="alert alert-dismissible fade show"
                        role="alert"
                        v-if="error.state"
                        :class="
                    error.type === 'error' ? 'alert-danger' : 'alert-default'
                  "
                >
                  <span class="alert-inner--icon"
                  ><i class="ni ni-bell-55"></i
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
                          ><i class="ni ni-lock-circle-open"></i
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
                  <div class="text-center">
                    <button
                            type="button"
                            class="btn btn-primary mt-4"
                            @click="confirm"
                            :disabled="loading"
                    >
                      <span
                              class="spinner-grow spinner-grow-sm"
                              role="status"
                              aria-hidden="true"
                              v-if="loading"
                      ></span>
                      Confirm Account
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
      loading: false,
      email: "",
      code: "",
      error: {
        state: false,
        text: "",
        type: false
      }
    };
  },
  methods: {
    confirm() {
      // validate data

      const url = "auth/confirm";
      const data = {
        username: this.email,
        confirmationCode: this.code
      };

      this.$http
        .post(url, data)
        .then(res => {
          const data = res.data;

          this.$router.push({
            name: "login",
            params: {message: "Account registration success"}
          });
        })
        .catch(err => {
          console.log(err);
          const mess = err.response.data.error || "Unknown error occurred";
          this.error.text = mess;
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
