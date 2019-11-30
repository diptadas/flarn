<template>
  <div class="column">
    <div class="mt-2">
      <ul
              class="nav nav-pills nav-fill flex-column flex-sm-row"
              id="tabs-text"
              role="tablist"
      >
        <li class="nav-item" v-for="tab in tabs" :key="tab.id">
          <a
                  class="nav-link mb-sm-3 mb-md-0"
                  id="tabs-text-1-tab"
                  data-toggle="tab"
                  href="#tabs-text-1"
                  role="tab"
                  aria-controls="tabs-text-1"
                  aria-selected="true"
                  :class="activeTab === tab.id ? ' active' : ''"
                  @click="changeTabView(tab.id)"
          >{{ tab.text }}</a
          >
        </li>
      </ul>
    </div>

    <div class="row mt-4">
      <div class="col">
        <div class="card shadow">
          <div class="table-responsive min-h-full" v-if="users.length">
            <table class="table align-items-center table-flush">
              <thead class="thead-light">
              <tr>
                <th scope="col">FullName</th>
                <th scope="col">ROLE</th>
                <th scope="col">POINTS</th>
                <th scope="col">FOLLOWERS</th>
                <th scope="col">FOLLOWING</th>
              </tr>
              </thead>
              <tbody>
              <person-item
                      v-for="user in users"
                      :key="user.id"
                      :user="user"
                      :action="false"
                      class="my-4"
              ></person-item>
              </tbody>
            </table>
          </div>
          <Empty v-else mess="users" />

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
