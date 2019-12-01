<template>
  <main>
    <div class="position-relative">
      <!-- shape Hero -->
      <section class="section section-lg section-shaped pb-250">
        <div class="shape shape-style-1 shape-default">
          <span></span>
          <span></span>
          <span></span>
          <span></span>
          <span></span>
          <span></span>
          <span></span>
          <span></span>
          <span></span>
        </div>
        <div class="container py-lg-md d-flex">
          <div class="col px-0">
            <div class="row">
              <div class="col-lg-6">
                <h1 class="display-3  text-white">
                  A beautiful Design System<span>completed with examples</span>
                </h1>
                <p class="lead  text-white">
                  The design system comes with four pre-built pages to help you
                  get started faster. You can change the text and images and
                  you're good to go.
                </p>
              </div>
            </div>
          </div>
        </div>
        <!-- SVG separator -->
        <div class="separator separator-bottom separator-skew">
          <svg
            x="0"
            y="0"
            viewBox="0 0 2560 100"
            preserveAspectRatio="none"
            version="1.1"
            xmlns="http://www.w3.org/2000/svg"
          >
            <polygon
              class="fill-white"
              points="2560 0 2560 100 0 100"
            ></polygon>
          </svg>
        </div>
      </section>
      <!-- 1st Hero Variation -->
    </div>
    <section class="section section-lg pt-lg-0 section-contact-us">
      <div class="container">
        <div class="row justify-content-center mt--300">
          <div class="col-lg-8">
            <div class="card bg-gradient-secondary shadow">
              <div class="card-body p-lg-5">
                <h4 class="mb-1">Want to work with us?</h4>
                <p class="mt-0">Your project is very important to us.</p>
                <div class="form-group mt-5">
                  <div class="input-group input-group-alternative">
                    <div class="input-group-prepend">
                      <span class="input-group-text"
                        ><i class="ni ni-user-run"></i
                      ></span>
                    </div>
                    <input
                      class="form-control"
                      placeholder="Your name"
                      type="text"
                    />
                  </div>
                </div>
                <div class="form-group">
                  <div class="input-group input-group-alternative">
                    <div class="input-group-prepend">
                      <span class="input-group-text"
                        ><i class="ni ni-email-83"></i
                      ></span>
                    </div>
                    <input
                      class="form-control"
                      placeholder="Email address"
                      type="email"
                    />
                  </div>
                </div>
                <div class="form-group mb-4">
                  <textarea
                    class="form-control form-control-alternative"
                    name="name"
                    rows="4"
                    cols="80"
                    placeholder="Type a message..."
                  ></textarea>
                </div>
                <div>
                  <button
                    type="button"
                    class="btn btn-default btn-round btn-block btn-lg"
                  >
                    Send Message
                  </button>
                </div>
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
  name: "Support"
};
</script>
