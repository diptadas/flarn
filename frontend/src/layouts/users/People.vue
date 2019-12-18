<template>
    <div class="column">
        <div class="mt-2">
            <ul
                    class="nav nav-pills nav-fill"
                    id="tabs-text"
                    role="tablist"
            >
                <li :key="tab.id" class="nav-item" v-for="tab in tabs">
                    <a
                            :class="activeTab === tab.id ? ' active' : ''"
                            @click="changeTabView(tab.id)"
                            aria-controls="tabs-text-1"
                            aria-selected="true"
                            class="nav-link mb-sm-3 mb-md-0"
                            data-toggle="tab"
                            href="#tabs-text-1"
                            id="tabs-text-1-tab"
                            role="tab"
                    >{{ tab.text }}</a
                    >
                </li>
            </ul>
        </div>

        <div class="row d-flex align-items-start justify-content-center mt-lg-4">
            <div class="form-group col-md-6 mb-0">
                <div class="input-group input-group-alternative mb-4">
                    <input
                            @keyup.enter="searchUser"
                            class="form-control"
                            placeholder="Search for Users"
                            type="text"
                            v-model="name"
                    />
                    <div class="input-group-append">
            <span class="input-group-text"
            ><i class="ni ni-zoom-split-in"></i
            ></span>
                    </div>
                </div>
            </div>

            <div class="col-md-6 d-none d-lg-block">
                <button
                        :disabled="searchLoading"
                        @click="searchUser"
                        class="btn btn-primary"
                        type="button"
                >
          <span
                  aria-hidden="true"
                  class="spinner-grow spinner-grow-sm"
                  role="status"
                  v-if="searchLoading"
          ></span>
                    Search Users
                </button>
            </div>
        </div>

        <div class="row mt-lg-4">
            <div class="col px-0">
                <div class="card shadow">
                    <div class="table-responsive min-h-full" v-if="users.length">
                        <table class="table align-items-center table-flush">
                            <thead class="thead-light">
                            <tr>
                                <th scope="col" class="d-none d-lg-table-cell">FullName</th>
                                <th scope="col" class="d-lg-none">Users</th>
                                <th scope="col" class="d-none d-lg-table-cell">ROLE</th>
                                <th scope="col" class="d-none d-lg-table-cell">POINTS</th>
                                <th scope="col" class="d-none d-lg-table-cell">FOLLOWERS</th>
                                <th scope="col" class="d-none d-lg-table-cell">FOLLOWING</th>
                            </tr>
                            </thead>
                            <tbody>
                            <person-item
                                    :action="false"
                                    :key="user.id"
                                    :user="user"
                                    class="my-4"
                                    v-for="user in users"
                            ></person-item>
                            </tbody>
                        </table>
                    </div>
                    <Empty mess="users" v-else/>

                    <div class="card-footer py-4">
                        <nav aria-label="...">
                            <ul class="pagination justify-content-end mb-0">
                                <li class="page-item disabled">
                                    <a class="page-link" href="#" tabindex="-1">
                                        <i class="fa fa-arrow-left"></i>
                                        <span class="sr-only">Previous</span>
                                    </a>
                                </li>
                                <li class="page-item active">
                                    <a class="page-link" href="#">1</a>
                                </li>
                                <li class="page-item">
                                    <a class="page-link" href="#">
                                        2
                                        <span class="sr-only">(current)</span>
                                    </a>
                                </li>
                                <li class="page-item"><a class="page-link" href="#">3</a></li>
                                <li class="page-item">
                                    <a class="page-link" href="#">
                                        <i class="fa fa-arrow-right"></i>
                                        <span class="sr-only">Next</span>
                                    </a>
                                </li>
                            </ul>
                        </nav>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import PersonItem from "@/components/people/Person.vue";

    export default {
        name: "People",
        data() {
            return {
                searchLoading: false,
                name: "",
                activeTab: 3,
                users: [],
                tabs: [
                    {
                        id: 1,
                        text: "Followers",
                        value: "subscribers"
                    },
                    {
                        id: 2,
                        text: "Following",
                        value: "subscriptions"
                    },
                    {
                        id: 3,
                        text: "All Users",
                        value: "all"
                    }
                ]
            };
        },
        methods: {
            searchUser() {
                if (this.searchLoading) return false;
                this.searchLoading = true;
                const url = `users/search?name=${this.name}`;

                this.$http
                    .get(url)
                    .then(res => {
                        this.users = res.data;
                        this.name = "";
                    })
                    .finally(() => (this.searchLoading = false));
            },
            changeTabView(tabId) {
                if (this.activeTab === tabId) return;

                switch (tabId) {
                    case 1:
                        this.getSubscribedUsers();
                        break;
                    case 2:
                        this.getSubscriptions();
                        break;
                    case 3:
                        this.getUsers();
                        break;
                }

                this.activeTab = tabId;
            },
            getSubscriptions() {
                const url = `users/${this.$store.state.userId}/subscriptions`;

                this.$http.get(url).then(res => {
                    this.users = res.data;
                });
            },
            getSubscribedUsers() {
                const url = `users/${this.$store.state.userId}/subscribers`;

                this.$http.get(url).then(res => {
                    this.users = res.data;
                });
            },
            getUsers() {
                const url = "users";

                this.$http.get(url).then(res => {
                    this.users = res.data;
                });
            }
        },
        created() {
            this.getUsers();
        },
        components: {
            PersonItem
        }
    };
</script>
