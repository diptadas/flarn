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
          <div class="table-responsive">
            <table class="table align-items-center table-flush">
              <tbody>
                <person-item
                  v-for="user in users"
                  :key="user.id"
                  :user="user"
                  class="my-4"
                ></person-item>
              </tbody>
            </table>
          </div>
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
      activeTab: 1,
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

      this.$http
        .get(url)
        .then(res => {
          this.users = res.data;
        })
    },
    getSubscribedUsers() {
      const url = `users/${this.$store.state.userId}/subscribers`;

      this.$http
        .get(url)
        .then(res => {
          this.users = res.data;
        })
    },
    getUsers() {
      const url = "users";

      this.$http
        .get(url)
        .then(res => {
          this.users = res.data;
        })
    }
  },
  created() {
    this.getSubscribedUsers();
  },
  components: {
    PersonItem
  }
};
</script>

<style lang="scss" scoped>
table {
  border-spacing: 2em;
}
</style>
