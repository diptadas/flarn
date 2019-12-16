<template>
  <div class="home">
    <nav
      class="navbar navbar-vertical fixed-left navbar-expand-md navbar-light bg-white"
      id="sidenav-main"
    >
      <div class="container-fluid">
        <!-- Toggler -->
        <button
          aria-controls="sidenav-main"
          aria-expanded="false"
          aria-label="Toggle navigation"
          class="navbar-toggler"
          data-target="#sidenav-collapse-main"
          data-toggle="collapse"
          type="button"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <!-- Brand -->
        <router-link
          :to="{ name: 'home' }"
          class="navbar-brand d-none d-md-block"
        >
          <img
            alt="brand"
            class="navbar-brand-img"
            src="../assets/img/brand/Flarn.svg"
            style="transform: scale(4, 4);"
          />
        </router-link>
        <router-link
          class="h4 mb-0 text-uppercase d-md-none"
          :to="{ name: 'home' }"
          >{{ activeHomePage }}</router-link
        >
        <!-- User -->
        <ul class="nav align-items-center d-md-none">
          <li class="nav-item dropdown">
            <a
              aria-expanded="false"
              aria-haspopup="true"
              class="nav-link"
              data-toggle="dropdown"
              href="#"
              role="button"
            >
              <div class="media align-items-center">
                <span class="avatar avatar-sm rounded-circle">
                  <img :src="user.avatarLink" alt="User Avatar" />
                </span>
              </div>
            </a>
            <div class="dropdown-menu dropdown-menu-arrow dropdown-menu-right">
              <div class=" dropdown-header noti-title">
                <h6 class="text-overflow m-0">Welcome!</h6>
              </div>
              <router-link
                :key="nav.id"
                :to="{ name: nav.value }"
                class="dropdown-item"
                v-for="nav in profileNavs"
              >
                <i :class="`fas fa-${nav.icon}`"></i>
                <span>{{ nav.text }}</span>
              </router-link>
            </div>
          </li>
        </ul>
        <!-- Collapse -->
        <div
          class="collapse navbar-collapse"
          id="sidenav-collapse-main"
          data-toggle="collapse"
          data-target="#sidenav-collapse-main"
        >
          <!-- Collapse header -->
          <div class="navbar-collapse-header d-md-none">
            <div class="row">
              <div class="col-6 collapse-brand">
                <router-link :to="{ name: 'home' }">
                  <img src="../assets/img/brand/Flarn.svg" />
                </router-link>
              </div>
              <div class="col-6 collapse-close">
                <button
                  aria-controls="sidenav-main"
                  aria-expanded="false"
                  aria-label="Toggle sidenav"
                  class="navbar-toggler"
                  data-target="#sidenav-collapse-main"
                  data-toggle="collapse"
                  type="button"
                  ref="navToggle"
                >
                  <span></span>
                  <span></span>
                </button>
              </div>
            </div>
          </div>
          <!-- Navigation -->
          <ul class="navbar-nav mt--4">
            <li :key="nav.id" class="nav-item" v-for="nav in navs">
              <router-link :to="{ name: nav.value }" class="nav-link">
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
              <li :key="nav.id" class="nav-item" v-for="nav in moderatorNavs">
                <router-link :to="{ name: nav.value }" class=" nav-link">
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
              <li :key="nav.id" class="nav-item" v-for="nav in adminNavs">
                <router-link :to="{ name: nav.value }" class=" nav-link">
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
          <router-link
            class="h4 mb-0 text-white text-uppercase d-none d-lg-inline-block"
            :to="{ name: 'home' }"
            >{{ activeHomePage }}</router-link
          >
          <!-- User -->
          <ul class="navbar-nav align-items-center d-none d-md-flex">
            <li class="nav-item dropdown">
              <a
                aria-expanded="false"
                aria-haspopup="true"
                class="nav-link pr-0"
                data-toggle="dropdown"
                href="#"
                role="button"
              >
                <div class="media align-items-center">
                  <span class="avatar avatar-sm rounded-circle">
                    <img :src="user.avatarLink" alt="Image placeholder" />
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
                  :key="nav.id"
                  :to="{ name: nav.value }"
                  class="dropdown-item"
                  v-for="nav in profileNavs"
                >
                  <i :class="`fas fa-${nav.icon}`"></i>
                  <span>{{ nav.text }}</span>
                </router-link>
                <a @click="logout" class="dropdown-item pointed">
                  <i class="fas fa-sign-out-alt"></i>
                  <span>Logout</span>
                </a>
              </div>
            </li>
          </ul>
        </div>
      </nav>
      <!-- End Navbar -->
      <!-- Header -->
      <div
        class="header bg-gradient-primary d-none d-md-block"
        style="height: 4.5rem; z-index: 10;"
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
          text: "Contact",
          value: "contact",
          icon: "envelope-open-text"
        }
      ]
    };
  },
  methods: {
    formatNavText(string) {
      return string.split("-").join(" ");
    },
    logout() {
      this.$store.commit("LOGOUT");
      localStorage.removeItem("auth_token");
      this.$router.replace({
        name: "login",
        params: { message: "You have successfully logged out." }
      });
    }
  },
  created() {
    this.$store.dispatch("getCurrentUser");
  },
  computed: {
    activeHomePage() {
      return this.formatNavText(this.$route.name);
    },
    isModerator() {
      return this.user.userType === "MODERATOR" || this.isAdmin;
    },
    isAdmin() {
      return this.user.userType === "ADMIN";
    },
    user() {
      return this.$store.state.user;
    }
  },
  components: {
    Error
  }
};
</script>
