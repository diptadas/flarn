<template>
  <div id="app">
    <header class="header-global">
      <nav
        id="navbar-main"
        class="navbar navbar-main navbar-expand-lg navbar-transparent navbar-light headroom"
      >
        <div class="container">
          <router-link class="navbar-brand mr-lg-5" :to="{ name: 'home' }">
            <img src="../assets/img/brand/white.png" alt="brand" />
          </router-link>
          <button
            class="navbar-toggler"
            type="button"
            data-toggle="collapse"
            data-target="#navbar_global"
            aria-controls="navbar_global"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="navbar-collapse collapse" id="navbar_global">
            <div class="navbar-collapse-header">
              <div class="row">
                <div class="col-6 collapse-brand">
                  <a href="../index.html">
                    <img src="../assets/img/brand/blue.png" alt="brand" />
                  </a>
                </div>
                <div class="col-6 collapse-close">
                  <button
                    type="button"
                    class="navbar-toggler"
                    data-toggle="collapse"
                    data-target="#navbar_global"
                    aria-controls="navbar_global"
                    aria-expanded="false"
                    aria-label="Toggle navigation"
                  >
                    <span></span>
                    <span></span>
                  </button>
                </div>
              </div>
            </div>
            <ul class="navbar-nav navbar-nav-hover align-items-lg-center">
              <li class="nav-item dropdown">
                <a
                  href="#"
                  class="nav-link"
                  data-toggle="dropdown"
                  role="button"
                >
                  <i class="ni ni-ui-04 d-lg-none"></i>
                  <span class="nav-link-inner--text">Components</span>
                </a>
                <div class="dropdown-menu dropdown-menu-xl">
                  <div class="dropdown-menu-inner">
                    <a
                      href="https://demos.creative-tim.com/argon-design-system/docs/getting-started/overview.html"
                      class="media d-flex align-items-center"
                    >
                      <div
                        class="icon icon-shape bg-gradient-primary rounded-circle text-white"
                      >
                        <i class="ni ni-spaceship"></i>
                      </div>
                      <div class="media-body ml-3">
                        <h6 class="heading text-primary mb-md-1">
                          Getting started
                        </h6>
                        <p class="description d-none d-md-inline-block mb-0">
                          Learn how to use Argon compiling Scss, change brand
                          colors and more.
                        </p>
                      </div>
                    </a>
                    <a
                      href="https://demos.creative-tim.com/argon-design-system/docs/foundation/colors.html"
                      class="media d-flex align-items-center"
                    >
                      <div
                        class="icon icon-shape bg-gradient-success rounded-circle text-white"
                      >
                        <i class="ni ni-palette"></i>
                      </div>
                      <div class="media-body ml-3">
                        <h6 class="heading text-primary mb-md-1">Foundation</h6>
                        <p class="description d-none d-md-inline-block mb-0">
                          Learn more about colors, typography, icons and the
                          grid system we used for Argon.
                        </p>
                      </div>
                    </a>
                    <a
                      href="https://demos.creative-tim.com/argon-design-system/docs/components/alerts.html"
                      class="media d-flex align-items-center"
                    >
                      <div
                        class="icon icon-shape bg-gradient-warning rounded-circle text-white"
                      >
                        <i class="ni ni-ui-04"></i>
                      </div>
                      <div class="media-body ml-3">
                        <h5 class="heading text-warning mb-md-1">Components</h5>
                        <p class="description d-none d-md-inline-block mb-0">
                          Browse our 50 beautiful handcrafted components offered
                          in the Free version.
                        </p>
                      </div>
                    </a>
                  </div>
                </div>
              </li>
              <li class="nav-item dropdown">
                <a
                  href="#"
                  class="nav-link"
                  data-toggle="dropdown"
                  role="button"
                >
                  <i class="ni ni-collection d-lg-none"></i>
                  <span class="nav-link-inner--text">Examples</span>
                </a>
                <div class="dropdown-menu">
                  <a href="../examples/landing.html" class="dropdown-item"
                    >Landing</a
                  >
                  <a href="../examples/profile.html" class="dropdown-item"
                    >Profile</a
                  >
                  <a href="../examples/login.html" class="dropdown-item"
                    >Login</a
                  >
                  <a href="../examples/register.html" class="dropdown-item"
                    >Register</a
                  >
                </div>
              </li>
            </ul>
            <ul class="navbar-nav align-items-lg-center ml-lg-auto">
              <li class="nav-item">
                <a
                  class="nav-link nav-link-icon"
                  href="https://www.facebook.com/creativetim"
                  target="_blank"
                  data-toggle="tooltip"
                  title="Like us on Facebook"
                >
                  <i class="fab fa-facebook-square"></i>
                  <span class="nav-link-inner--text d-lg-none">Facebook</span>
                </a>
              </li>
              <li class="nav-item">
                <a
                  class="nav-link nav-link-icon"
                  href="https://www.instagram.com/creativetimofficial"
                  target="_blank"
                  data-toggle="tooltip"
                  title="Follow us on Instagram"
                >
                  <i class="fab fa-instagram"></i>
                  <span class="nav-link-inner--text d-lg-none">Instagram</span>
                </a>
              </li>
              <li class="nav-item">
                <a
                  class="nav-link nav-link-icon"
                  href="https://twitter.com/creativetim"
                  target="_blank"
                  data-toggle="tooltip"
                  title="Follow us on Twitter"
                >
                  <i class="fab fa-twitter-square"></i>
                  <span class="nav-link-inner--text d-lg-none">Twitter</span>
                </a>
              </li>
              <li class="nav-item">
                <a
                  class="nav-link nav-link-icon"
                  href="https://github.com/creativetimofficial/argon-design-system"
                  target="_blank"
                  data-toggle="tooltip"
                  title="Star us on Github"
                >
                  <i class="fab fa-github"></i>
                  <span class="nav-link-inner--text d-lg-none">Github</span>
                </a>
              </li>
              <li class="nav-item d-none d-lg-block ml-lg-4">
                <router-link
                  :to="{ name: button.value }"
                  class="nav-link nav-link-icon"
                >
                  <i class="fab fa-cloud-download mr-2"></i>
                  <span class="nav-link-inner--text">{{ button.text }}</span>
                </router-link>
              </li>
            </ul>
          </div>
        </div>
      </nav>
    </header>
    <router-view />

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
              <i class="fab fa-twitter"></i>
            </a>
            <a
              target="_blank"
              href="https://www.facebook.com/creativetim"
              class="btn btn-neutral btn-icon-only btn-facebook btn-round btn-lg"
              data-toggle="tooltip"
              data-original-title="Like us"
            >
              <i class="fab fa-facebook-square"></i>
            </a>
            <a
              target="_blank"
              href="https://dribbble.com/creativetim"
              class="btn btn-neutral btn-icon-only btn-dribbble btn-lg btn-round"
              data-toggle="tooltip"
              data-original-title="Follow us"
            >
              <i class="fab fa-dribbble"></i>
            </a>
            <a
              target="_blank"
              href="https://github.com/creativetimofficial"
              class="btn btn-neutral btn-icon-only btn-github btn-round btn-lg"
              data-toggle="tooltip"
              data-original-title="Star on Github"
            >
              <i class="fab fa-github"></i>
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
  name: "Account",
  data() {
    return {
      button: {
        text: "Log In",
        value: "login"
      }
    };
  },
  created() {
    this.$store.commit("LOGOUT");
    localStorage.removeItem("auth_token");
    this.setButton(this.currentPage);
  },
  computed: {
    currentPage() {
      return this.$router.currentRoute.name;
    }
  },
  methods: {
    setButton(page) {
      switch (page) {
        case "login":
          this.button.text = "Sign Up";
          this.button.value = "register";
          break;
        default:
          this.button.text = "Log In";
          this.button.value = "login";
          break;
      }
    }
  },
  watch: {
    $route(to, from) {
      this.setButton(to.name);
    }
  }
};
</script>
