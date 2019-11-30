<template>
  <div>
    <!-- Header -->
    <div
      class="header pb-8 pt-5 pt-lg-8 d-flex align-items-center header-back rounded mt--7"
    >
      <!-- Mask -->
      <span class="mask bg-gradient-default opacity-8"></span>
      <!-- Header container -->
      <div class="container-fluid d-flex align-items-center">
        <div class="row">
          <div class="col-lg-12 col-md-12">
            <h1 class="display-2 text-white">Hello {{ user.fullName }}</h1>
            <p class="text-white mt-0 mb-5">
              This is your profile page. You can edit your account here
            </p>
          </div>
        </div>
      </div>
    </div>
    <div class="container-fluid mt--7">
      <div class="row d-flex align-items-start">
        <div
          class="order-2 mb-5 mb-xl-0 mx-auto"
          :class="edit ? 'col-xl-4' : 'col-xl-6'"
        >
          <div class="card card-profile shadow">
            <div class="row justify-content-center">
              <div class="col-lg-3 order-lg-2">
                <div class="card-profile-image">
                  <a href="#">
                    <img
                      :src="user.dpLink"
                      class="rounded-circle"
                      alt="Profile Picture"
                      style="height: 12rem;"
                    />
                  </a>
                </div>
              </div>
            </div>
            <div class="card-body pt-0 pt-md-8">
              <div class="row">
                <div class="col">
                  <div
                    class="card-profile-stats d-flex justify-content-center "
                  >
                    <div>
                      <span class="heading">
                        {{ user.subscriptions.length }}
                      </span>
                      <span class="description">Subscriptions</span>
                    </div>
                    <div>
                      <span class="heading">
                        {{ user.subscribers.length }}
                      </span>
                      <span class="description">Subscribers</span>
                    </div>
                    <div>
                      <span class="heading">
                        {{ user.subscriptions.length }}
                      </span>
                      <span class="description">User Rank</span>
                    </div>
                  </div>
                </div>
              </div>
              <div class="text-center">
                <h3>
                  {{ user.fullName }}
                  <span class="font-weight-light">, {{ user.userType }}</span>
                </h3>
                <div class="h5 font-weight-300">
                  <i class="ni location_pin mr-2"></i>{{ user.username }}
                </div>
                <div class="h5 mt-4">
                  <i class="ni business_briefcase-24 mr-2"></i
                  >{{ user.state }} - {{ user.city }}
                </div>
                <hr class="my-4" />
                <p>
                  {{ user.biography }}
                </p>
              </div>
            </div>
          </div>
        </div>

        <button v-if="!edit" class="btn btn-info" @click="edit = true">
          Edit profile
        </button>

        <div class="col-xl-8 order-xl-1" v-if="edit">
          <div class="card bg-secondary shadow">
            <div class="card-header bg-white border-0">
              <div class="row align-items-center">
                <div class="col-8">
                  <h3 class="mb-0">My account</h3>
                </div>
                <div class="col-4 text-right">
                  <a
                    href="#!"
                    class="btn btn-sm btn-primary"
                    @click="edit = false"
                    >Cancel</a
                  >
                </div>
              </div>
            </div>
            <div class="card-body">
              <form>
                <h6 class="heading-small text-muted mb-4">User information</h6>


                <div class="pl-lg-4">
                  <div class="row">

                    <div class="col-lg-12">
                      <div class="form-group">
                        <label class="form-control-label" for="input-country">Profile Picture</label>
                        <file-pond
                                name="test"
                                ref="pond"
                                label-idle="Drop files here or click to Browse"
                                accepted-file-types="image/jpeg, image/png"
                                :server="uploadImage"
                                :files="files"
                                @init="handleFilePondInit"
                                class="pointed"/>
                      </div>
                    </div>

                    <div class="col-lg-6">
                      <div class="form-group">
                        <label class="form-control-label" for="input-email"
                          >Email address</label
                        >
                        <input
                          type="email"
                          id="input-email"
                          class="form-control form-control-alternative"
                          placeholder="jesse@example.com"
                          v-model="user.username"
                        />
                      </div>
                    </div>
                    <div class="col-lg-6">
                      <div class="form-group">
                        <label class="form-control-label" for="input-username"
                          >Phone Number</label
                        >
                        <input
                          type="text"
                          id="input-username"
                          class="form-control form-control-alternative"
                          placeholder="Phone number"
                          v-model="user.phoneNumber"
                        />
                      </div>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-lg-6">
                      <div class="form-group">
                        <label class="form-control-label" for="input-first-name"
                          >First name</label
                        >
                        <input
                          type="text"
                          id="input-first-name"
                          class="form-control form-control-alternative"
                          placeholder="First name"
                          v-model="user.fullName"
                        />
                      </div>
                    </div>
                  </div>
                </div>
                <hr class="my-4" />
                <!-- Address -->
                <h6 class="heading-small text-muted mb-4">
                  Contact information
                </h6>
                <div class="pl-lg-4">
                  <div class="row">
                    <div class="col-md-12">
                      <div class="form-group">
                        <label class="form-control-label" for="input-address"
                          >Address</label
                        >
                        <input
                          id="input-address"
                          class="form-control form-control-alternative"
                          placeholder="Home Address"
                          v-model="user.street"
                          type="text"
                        />
                      </div>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-lg-4">
                      <div class="form-group">
                        <label class="form-control-label" for="input-city"
                          >City</label
                        >
                        <input
                          type="text"
                          id="input-city"
                          class="form-control form-control-alternative"
                          placeholder="City"
                          v-model="user.city"
                        />
                      </div>
                    </div>
                    <div class="col-lg-4">
                      <div class="form-group">
                        <label class="form-control-label" for="input-country"
                          >Country</label
                        >
                        <input
                          type="text"
                          id="input-country"
                          class="form-control form-control-alternative"
                          placeholder="Country"
                          value="United States"
                        />
                      </div>
                    </div>
                    <div class="col-lg-4">
                      <div class="form-group">
                        <label class="form-control-label" for="input-country"
                          >Postal code</label
                        >
                        <input
                          type="number"
                          id="input-postal-code"
                          class="form-control form-control-alternative"
                          placeholder="Postal code"
                          v-model="user.zip"
                        />
                      </div>
                    </div>
                  </div>
                </div>
                <hr class="my-4" />
                <!-- Description -->
                <h6 class="heading-small text-muted mb-4">About me</h6>
                <div class="pl-lg-4">
                  <div class="form-group">
                    <label>About Me</label>
                    <textarea
                      rows="4"
                      class="form-control form-control-alternative"
                      placeholder="A few words about you ..."
                      v-model="user.biography"
                    ></textarea>
                  </div>
                </div>

                <div class="d-flex justify-content-between mt-4">
                  <div>
                    <button
                      type="button"
                      class="btn btn-danger"
                      @click="deleteAccount"
                      :disabled="delLoading"
                    >
                      <span
                        class="spinner-grow spinner-grow-sm"
                        role="status"
                        aria-hidden="true"
                        v-if="delLoading"
                      ></span>
                      Deactivate Account
                    </button>
                  </div>

                  <div>
                    <button
                      type="button"
                      class="btn btn-primary"
                      @click="updateProfile"
                      :disabled="loading"
                    >
                      <span
                        class="spinner-grow spinner-grow-sm"
                        role="status"
                        aria-hidden="true"
                        v-if="loading"
                      ></span>
                      Update Profile
                    </button>
                  </div>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
    <Delete ref="delete" :content="deleteContent" :action="deleteAction" />
  </div>
</template>

<script>
  // Image/File upload

  import vueFilePond from 'vue-filepond';
  import 'filepond/dist/filepond.min.css';

  // Import image preview plugin styles
  import 'filepond-plugin-image-preview/dist/filepond-plugin-image-preview.min.css';

  // Import image preview and file type validation plugins
  import FilePondPluginFileValidateType from 'filepond-plugin-file-validate-type';
  import FilePondPluginImagePreview from 'filepond-plugin-image-preview';

  // Create component
  const FilePond = vueFilePond(FilePondPluginFileValidateType, FilePondPluginImagePreview);

import Delete from "@/components/utils/Delete.vue";
import axios from 'axios';

const imageUploadToken = process.env.VUE_APP_FILE_UPLOAD;

export default {
  name: "Profile",
  data() {
    return {
      delLoading: false,
      loading: false,
      edit: false,
      user: {
        subscriptions: [],
        subscribers: []
      },
      deleteContent: {},
      deleteAction: null,
      files: [],
      userId: '',
      uploadImage: {
        process: this.processImage
      }
    };
  },
  methods: {
    processImage (fieldName, file, metadata, load, error, progress, abort, transfer, options) {
    const token = imageUploadToken;
    const url = "https://api.imgbb.com/1/upload";
    const ext = file.name.split('.').pop();
    const fileName = this.$store.state.username + "." + ext;

    const data = new FormData();
    data.append('key', token);
    data.append('name', fileName);
    data.append('image', file);

      const config = {
        onUploadProgress: function(progressEvent) {
          progress(progressEvent.lengthComputable, progressEvent.loaded, progressEvent.total);
        },
      };

      // request.onload = function() {
      //   if (request.status >= 200 && request.status < 300) {
      //     // the load method accepts either a string (id) or an object
      //     load(request.responseText);
      //   }
      //   else {
      //     // Can call the error method if something is wrong, should exit after
      //     error('oh no');
      //   }
      // };

    axios.post(url, data, config)
            .then(res => {
              this.user.dpLink = res.data.data.medium.url;
              this.user.avatarLink = res.data.data.thumb.url;
              load(res.data.id);
            })
            .catch(err => {
              console.log(err)
              error("Error uploading file")
            })
  },
    handleFilePondInit() {
      console.log('FilePond has initialized');

      // FilePond instance methods are available on `this.$refs.pond`
    },
    deleteAccount() {
      this.deleteContent = {
        name: this.user.fullName
      };
      this.deleteAction = () => this.doDeleteAccount();
      this.$refs["delete"].show();
    },
    doDeleteAccount() {
      const url = "users/current/deactivate";

      this.$http.get(url).then(res => {
        this.$router.replace({ name: "login" });
      });
    },
    updateProfile() {
      if (this.loading) return;
      this.loading = true;
      const url = `users/current`;

      this.$http
        .post(url, this.user)
        .then(res => {
          this.user = res.data;
          this.edit = false;
        })
        .finally(() => (this.loading = false));
    },
    getUserProfile(userId) {
      const url = `users/${userId}`;

      this.$http.get(url).then(res => {
        this.user = res.data;
        this.edit = false;
      });
    }
  },
  created() {
    this.userId = Number(this.$store.state.userId);
    this.getUserProfile(this.userId);
  },
  components: {
    Delete,
    FilePond
  }
};
</script>
