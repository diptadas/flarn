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
                            class="col-12 nav-link mb-sm-3 mb-md-0"
                            data-toggle="tab"
                            href="#tabs-text-1"
                            id="tabs-text-1-tab"
                            role="tab"
                    >{{ tab.text }}</a
                    >
                </li>
            </ul>
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
                activeTab: 1,
                users: [],
                tabs: [
                    {
                        id: 1,
                        text: "Global",
                        value: "global"
                    },
                    {
                        id: 2,
                        text: "Following",
                        value: "subscriptions"
                    },
                ]
            };
        },
        methods: {
            changeTabView(tabId) {
                if (this.activeTab === tabId) return;

                switch (tabId) {
                    case 1:
                        this.getGlobalRank();
                        break;
                    case 2:
                        this.getSubscriptionsRank();
                        break;
                }

                this.activeTab = tabId;
            },
            getSubscriptionsRank() {
                const url = '/users/current/subscriptionsOrderByPoints';

                this.$http.get(url).then(res => {
                    this.users = res.data;
                });
            },
            getGlobalRank() {
                const url = "users/orderByPoints";

                this.$http.get(url).then(res => {
                    this.users = res.data;
                });
            }
        },
        created() {
            this.getGlobalRank();
        },
        components: {
            PersonItem
        }
    };
</script>
