<template>
  <div id="account">
    <header class="header-global">
      <nav
        id="navbar-main"
        class="navbar navbar-main navbar-expand-lg navbar-transparent navbar-light headroom"
      >
        <div class="container">
          <router-link class="navbar-brand mr-lg-5" :to="{ name: 'home' }">
            <img
              src="../assets/img/brand/logo-white.png"
              alt="brand"
              style="transform: scale(5, 5);"
            />
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
                <router-link
                  class="nav-link"
                  role="button"
                  :to="{ name: 'acc-about' }"
                >
                  <i class="ni ni-ui-04 d-lg-none"></i>
                  <span class="nav-link-inner--text">About Us</span>
                </router-link>
              </li>
              <li class="nav-item dropdown">
                <router-link
                  :to="{ name: 'acc-support' }"
                  class="nav-link"
                  data-toggle="dropdown"
                  role="button"
                >
                  <i class="ni ni-collection d-lg-none"></i>
                  <span class="nav-link-inner--text">Support</span>
                </router-link>
              </li>
            </ul>
            <ul class="navbar-nav align-items-lg-center ml-lg-auto">
              <li class="nav-item">
                <a
                  class="nav-link nav-link-icon"
                  href="https://github.com/diptadas/flarn"
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
        <div class="row align-items-center justify-content-xl-between">
          <div class="col-xl-6">
            <div class="copyright text-center text-xl-left text-muted">
              &copy; 2019
              <a
                href="https://github.com/diptadas/flarn"
                class="font-weight-bold ml-1"
                target="_blank"
                >Flarn Project</a
              >
            </div>
          </div>
          <div class="col-xl-6">
            <ul
              class="nav nav-footer justify-content-center justify-content-xl-end"
            >
              <li class="nav-item">
                <a
                  href="https://github.com/diptadas/flarn"
                  class="nav-link"
                  target="_blank"
                  >Flarn Project</a
                >
              </li>
              <li class="nav-item">
                <router-link :to="{ name: 'about' }" class="nav-link"
                  >About Us</router-link
                >
              </li>
              <li class="nav-item">
                <a
                  href="https://www.baylor.edu"
                  class="nav-link"
                  target="_blank"
                  >Baylor</a
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
