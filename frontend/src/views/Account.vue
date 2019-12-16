<template>
  <div id="account">
    <header class="header-global">
      <nav
        class="navbar navbar-main navbar-expand-lg navbar-transparent navbar-light headroom"
        id="navbar-main"
      >
        <div class="container">
          <router-link
            :to="{ name: 'home' }"
            class="navbar-brand mr-lg-8 d-none d-lg-block"
          >
            <img
              alt="brand"
              src="../assets/img/brand/whiteFlarn.svg"
              style="transform: scale(4, 4);"
            />
          </router-link>
          <button
            aria-controls="navbar_global"
            aria-expanded="false"
            aria-label="Toggle navigation"
            class="navbar-toggler"
            data-target="#navbar_global"
            data-toggle="collapse"
            type="button"
          >
            <span class="navbar-toggler-icon"></span>
          </button>

          <ul class="navbar-nav align-items-lg-center ml-lg-auto d-lg-none">
            <li class="nav-item ml-lg-4">
              <button
                class="btn btn-secondary"
                type="button"
                @click="$router.push({ name: button.value })"
              >
                {{ button.text }}
              </button>
            </li>
          </ul>
          <div class="navbar-collapse collapse" id="navbar_global">
            <div class="navbar-collapse-header">
              <div class="row">
                <div class="col-6 collapse-brand">
                  <router-link :to="{ name: 'home' }">
                    <img alt="brand" src="../assets/img/brand/Flarn.svg" />
                  </router-link>
                </div>
                <div class="col-6 collapse-close">
                  <button
                    aria-controls="navbar_global"
                    aria-expanded="false"
                    aria-label="Toggle navigation"
                    class="navbar-toggler"
                    data-target="#navbar_global"
                    data-toggle="collapse"
                    type="button"
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
                  :to="{ name: 'about' }"
                  class="nav-link"
                  role="button"
                >
                  <i class="ni ni-ui-04 d-lg-none"></i>
                  <span class="nav-link-inner--text">About Us</span>
                </router-link>
              </li>
              <li class="nav-item dropdown">
                <router-link
                  :to="{ name: 'contact' }"
                  class="nav-link"
                  data-toggle="dropdown"
                  role="button"
                >
                  <i class="ni ni-collection d-lg-none"></i>
                  <span class="nav-link-inner--text">Contact</span>
                </router-link>
              </li>
            </ul>
            <ul class="navbar-nav align-items-lg-center ml-lg-auto">
              <li class="nav-item">
                <a
                  class="nav-link nav-link-icon"
                  data-toggle="tooltip"
                  href="https://github.com/diptadas/flarn"
                  target="_blank"
                  title="Star us on Github"
                >
                  <i class="fab fa-github"></i>
                  <span class="nav-link-inner--text d-lg-none">Github</span>
                </a>
              </li>
              <li class="nav-item d-none d-lg-block ml-lg-4">
                <button
                  class="btn btn-secondary"
                  type="button"
                  @click="$router.push({ name: button.value })"
                >
                  {{ button.text }}
                </button>
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
                class="font-weight-bold ml-1"
                href="https://github.com/diptadas/flarn"
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
                  class="nav-link"
                  href="https://github.com/diptadas/flarn"
                  target="_blank"
                  >Flarn Project</a
                >
              </li>
              <li class="nav-item">
                <router-link :to="{ name: 'about' }" class="nav-link"
                  >About Us
                </router-link>
              </li>
              <li class="nav-item">
                <a
                  class="nav-link"
                  href="https://www.baylor.edu"
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
        text: "Sign In",
        value: "login"
      }
    };
  },
  created() {
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
          this.button.text = "Sign In";
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
