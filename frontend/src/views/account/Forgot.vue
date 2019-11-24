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
                      >Enter your email address here to recover your
                      password</small
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
                      <small
                        ><router-link :to="{ name: 'login' }"
                          >or Login instead</router-link
                        ></small
                      >
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
      email: "",
      error: {
        state: false,
        text: "",
        type: false
      }
    };
  },
  methods: {
    submit() {
      // validate data

      const url = `users/sendConfirmationCode/?username=${this.email}`;

      this.$http
        .get(url)
        .then(res => {
          const data = res.data;

          this.error.text = "Recover code sent to your account";
          this.error.type = "info";
          this.error.state = false;
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
