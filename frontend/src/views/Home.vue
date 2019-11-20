<template>
  <div class="home">
    <nav
      class="navbar navbar-vertical fixed-left navbar-expand-md navbar-light bg-white"
      id="sidenav-main"
    >
      <div class="container-fluid">
        <!-- Toggler -->
        <button
          class="navbar-toggler"
          type="button"
          data-toggle="collapse"
          data-target="#sidenav-collapse-main"
          aria-controls="sidenav-main"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <!-- Brand -->
        <router-link class="navbar-brand pt-0" :to="{ name: 'home' }">
          <img
            src="../assets/img/brand/blue.png"
            class="navbar-brand-img"
            alt="..."
          />
        </router-link>
        <!-- User -->
        <ul class="nav align-items-center d-md-none">
          <li class="nav-item dropdown">
            <a
              class="nav-link nav-link-icon"
              href="#"
              role="button"
              data-toggle="dropdown"
              aria-haspopup="true"
              aria-expanded="false"
            >
              <i class="ni ni-bell-55"></i>
            </a>
            <div
              class="dropdown-menu dropdown-menu-arrow dropdown-menu-right"
              aria-labelledby="navbar-default_dropdown_1"
            >
              <a class="dropdown-item" href="#">Action</a>
              <a class="dropdown-item" href="#">Another action</a>
              <div class="dropdown-divider"></div>
              <a class="dropdown-item" href="#">Something else here</a>
            </div>
          </li>
          <li class="nav-item dropdown">
            <a
              class="nav-link"
              href="#"
              role="button"
              data-toggle="dropdown"
              aria-haspopup="true"
              aria-expanded="false"
            >
              <div class="media align-items-center">
                <span class="avatar avatar-sm rounded-circle">
                  <img
                    alt="Image placeholder"
                    src="../assets/img/theme/team-1-800x800.jpg"
                  />
                </span>
              </div>
            </a>
            <div class="dropdown-menu dropdown-menu-arrow dropdown-menu-right">
              <div class=" dropdown-header noti-title">
                <h6 class="text-overflow m-0">Welcome!</h6>
              </div>
              <a href="./examples/profile.html" class="dropdown-item">
                <i class="ni ni-single-02"></i>
                <span>My profile</span>
              </a>
              <a href="./examples/profile.html" class="dropdown-item">
                <i class="ni ni-settings-gear-65"></i>
                <span>Settings</span>
              </a>
              <a href="./examples/profile.html" class="dropdown-item">
                <i class="ni ni-calendar-grid-58"></i>
                <span>Activity</span>
              </a>
              <a href="./examples/profile.html" class="dropdown-item">
                <i class="ni ni-support-16"></i>
                <span>Support</span>
              </a>
              <div class="dropdown-divider"></div>
              <a href="#!" class="dropdown-item">
                <i class="ni ni-user-run"></i>
                <span>Logout</span>
              </a>
            </div>
          </li>
        </ul>
        <!-- Collapse -->
        <div class="collapse navbar-collapse" id="sidenav-collapse-main">
          <!-- Collapse header -->
          <div class="navbar-collapse-header d-md-none">
            <div class="row">
              <div class="col-6 collapse-brand">
                <a href="./index.html">
                  <img src="../assets/img/brand/blue.png" />
                </a>
              </div>
              <div class="col-6 collapse-close">
                <button
                  type="button"
                  class="navbar-toggler"
                  data-toggle="collapse"
                  data-target="#sidenav-collapse-main"
                  aria-controls="sidenav-main"
                  aria-expanded="false"
                  aria-label="Toggle sidenav"
                >
                  <span></span>
                  <span></span>
                </button>
              </div>
            </div>
          </div>
          <!-- Form -->
          <!-- <form class="mt-4 mb-3 d-md-none">
            <div class="input-group input-group-rounded input-group-merge">
              <input
                type="search"
                class="form-control form-control-rounded form-control-prepended"
                placeholder="Search"
                aria-label="Search"
              />
              <div class="input-group-prepend">
                <div class="input-group-text">
                  <span class="fa fa-search"></span>
                </div>
              </div>
            </div>
          </form> -->
          <!-- Navigation -->
          <ul class="navbar-nav">
            <li class="nav-item" v-for="nav in navs" :key="nav.id">
              <router-link class=" nav-link" :to="{ name: nav.value }">
                <i class="ni ni-tv-2 text-primary"></i> {{ nav.text }}
              </router-link>
            </li>

            <!-- <li class="nav-item">
              <a class="nav-link " href="./examples/icons.html">
                <i class="ni ni-planet text-blue"></i> Icons
              </a>
            </li>
            <li class="nav-item">
              <a class="nav-link " href="./examples/maps.html">
                <i class="ni ni-pin-3 text-orange"></i> Maps
              </a>
            </li>
            <li class="nav-item">
              <a class="nav-link " href="./examples/profile.html">
                <i class="ni ni-single-02 text-yellow"></i> User profile
              </a>
            </li>
            <li class="nav-item">
              <a class="nav-link " href="./examples/tables.html">
                <i class="ni ni-bullet-list-67 text-red"></i> Tables
              </a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="./examples/login.html">
                <i class="ni ni-key-25 text-info"></i> Login
              </a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="./examples/register.html">
                <i class="ni ni-circle-08 text-pink"></i> Register
              </a>
            </li> -->
          </ul>
          <!-- Divider -->
          <hr class="my-3" />
          <!-- Heading -->
          <h6 class="navbar-heading text-muted">Personal</h6>
          <!-- Navigation -->
          <ul class="navbar-nav mb-md-3">
            <li class="nav-item" v-for="nav in subNavs" :key="nav.id">
              <router-link class=" nav-link" :to="{ name: nav.value }">
                <i class="ni ni-tv-2 text-primary"></i> {{ nav.text }}
              </router-link>
            </li>
            <!-- <li class="nav-item">
              <a
                class="nav-link"
                href="https://demos.creative-tim.com/argon-dashboard/docs/foundation/colors.html"
              >
                <i class="ni ni-palette"></i> Foundation
              </a>
            </li>
            <li class="nav-item">
              <a
                class="nav-link"
                href="https://demos.creative-tim.com/argon-dashboard/docs/components/alerts.html"
              >
                <i class="ni ni-ui-04"></i> Components
              </a>
            </li> -->
          </ul>
        </div>
      </div>
    </nav>
    <div class="main-content">
      <!-- Navbar -->
      <nav
        class="navbar navbar-top navbar-expand-md navbar-dark pt-0 pb-0"
        id="navbar-main"
      >
        <div class="container-fluid">
          <!-- Brand -->
          <a
            class="h4 mb-0 text-white text-uppercase d-none d-lg-inline-block"
            href="./index.html"
            >{{ activeHomePage }}</a
          >
          <!-- Form -->
          <!-- <form
            class="navbar-search navbar-search-dark form-inline mr-3 d-none d-md-flex ml-lg-auto"
          >
            <div class="form-group mb-0">
              <div class="input-group input-group-alternative">
                <div class="input-group-prepend">
                  <span class="input-group-text"
                    ><i class="fas fa-search"></i
                  ></span>
                </div>
                <input class="form-control" placeholder="Search" type="text" />
              </div>
            </div>
          </form> -->
          <!-- User -->
          <ul class="navbar-nav align-items-center d-none d-md-flex">
            <li class="nav-item dropdown">
              <a
                class="nav-link pr-0"
                href="#"
                role="button"
                data-toggle="dropdown"
                aria-haspopup="true"
                aria-expanded="false"
              >
                <div class="media align-items-center">
                  <span class="avatar avatar-sm rounded-circle">
                    <img
                      alt="Image placeholder"
                      src="../assets/img/theme/team-4-800x800.jpg"
                    />
                  </span>
                  <div class="media-body ml-2 d-none d-lg-block">
                    <span class="mb-0 text-sm  font-weight-bold">{{
                      fullName
                    }}</span>
                  </div>
                </div>
              </a>
              <div
                class="dropdown-menu dropdown-menu-arrow dropdown-menu-right"
              >
                <div class=" dropdown-header noti-title">
                  <h6 class="text-overflow m-0">Welcome!</h6>
                </div>
                <router-link
                  :to="{ name: nav.value }"
                  v-for="nav in profileNavs"
                  :key="nav.id"
                  class="dropdown-item"
                >
                  <i class="ni ni-single-02"></i>
                  <span>{{ nav.text }}</span>
                </router-link>
                <!-- <a href="./examples/profile.html" class="dropdown-item">
                  <i class="ni ni-settings-gear-65"></i>
                  <span></span>
                </a>
                <a href="./examples/profile.html" class="dropdown-item">
                  <i class="ni ni-calendar-grid-58"></i>
                  <span></span>
                </a>
                <a href="./examples/profile.html" class="dropdown-item">
                  <i class="ni ni-support-16"></i>
                  <span></span>
                </a>
                <div class="dropdown-divider"></div>
                <a href="#!" class="dropdown-item">
                  <i class="ni ni-user-run"></i>
                  <span>Logout</span>
                </a> -->
              </div>
            </li>
          </ul>
        </div>
      </nav>
      <!-- End Navbar -->
      <!-- Header -->
      <div
        class="header bg-gradient-primary d-block"
        style="height: 4.5rem;"
      ></div>

      <div class="container-fluid mt-2">
        <router-view />

        <footer class="footer">
          <div class="row align-items-center justify-content-xl-between">
            <div class="col-xl-6">
              <div class="copyright text-center text-xl-left text-muted">
                &copy; 2019
                <a
                  href="https://www.creative-tim.com"
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
                    href="https://www.creative-tim.com"
                    class="nav-link"
                    target="_blank"
                    >Flarn Project</a
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
                    >Baylor</a
                  >
                </li>
                <li class="nav-item">
                  <a
                    href="https://github.com/creativetimofficial/argon-dashboard/blob/master/LICENSE.md"
                    class="nav-link"
                    target="_blank"
                    >MIT License</a
                  >
                </li>
              </ul>
            </div>
          </div>
        </footer>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "home",
  data() {
    return {
      fullName: "Test User",
      navs: [
        {
          id: 1,
          text: "Timeline",
          value: "timeline"
        },
        {
          id: 2,
          text: "Problems",
          value: "problems"
        },
        {
          id: 3,
          text: "People",
          value: "people"
        },
        {
          id: 4,
          text: "Ranks",
          value: "ranks"
        }
      ],
      subNavs: [
        {
          id: 5,
          text: "My Profile",
          value: "profile"
        }
      ],
      profileNavs: [
        // {
        //   id: 1,
        //   text: "My Profile",
        //   value: "profile"
        // },
        // {
        //   id: 2,
        //   text: "Settings",
        //   value: "settings"
        // },
        // {
        //   id: 3,
        //   text: "Activity",
        //   value: "activity"
        // },
        // {
        //   id: 4,
        //   text: "Support",
        //   value: "support"
        // },
        {
          id: 5,
          text: "Logout",
          value: "login"
        }
      ]
    };
  },
  methods: {
    authenticate() {
      const url = "users/current";

      this.$http
        .get(url)
        .then(res => {
          const data = res.data;
          this.fullName = data.fullName;
        })
        .catch(err => {
          this.$router.replace({ name: "login" });
        });
    },
    formatNavText(string) {
      return string.split("-").join(" ");
    }
  },
  created() {
    this.$store.commit("SET_GLOBAL_BUTTON", "home");
    this.authenticate();
  },
  computed: {
    activeHomePage() {
      return this.formatNavText(this.$route.name);
    }
  }
};
</script>
