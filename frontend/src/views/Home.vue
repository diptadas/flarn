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
                <router-link :to="{ name: 'home' }" class="navbar-brand">
                    <img
                            alt="brand"
                            class="navbar-brand-img"
                            src="../assets/img/brand/Flarn.svg"
                            style="transform: scale(4, 4);"
                    />
                </router-link>
                <!-- User -->
                <ul class="nav align-items-center d-md-none">
                    <li class="nav-item dropdown">
                        <a
                                aria-expanded="false"
                                aria-haspopup="true"
                                class="nav-link nav-link-icon"
                                data-toggle="dropdown"
                                href="#"
                                role="button"
                        >
                            <i class="ni ni-bell-55"></i>
                        </a>
                        <div
                                aria-labelledby="navbar-default_dropdown_1"
                                class="dropdown-menu dropdown-menu-arrow dropdown-menu-right"
                        >
                            <a class="dropdown-item" href="#">Action</a>
                            <a class="dropdown-item" href="#">Another action</a>
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="#">Something else here</a>
                        </div>
                    </li>
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
                            <a class="dropdown-item" href="./examples/profile.html">
                                <i class="ni ni-single-02"></i>
                                <span>My profile</span>
                            </a>
                            <a class="dropdown-item" href="./examples/profile.html">
                                <i class="ni ni-settings-gear-65"></i>
                                <span>Settings</span>
                            </a>
                            <a class="dropdown-item" href="./examples/profile.html">
                                <i class="ni ni-calendar-grid-58"></i>
                                <span>Activity</span>
                            </a>
                            <a class="dropdown-item" href="./examples/profile.html">
                                <i class="ni ni-support-16"></i>
                                <span>Contact</span>
                            </a>
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="#!">
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
                                    <img src="../assets/img/brand/Flarn.svg"/>
                                </a>
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
                        <li :key="nav.id" class="nav-item" v-for="nav in navs">
                            <router-link :to="{ name: nav.value }" class=" nav-link">
                                <i :class="`fas fa-${nav.icon} text-primary`"></i>
                                {{ nav.text }}
                            </router-link>
                        </li>
                    </ul>
                    <template v-if="isModerator">
                        <!-- Divider -->
                        <hr class="my-3"/>
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
                        <hr class="my-3"/>
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
                                    aria-expanded="false"
                                    aria-haspopup="true"
                                    class="nav-link pr-0"
                                    data-toggle="dropdown"
                                    href="#"
                                    role="button"
                            >
                                <div class="media align-items-center">
                  <span class="avatar avatar-sm rounded-circle">
                    <img
                            :src="user.avatarLink"
                            alt="Image placeholder"
                    />
                  </span>
                                    <div class="media-body ml-2 d-none d-lg-block">
                                        <span class="mb-0 text-sm  font-weight-bold">{{user.fullName}}</span>
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
                    <router-view/>
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
                                    </router-link
                                    >
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

        <Error ref="error"/>
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
            formatNavText(string) {
                return string.split("-").join(" ");
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
                return (this.user.userType === 'MODERATOR') || (this.isAdmin) ;
            },
            isAdmin() {
                return this.user.userType === 'ADMIN';
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
