<template>
  <div class="column">
    <div class="row d-flex align-items-start justify-content-center  mt-4">
      <div class="form-group col-md-6 mb-0">
        <div class="input-group input-group-alternative mb-4">
          <input
            class="form-control"
            placeholder="Search for User"
            type="text"
            v-model="name"
            @keyup.enter="searchUser"
          />
          <div class="input-group-append">
            <span class="input-group-text"
              ><i class="ni ni-zoom-split-in"></i
            ></span>
          </div>
        </div>
      </div>

      <div class="col-md-6">
        <button type="button" class="btn btn-primary" @click="searchUser">
          Search Users
        </button>
      </div>
    </div>

    <div class="row mt-4">
      <div class="col">
        <div class="card shadow">
          <div class="table-responsive min-h-full">
            <table class="table align-items-center table-flush">
              <tbody>
                <person-item
                  v-for="user in users"
                  :key="user.id"
                  :user="user"
                  :action="true"
                  class="my-4"
                  @promote="promoteUser(user.id)"
                  @delete="deleteUser(user.id)"
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

    <div class="col-md-4">
      <button
        data-toggle="modal"
        data-target="#modal-notification"
        ref="modal-btn"
        v-show="false"
      ></button>
      <div
        class="modal fade"
        id="modal-notification"
        tabindex="-1"
        role="dialog"
        aria-labelledby="modal-notification"
        aria-hidden="true"
      >
        <div class="modal-dialog modal-dialog-centered modal-" role="document">
          <div class="modal-content">
            <div class="modal-header">
              <h6 class="modal-title" id="modal-title-notification">
                Your attention is required
              </h6>
              <button
                type="button"
                class="close"
                data-dismiss="modal"
                aria-label="Close"
                ref="modal-btn-close"
              >
                <span aria-hidden="true">×</span>
              </button>
            </div>

            <div class="modal-body">
              <div class="py-3 text-center">
                <i class="ni ni-bell-55 ni-3x"></i>
                <h4 class="heading mt-4">
                  Select new position for {{ currentUser.fullName }}
                </h4>

                <div class=" d-inline-flex w-100 justify-content-around mt-4">
                  <div
                    class="custom-control custom-radio mb-3"
                    v-for="type in userTypes"
                    :key="type.id"
                  >
                    <input
                      name="category"
                      class="custom-control-input"
                      :id="`category-radio-${type.id}`"
                      type="radio"
                      v-model="userType"
                      :value="type.value"
                      :disabled="exitingUserType === type.value"
                    />
                    <label
                      class="custom-control-label"
                      :for="`category-radio-${type.id}`"
                    >
                      {{ type.text }}
                    </label>
                  </div>
                </div>
              </div>
            </div>

            <div class="modal-footer">
              <button
                type="button"
                class="btn btn-primary"
                @click="doPromoteUser"
              >
                Update User
              </button>
              <button
                type="button"
                class="btn btn-link  ml-auto"
                data-dismiss="modal"
              >
                Cancel
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import PersonItem from "@/components/people/Person.vue";

export default {
  name: "ManageUser",
  data() {
    return {
      name: "",
      users: [],
      currentUser: {},
      exitingUserType: "",
      userType: "",
      userTypes: [
        {
          id: 1,
          text: "Learner",
          value: "LEARNER",
          enabled: false
        },
        {
          id: 2,
          text: "Moderator",
          value: "MODERATOR",
          enabled: false
        },
        {
          id: 3,
          text: "Admin",
          value: "ADMIN",
          enabled: false
        }
      ]
    };
  },
  methods: {
    promoteUser(userId) {
      const user = this.users.find(user => user.id === userId);
      if (!user) return false;

      this.exitingUserType = user.userType;
      this.userType = "";
      this.currentUser = user;
      this.$refs["modal-btn"].click();
    },
    doPromoteUser() {
      const url = `users/usertype`;

      const data = {
        id: this.currentUser.id,
        userType: this.userType
      };

      this.$http.post(url, data).then(res => {
        const index = this.users.findIndex(user => user.id === res.data.id);
        this.users.splice(index, 1, res.data);
        this.$refs["modal-btn-close"].click();
      });
    },
    deleteUser(userId) {},
    searchUser() {
      const url = `users/search?name=${this.name}`;

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
