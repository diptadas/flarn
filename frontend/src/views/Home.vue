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
            src="../assets/img/brand/logo.png"
            class="navbar-brand-img"
            alt="brand"
            style="transform: scale(5, 5);"
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
                <i :class="`fas fa-${nav.icon} text-primary`"></i>
                {{ nav.text }}
              </router-link>
            </li>
          </ul>
          <template v-if="isModerator">
            <!-- Divider -->
            <hr class="my-3" />
            <!-- Heading -->
            <h6 class="navbar-heading text-muted">Moderator</h6>
            <!-- Navigation -->
            <ul class="navbar-nav mb-md-3">
              <li class="nav-item" v-for="nav in moderatorNavs" :key="nav.id">
                <router-link class=" nav-link" :to="{ name: nav.value }">
                  <i :class="`fas fa-${nav.icon} text-primary`"></i>
                  {{ nav.text }}
                </router-link>
              </li>
            </ul>
          </template>
          <template v-if="isAdmin">
            <!-- Divider -->
            <hr class="my-3" />
            <!-- Heading -->
            <h6 class="navbar-heading text-muted">Admin</h6>
            <!-- Navigation -->
            <ul class="navbar-nav mb-md-3">
              <li class="nav-item" v-for="nav in adminNavs" :key="nav.id">
                <router-link class=" nav-link" :to="{ name: nav.value }">
                  <i :class="`fas fa-${nav.icon} text-primary`"></i>
                  {{ nav.text }}
                </router-link>
              </li>
            </ul>
          </template>
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
                      user.fullName
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
                  <i :class="`fas fa-${nav.icon}`"></i>
                  <span>{{ nav.text }}</span>
                </router-link>
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
        <div class="min-h-full">
          <router-view />
        </div>

        <footer class="footer">
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
        </footer>
      </div>
    </div>

    <Error ref="error" />
  </div>
</template>

<script>
import Error from "@/components/utils/Error.vue";
export default {
  name: "home",
  data() {
    return {
      user: {
        fullName: "Test User",
        roles: []
      },
      navs: [
        {
          id: 1,
          text: "Stories",
          value: "stories",
          icon: "tachometer-alt"
        },
        {
          id: 12,
          text: "Activities",
          value: "activities",
          icon: "snowboarding"
        },
        {
          id: 2,
          text: "Problems",
          value: "problems",
          icon: "brain"
        },
        {
          id: 3,
          text: "People",
          value: "people",
          icon: "users"
        },
        {
          id: 4,
          text: "Ranks",
          value: "ranks",
          icon: "hat-cowboy"
        },
        {
          id: 5,
          text: "My Profile",
          value: "profile",
          icon: "id-card"
        }
      ],
      moderatorNavs: [
        {
          id: 6,
          text: "Manage Problems",
          value: "manage-problems",
          icon: "atom"
        }
      ],
      adminNavs: [
        {
          id: 7,
          text: "Manage Users",
          value: "manage-users",
          icon: "users-cog"
        },
        {
          id: 8,
          text: "Manage Categories",
          value: "manage-categories",
          icon: "filter"
        }
      ],
      profileNavs: [
        {
          id: 9,
          text: "Team",
          value: "about",
          icon: "users"
        },
        {
          id: 10,
          text: "Support",
          value: "support",
          icon: "question"
        },
        {
          id: 11,
          text: "Logout",
          value: "login",
          icon: "sign-out-alt"
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
          this.user = res.data;
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
    this.authenticate();
  },
  computed: {
    activeHomePage() {
      return this.formatNavText(this.$route.name);
    },
    isModerator() {
      return this.user.roles.indexOf("ROLE_MODERATOR") !== -1;
    },
    isAdmin() {
      return this.user.roles.indexOf("ROLE_ADMIN") !== -1;
    }
  },
  components: {
    Error
  }
};
</script>
